package jhi.brapi.data;

import java.io.*;
import java.sql.*;
import java.util.*;

import jhi.brapi.resource.*;

/**
 * Created by gs40939 on 16/06/2015.
 */
public class AlleleMatrixDAO
{
	private String allMarkers = "select genotypes.allele1, genotypes.allele2, genotypes.marker_id, " +
			"genotypes.germinatebase_id, genotypes.dataset_id, markers.id from genotypes INNER JOIN markers ON " +
			"genotypes.marker_id = markers.id INNER JOIN datasets ON genotypes.dataset_id = datasets.id where germinatebase_id IN (%s) AND datasets.id IN (%s) ORDER BY markers.marker_name, dataset_id, germinatebase_id LIMIT ?, ?";

	private String countAllMarkers = "select COUNT(1) AS total_count, genotypes.allele1, genotypes.allele2, genotypes.marker_id, " +
			"genotypes.germinatebase_id, genotypes.dataset_id, markers.id from genotypes INNER JOIN markers ON " +
			"genotypes.marker_id = markers.id INNER JOIN datasets ON genotypes.dataset_id = datasets.id where germinatebase_id IN (%s) AND datasets.id IN (%s)";

	public BasicResource<BrapiAlleleMatrix> get(List<String> markerProfileDbIds, List<String> markerDbIds, String format, GenotypeEncodingParams params, int currentPage, int pageSize)
	{
		if (format != null)
		{
			currentPage = 0;
			pageSize = Integer.MAX_VALUE;
		}

		BasicResource<BrapiAlleleMatrix> result = new BasicResource<>();

		List<Integer> datasetIds = new ArrayList<>();
		List<Integer> germinatebaseIds = new ArrayList<>();

		for (String profileId : markerProfileDbIds)
		{
			String[] tokens = profileId.split("-");
			datasetIds.add(Integer.parseInt(tokens[0]));
			germinatebaseIds.add(Integer.parseInt(tokens[1]));
		}

		String countQuery = String.format(countAllMarkers, DatabaseUtils.createPlaceholders(germinatebaseIds.size()), DatabaseUtils.createPlaceholders(datasetIds.size()));

		long totalCount = -1;

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = createByIdStatement(con, countQuery, germinatebaseIds, datasetIds);
			 ResultSet resultSet = statement.executeQuery())
		{
			if (resultSet.first())
				totalCount = resultSet.getInt("total_count");
		}
		catch (SQLException e) { e.printStackTrace(); }

		if (totalCount != -1)
		{
			try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
				 PreparedStatement statement = DatabaseUtils.createInLimitStatement(con, allMarkers, currentPage, pageSize, germinatebaseIds, datasetIds);
				 ResultSet resultSet = statement.executeQuery())
			{
				if (format != null)
				{
					result = getMatrixForFile(resultSet, markerProfileDbIds, params);
				}
				else
				{
					result = new BasicResource<BrapiAlleleMatrix>(getMatrixForJson(resultSet, params), currentPage, pageSize, totalCount);
				}
			}
			catch (SQLException | IOException e)
			{
				e.printStackTrace();
			}
		}

		return result;
	}

	private BasicResource<BrapiAlleleMatrix> getMatrixForFile(ResultSet resultSet, List<String> markerProfileDbIds, GenotypeEncodingParams params)
			throws SQLException, IOException
	{
		BrapiAlleleMatrix matrix = new BrapiAlleleMatrix();

		File file = File.createTempFile("allelematrix", ".tsv");

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file)))
		{
			bw.write("markerprofileDbIds");

			for (String markerprofileDbId : markerProfileDbIds)
				bw.write("\t" + markerprofileDbId);

			String previousMarkerName = null;
			while (resultSet.next())
			{
				String markerName = resultSet.getString("markers.id");
				String allele1 = resultSet.getString("allele1");
				String allele2 = resultSet.getString("allele2");

				if (!Objects.equals(previousMarkerName, markerName))
				{
					previousMarkerName = markerName;
					bw.newLine();
					bw.write(markerName);
				}

				bw.write("\t" + GenotypeEncodingUtils.getString(allele1, allele2, params));
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		BasicResource<BrapiAlleleMatrix> result = new BasicResource<BrapiAlleleMatrix>(matrix, 0, 1, 1);
		Datafile datafile = new Datafile("https://ics.hutton.ac.uk/brapi/cactuar/v1/files/" + file.getName()); // TODO: get absolute URL
		result.getMetadata().setDatafiles(Collections.singletonList(datafile));
		return result;
	}

	private BrapiAlleleMatrix getMatrixForJson(ResultSet resultSet, GenotypeEncodingParams params)
			throws SQLException
	{
		BrapiAlleleMatrix matrix = new BrapiAlleleMatrix();

		List<List<String>> data = new ArrayList<>();

		while (resultSet.next())
		{
			String markerName = resultSet.getString("markers.id");
			String allele1 = resultSet.getString("allele1");
			String allele2 = resultSet.getString("allele2");
			String lineName = resultSet.getString("dataset_id") + "-" + resultSet.getString("germinatebase_id");

			List<String> callData = createArray(markerName, lineName, GenotypeEncodingUtils.getString(allele1, allele2, params));
			data.add(callData);
		}

		matrix.setData(data);

		return matrix;
	}

	private List<String> createArray(String markerDbId, String markerprofileDbIds, String allele)
	{
		List<String> callData = new ArrayList<>();
		callData.add(markerDbId);
		callData.add(markerprofileDbIds);
		callData.add(allele);

		return callData;
	}

	private PreparedStatement createByIdStatement(Connection con, String query, List<Integer> germinatebaseIds, List<Integer> datasetIds)
			throws SQLException
	{
		// Get the basic information on the map
		PreparedStatement statement = con.prepareStatement(query);

		int counter = 1;
		for (Integer id : germinatebaseIds)
			statement.setInt(counter++, id);

		for (Integer id : datasetIds)
			statement.setInt(counter++, id);

		return statement;
	}

	// TODO: Check what happens if the user requests data that isn't in the hdf5 file (is the data chunk as big as requested? is the totalCount correct? etc)
	public BasicResource<BrapiAlleleMatrix> getFromHdf5(List<String> profileIds, List<String> markerDbIds, GenotypeEncodingParams params, int currentPage, int pageSize)
	{
		BrapiAlleleMatrix matrix = new BrapiAlleleMatrix();

		Hdf5DataExtractor extractor = new Hdf5DataExtractor(new File("/home/tomcat/germinate-demo-brapi/germinate-demo-brapi.hdf5")); // TODO: get hdf5 location from the database

		// The dataset. Remember the mapping from germplasmDbId to datasetDbId. We're extracting data from hdf5, not the database. So we need to remember the mapping.
		Map<String, String> germplasmDbIdToDataset = new HashMap<>();
		// The lines we want to extract
		List<Integer> germinatebaseIds = new ArrayList<>();

		for (String profileId : profileIds)
		{
			String[] tokens = profileId.split("-");
			germplasmDbIdToDataset.put(tokens[1], tokens[0]);
			germinatebaseIds.add(Integer.parseInt(tokens[1]));
		}

		// Get the bidirectional mapping between germplasm/marker id and name
		LinkedHashMap<String, String> germplasmIdToName = getGermplasmMapping(germinatebaseIds);
		LinkedHashMap<String, String> markerIdToName;

		if(markerDbIds == null || markerDbIds.isEmpty())
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

		// Get the total number of data points
		int maxData = Math.min(nrOfLines * nrOfMarkers, extractor.getLines().size() * extractor.getMarkers().size());

		List<List<String>> data = new ArrayList<>();
		int lower = PaginationUtils.getLowLimit(currentPage, pageSize);

		if(nrOfLines > 0 && nrOfMarkers > 0)
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

		return new BasicResource<>(matrix, currentPage, pageSize, maxData);
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
			 PreparedStatement statement = createOrderedInStatement(con, query, germinatebaseIds);
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
			 PreparedStatement statement = createOrderedInStatement(con, query, markerDbIds);
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
			 PreparedStatement statement = createOrderedInStatement(con, query, markerNames);
			 ResultSet resultSet = statement.executeQuery())
		{
			while(resultSet.next())
				map.put(resultSet.getString("id"), resultSet.getString("marker_name"));
		}
		catch (SQLException e) { e.printStackTrace(); }

		return map;
	}

	/**
	 * Create a statement that will search for items with the given "IN" call. This will also order the items based on these values
	 *
	 * @param con   The database {@link Connection}
	 * @param sql   The SQL query containing two "%s" placeholders, one in the "IN" block and one for the order of the items
	 * @param items The items to put into the two placeholders
	 * @return The {@link PreparedStatement} representing the query
	 * @throws SQLException Thrown if the query fails on the database
	 */
	private PreparedStatement createOrderedInStatement(Connection con, String sql, List<?> items) throws SQLException
	{
		String formatted = String.format(sql, DatabaseUtils.createPlaceholders(items.size()), DatabaseUtils.createPlaceholders(items.size()));

		PreparedStatement stmt = con.prepareStatement(formatted);

		int i = 1;
		for(Object item : items)
			stmt.setString(i++, item.toString());

		for(Object item : items)
			stmt.setString(i++, item.toString());

		return stmt;
	}
}