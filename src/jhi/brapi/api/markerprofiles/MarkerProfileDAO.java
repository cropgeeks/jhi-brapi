package jhi.brapi.api.markerprofiles;

import java.io.File;
import java.sql.*;
import java.util.*;

import jhi.brapi.api.*;
import jhi.brapi.util.*;
import org.restlet.Context;

public class MarkerProfileDAO
{
	private final String genotypeDatasets = "SELECT datasets.id FROM datasets " +
		"LEFT JOIN experiments ON experiment_id = experiments.id WHERE " +
		"experiments.experiment_type_id = 1";

	private final String getNameForGermplasmId = "SELECT name FROM germinatebase " +
		"WHERE id = ?";

	private final String allMarkers = "select genotypes.allele1, genotypes.allele2, genotypes.marker_id, " +
		"genotypes.germinatebase_id, genotypes.dataset_id, CONCAT(genotypes.dataset_id, '-', genotypes.germinatebase_id) " +
		"AS markerprofile_id, markers.marker_name from genotypes INNER JOIN markers ON genotypes.marker_id = markers.id " +
		"INNER JOIN datasets ON genotypes.dataset_id = datasets.id where germinatebase_id=? AND datasets.id=?";

	public BrapiListResource<BrapiMarkerProfile> getAll(Context context, LinkedHashMap<String, String> params, int currentPage, int pageSize)
	{
		BrapiListResource<BrapiMarkerProfile> result = new BrapiListResource<>();
		List<BrapiMarkerProfile> markerProfiles = new ArrayList<>();

		List<String> dataSetIds = getDataSetIds();

		dataSetIds.forEach(id ->
		{
			String hdf5File = Hdf55Utils.getHdf5File(id);

			String folder = context.getParameters().getFirstValue("hdf5-folder");

			try(Hdf5DataExtractor extractor = new Hdf5DataExtractor(new File(folder, hdf5File)))
			{
				List<String> lineNames = extractor.getLines();
				LinkedHashMap<String, String> map = Hdf55Utils.getGermplasmMappingForNames(lineNames);

				markerProfiles.addAll(getProfiles(id, map));
			}
			catch (Exception e) { e.printStackTrace(); }
		});

		int start = DatabaseUtils.getLimitStart(currentPage, pageSize);
		int end = Math.min(start + pageSize, markerProfiles.size());

		List<BrapiMarkerProfile> filteredProfiles = markerProfiles.subList(start, end);

		return new BrapiListResource<BrapiMarkerProfile>(filteredProfiles, currentPage, pageSize, markerProfiles.size());
	}

	private List<BrapiMarkerProfile> getProfiles(String id, LinkedHashMap<String, String> germplasmNamesById)
	{
		List<BrapiMarkerProfile> profiles = new ArrayList<>();

		germplasmNamesById.forEach((germplasmId, name) ->
		{
			BrapiMarkerProfile profile = new BrapiMarkerProfile();
			profile.setMarkerprofileDbId(id + "-" + germplasmId);
			profile.setGermplasmDbId(germplasmId);
			profile.setUniqueDisplayName(name);

			profiles.add(profile);
		});

		return profiles;
	}

	private List<String> getDataSetIds()
	{
		List<String> dataSetIds = new ArrayList<>();

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = con.prepareStatement(genotypeDatasets);
			 ResultSet resultSet = statement.executeQuery())
		{
			while (resultSet.next())
				dataSetIds.add(resultSet.getString("id"));
		}
		catch (SQLException e) {e.printStackTrace(); }

		return dataSetIds;
	}

	/**
	 * Return a specific Map specified by the supplied id. Queries a database using the allMarkers query specified
	 * above.
	 *
	 * @param id	The id of the BrapiMarkerProfile to getJson
	 * @return		A BrapiMarkerProfile object identified by id (or null if none exists).
	 */
	public BrapiBaseResource<BrapiMarkerProfileData> getById(Context context, String id, GenotypeEncodingParams params, int currentPage, int pageSize)
	{
		BrapiBaseResource<BrapiMarkerProfileData> result = new BrapiBaseResource<>();
		BrapiMarkerProfileData profileData = new BrapiMarkerProfileData();

		// The markerprofile id is the datasetId separated from the lineId with a hyphen
		String[] tokens = id.split("-");
		String datasetId = tokens[0];
		String germinatebaseId = tokens[1];

		// Get the germplasmName of the germplasm with the given germinatebaseId
		String name = getLineNameFromId(germinatebaseId);

		// If the line doesn't exist in the db return a blank result
		if (!name.isEmpty())
		{
			String hdf5File = Hdf55Utils.getHdf5File(datasetId);
			String folder = context.getParameters().getFirstValue("hdf5-folder");

			List<Map<String, String>> allelesByMarker = new ArrayList<>();

			// We're going to read allele data in from the 2D matrix stored in the
			// HDF5 file for this data set
			try (Hdf5DataExtractor extractor = new Hdf5DataExtractor(new File(folder, hdf5File)))
			{
				List<String> alleles = extractor.getAllelesForLine(name, params);
				List<String> markers = extractor.getMarkers();

				// Link our marker names and allele calls
				for (int i=0; i < markers.size(); i++)
				{
					Map<String, String> map = new HashMap<String, String>();
					map.put(markers.get(i), alleles.get(i));
					allelesByMarker.add(map);
				}

				// Subset the data for the purposes of paging
				int start = DatabaseUtils.getLimitStart(currentPage, pageSize);
				int end = Math.min(start + pageSize, allelesByMarker.size());
				List<Map<String, String>> subset = allelesByMarker.subList(start, end);

				profileData.setMarkerprofileDbId(id);
				profileData.setGermplasmDbId(germinatebaseId);
				profileData.setData(subset);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			result = new BrapiBaseResource<BrapiMarkerProfileData>(profileData, currentPage, pageSize, allelesByMarker.size());
		}

		return result;
	}

	private String getLineNameFromId(String germinatebaseId)
	{
		String name = "";

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = DatabaseUtils.createByIdStatement(con, getNameForGermplasmId, germinatebaseId);
			 ResultSet resultSet = statement.executeQuery())
		{
			if (resultSet.next())
				name = resultSet.getString("name");
		}
		catch (SQLException e) {e.printStackTrace(); }

		return name;
	}
}