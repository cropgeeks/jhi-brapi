package jhi.brapi.api.markerprofiles;

import java.io.*;
import java.sql.*;
import java.util.*;

import jhi.brapi.api.*;
import jhi.brapi.api.Status;
import jhi.brapi.util.*;

import org.restlet.*;
import org.restlet.resource.*;

public class AlleleMatrixDAO
{
	private List<String> createArray(String markerDbId, String markerprofileDbIds, String allele)
	{
		List<String> callData = new ArrayList<>();
		callData.add(markerDbId);
		callData.add(markerprofileDbIds);
		callData.add(allele);

		return callData;
	}

	public BrapiBaseResource<BrapiAlleleMatrix> getFromHdf5(Request request, Context context, List<String> profileIds, List<String> markerDbIds, String format, GenotypeEncodingParams params, int currentPage, int pageSize)
		throws Exception
	{
		BrapiAlleleMatrix matrix = new BrapiAlleleMatrix();

		// The dataset. Remember the mapping from germplasmDbId to datasetDbId. We're extracting data from hdf5, not the database. So we need to remember the mapping.
		Map<String, String> germplasmDbIdToDataset = new HashMap<>();
		// The lines we want to extract
		List<Integer> germinatebaseIds = new ArrayList<>();

		try
		{
			String datasetId = null;
			for (String profileId : profileIds)
			{
				String[] tokens = profileId.split("-");
				germplasmDbIdToDataset.put(tokens[1], tokens[0]);
				germinatebaseIds.add(Integer.parseInt(tokens[1]));
				datasetId = tokens[0];
			}

			String hdf5File = HDF5Utils.getHdf5File(datasetId);

			String folder = context.getParameters().getFirstValue("hdf5-folder");

			// Get the bidirectional mapping between germplasm/marker id and name
			LinkedHashMap<String, String> germplasmIdToName = getGermplasmMapping(germinatebaseIds);
			LinkedHashMap<String, String> markerIdToName;

			try (Hdf5DataExtractor extractor = new Hdf5DataExtractor(new File(folder, hdf5File)))
			{
				if (markerDbIds == null || markerDbIds.isEmpty())
				{
					// If the user didn't request specific markers, get all of the ones from the file
					markerIdToName = getMarkerMappingForNames(extractor.getMarkers());
				}
				else
				{
					// If the user requested specific markers, get them from the database
					markerIdToName = getMarkerMappingForIds(markerDbIds);
				}

				List<String> internalGermplasmIds = new ArrayList<>(germplasmIdToName.keySet());
				List<String> internalMarkerIds = new ArrayList<>(markerIdToName.keySet());

				int nrOfMarkers = internalMarkerIds.size();
				int nrOfLines = internalGermplasmIds.size();

				if (format != null)
				{
					if (format.equals("tsv")) // TODO: TSV result should be transposed
					{
						File file = File.createTempFile("allelematrix", ".tsv");

						Hdf5ToGenotypeConverter converter = new Hdf5ToGenotypeConverter(new File(folder, hdf5File), germplasmIdToName, markerIdToName, params, datasetId);
						converter.readInput();
						converter.extractData(file.getAbsolutePath(), "");

						BrapiBaseResource<BrapiAlleleMatrix> result = new BrapiBaseResource<>(matrix, 0, 1, 1);

						result.getMetadata().getStatus().add(new Status("asynchid", file.getName()));

						return result;
					}
					else if (format.equals("flapjack"))
					{
						File file = File.createTempFile("allelematrix", ".dat");

						Hdf5ToGenotypeConverter converter = new Hdf5ToGenotypeConverter(new File(folder, hdf5File), germplasmIdToName, markerIdToName, params, datasetId);
						converter.readInput();
						converter.extractDataFJ(file.getAbsolutePath(), Collections.singletonList("# fjFile = genotype"));

						BrapiBaseResource<BrapiAlleleMatrix> result = new BrapiBaseResource<>(matrix, 0, 1, 1);

						result.getMetadata().getStatus().add(new Status("asynchid", file.getName()));
						return result;
					}
				}
				else
				{
					// Get the total number of data points
					int maxData = Math.min(nrOfLines * nrOfMarkers, extractor.getLines().size() * extractor.getMarkers().size());

					List<List<String>> data = new ArrayList<>();
					int lower = DatabaseUtils.getLimitStart(currentPage, pageSize);

					if (nrOfLines > 0 && nrOfMarkers > 0)
					{
						// Loop over the chunk of data that is required
						for (int i = lower; i < lower + pageSize; i++)
						{
							// Get the x and y coordinates
							int lineIndex = i / nrOfMarkers;
							int markerIndex = i - ((i / nrOfMarkers) * nrOfMarkers);

							if (lineIndex >= internalGermplasmIds.size())
								break;

							// Get the names from the database, this is required to pass it to the HDF5 converter
							String lineName = germplasmIdToName.get(internalGermplasmIds.get(lineIndex));
							String markerName = markerIdToName.get(internalMarkerIds.get(markerIndex));

							// Skip a combination if the requested data doesn't exist
							if (lineName == null || markerName == null)
								continue;

							// Get the allele value from the HDF5
							String alleles = extractor.get(lineName, markerName, params);

							String germplasmDbId = internalGermplasmIds.get(lineIndex);
							// Convert the data back into ids
							List<String> callData = createArray(internalMarkerIds.get(markerIndex), germplasmDbIdToDataset.get(germplasmDbId) + "-" + germplasmDbId, alleles);

							// Add the data to the array
							data.add(callData);
						}
					}

					matrix.setData(data);

					return new BrapiBaseResource<BrapiAlleleMatrix>(matrix, currentPage, pageSize, maxData);
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		throw new Exception();
	}

	/**
	 * Returns the bidirectional mapping between germplasm ids and names based on the ids
	 *
	 * @param germinatebaseIds The ids of the germplasm (not the markerprofile ids)
	 * @return The bidirectional mapping between germplasm ids and names based on the ids
	 */
	private LinkedHashMap<String, String> getGermplasmMapping(List<Integer> germinatebaseIds)
	{
		String query = "SELECT id, name FROM germinatebase WHERE id IN (%s) ORDER BY FIELD (id, %s)";

		LinkedHashMap<String, String> map = new LinkedHashMap<>();

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = DatabaseUtils.createOrderedInStatement(con, query, germinatebaseIds);
			 ResultSet resultSet = statement.executeQuery())
		{
			while(resultSet.next())
				map.put(resultSet.getString("id"), resultSet.getString("name"));
		}
		catch (SQLException e) { e.printStackTrace(); }

		return map;
	}

	/**
	 * Returns the bidirectional mappint between marker ids and names based on the ids
	 *
	 * @param markerDbIds The ids of the markers
	 * @return The bidirectional mappint between marker ids and names based on the ids
	 */
	private LinkedHashMap<String, String> getMarkerMappingForIds(List<String> markerDbIds)
	{
		String query = "SELECT id, marker_name FROM markers WHERE id IN (%s) ORDER BY FIELD (id, %s)";

		LinkedHashMap<String, String> map = new LinkedHashMap<>();

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = DatabaseUtils.createOrderedInStatement(con, query, markerDbIds);
			 ResultSet resultSet = statement.executeQuery())
		{
			while(resultSet.next())
				map.put(resultSet.getString("id"), resultSet.getString("marker_name"));
		}
		catch (SQLException e) { e.printStackTrace(); }

		return map;
	}

	/**
	 * Returns the bidirectional mappint between marker ids and names based on the names
	 *
	 * @param markerNames The names of the markers
	 * @return The bidirectional mappint between marker ids and names based on the ids
	 */
	private LinkedHashMap<String, String> getMarkerMappingForNames(List<String> markerNames)
	{
		String query = "SELECT id, marker_name FROM markers WHERE marker_name IN (%s) ORDER BY FIELD (marker_name, %s)";

		LinkedHashMap<String, String> map = new LinkedHashMap<>();

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = DatabaseUtils.createOrderedInStatement(con, query, markerNames);
			 ResultSet resultSet = statement.executeQuery())
		{
			while(resultSet.next())
				map.put(resultSet.getString("id"), resultSet.getString("marker_name"));
		}
		catch (SQLException e) { e.printStackTrace(); }

		return map;
	}

	private LinkedHashMap<String, String> getGermplasmMappingForNames(List<String> lineNames)
	{
		String query = "SELECT id, name FROM germinatebase WHERE name IN (%s) ORDER BY FIELD (name, %s)";

		LinkedHashMap<String, String> map = new LinkedHashMap<>();

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = DatabaseUtils.createOrderedInStatement(con, query, lineNames);
			 ResultSet resultSet = statement.executeQuery())
		{
			while(resultSet.next())
				map.put(resultSet.getString("id"), resultSet.getString("name"));
		}
		catch (SQLException e) { e.printStackTrace(); }

		return map;
	}

	public BrapiBaseResource<BrapiAlleleMatrix> getFromHdf5ByMatrixId(Request request, Context context, String matrixDbId, GenotypeEncodingParams params, int currentPage, int pageSize)
		throws Exception
	{
		BrapiAlleleMatrix matrix = new BrapiAlleleMatrix();

		String datasetId = matrixDbId;

		String hdf5File = HDF5Utils.getHdf5File(datasetId);

		String folder = context.getParameters().getFirstValue("hdf5-folder");

		// Get the bidirectional mapping between germplasm/marker id and name
		LinkedHashMap<String, String> germplasmIdToName;
		LinkedHashMap<String, String> markerIdToName;

		try (Hdf5DataExtractor extractor = new Hdf5DataExtractor(new File(folder, hdf5File)))
		{
			// If the user didn't request specific markers, get all of the ones from the file
			markerIdToName = getMarkerMappingForNames(extractor.getMarkers());
			germplasmIdToName = getGermplasmMappingForNames(extractor.getLines());

			File file = File.createTempFile("allelematrix", ".dat");

			Hdf5ToGenotypeConverter converter = new Hdf5ToGenotypeConverter(new File(folder, hdf5File), germplasmIdToName, markerIdToName, params, datasetId);
			converter.readInput();
			converter.extractDataFJ(file.getAbsolutePath(), Collections.singletonList("# fjFile = genotype"));

			BrapiBaseResource<BrapiAlleleMatrix> result = new BrapiBaseResource<>(matrix, 0, 1, 1);

			result.getMetadata().getStatus().add(new Status("asynchid", file.getName()));
			return result;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			throw new Exception();
		}
	}
}