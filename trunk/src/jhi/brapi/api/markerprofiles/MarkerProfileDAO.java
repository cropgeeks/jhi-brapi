package jhi.brapi.api.markerprofiles;

import java.io.File;
import java.io.IOException;
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
			String hdf5File = HDF5Utils.getHdf5File(id);

			String folder = context.getParameters().getFirstValue("hdf5-folder");

			try(Hdf5DataExtractor extractor = new Hdf5DataExtractor(new File(folder, hdf5File)))
			{
				List<String> lineNames = extractor.getLines();
				LinkedHashMap<String, String> map = HDF5Utils.getGermplasmMappingForNames(lineNames);

				markerProfiles.addAll(getProfiles(id, map));
			}
			catch (Exception e) { e.printStackTrace(); }
		});

		result = new BrapiListResource<BrapiMarkerProfile>(markerProfiles, currentPage, pageSize, markerProfiles.size());

		return result;
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
	public BrapiBaseResource<BrapiMarkerProfileData> getById(String id, GenotypeEncodingParams params)
	{
		BrapiBaseResource<BrapiMarkerProfileData> result = new BrapiBaseResource<>();

		String[] tokens = id.split("-");
		int datasetId = Integer.parseInt(tokens[0]);
		int germinatebaseId = Integer.parseInt(tokens[1]);

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement markerProfileStatement = createByIdStatement(con, allMarkers, germinatebaseId, datasetId);
			 ResultSet resultSet = markerProfileStatement.executeQuery())
		{
			result = new BrapiBaseResource<>(getProfile(resultSet, params));
		}
		catch (SQLException e) { e.printStackTrace(); }

		return result;
	}

	private PreparedStatement createByIdStatement(Connection con, String query, int germinatebaseId, int datasetId)
		throws SQLException
	{
		// Get the basic information on the map
		PreparedStatement statement = con.prepareStatement(query);
		statement.setInt(1, germinatebaseId);
		statement.setInt(2, datasetId);

		return statement;
	}

	private List<BrapiMarkerProfile> getProfiles(String id, LinkedHashMap<String, String> germplasmNamesById)
	{
		List<BrapiMarkerProfile> profiles = new ArrayList<>();

		germplasmNamesById.forEach((germplasmId, name) ->
		{
			BrapiMarkerProfile profile = new BrapiMarkerProfile();
			profile.setMarkerProfileDbId(id + "-" + germplasmId);
			profile.setGermplasmDbId(germplasmId);
			profile.setUniqueDisplayName(name);

			profiles.add(profile);
		});

		return profiles;
	}

	// Given a ResultSet generated from the allMarkers query, returns a BrapiMarkerProfile object which has been initialized
	// with the information from the ResultSet
	private BrapiMarkerProfileData getProfile(ResultSet resultSet, GenotypeEncodingParams params)
		throws SQLException
	{
		BrapiMarkerProfileData profile = new BrapiMarkerProfileData();
		HashMap<String, String> alleles = new HashMap<>();
		while (resultSet.next())
		{
			profile.setGermplasmId(resultSet.getString("germinatebase_id"));
			profile.setMarkerprofileId(resultSet.getString("markerprofile_id"));
			// TODO: Make this conform with the new Allele encoding
			String allele1 = resultSet.getString("allele1");
			String allele2 = resultSet.getString("allele2");
			String encodedAllele = GenotypeEncodingUtils.getString(allele1, allele2, params);
			alleles.put(resultSet.getString("marker_name"), encodedAllele);
		}
		profile.setData(alleles);

		return profile;
	}
}