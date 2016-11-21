package jhi.brapi.api.markerprofiles;

import java.sql.*;
import java.util.*;

import jhi.brapi.api.*;
import jhi.brapi.util.*;

public class MarkerProfileDAO
{
	private final String allMarkers = "select genotypes.allele1, genotypes.allele2, genotypes.marker_id, " +
		"genotypes.germinatebase_id, genotypes.dataset_id, CONCAT(genotypes.dataset_id, '-', genotypes.germinatebase_id) " +
		"AS markerprofile_id, markers.marker_name from genotypes INNER JOIN markers ON genotypes.marker_id = markers.id " +
		"INNER JOIN datasets ON genotypes.dataset_id = datasets.id where germinatebase_id=? AND datasets.id=?";

	private final String allMarkerProfiles = "SELECT SQL_CALC_FOUND_ROWS DISTINCT markerprofile_id, germinatebase_id, " +
		"germinatebase_name FROM ( SELECT genotypes.marker_id, genotypes.germinatebase_id, genotypes.dataset_id, " +
		"markers.marker_name, CONCAT( genotypes.dataset_id, '-', genotypes.germinatebase_id ) AS markerprofile_id, " +
		"germinatebase.id AS germinatebase_name FROM genotypes INNER JOIN markers ON genotypes.marker_id = markers.id " +
		"INNER JOIN datasets ON genotypes.dataset_id = datasets.id INNER JOIN germinatebase ON genotypes.germinatebase_id " +
		"= germinatebase.id ) AS markerprofiles WHERE %s LIMIT ?, ?";

	public BrapiListResource<BrapiMarkerProfile> getAll(LinkedHashMap<String, String> params, int currentPage, int pageSize)
	{
		BrapiListResource<BrapiMarkerProfile> result = new BrapiListResource<>();

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement markerProfileStatement = DatabaseUtils.createParameterizedLimitStatement(con, allMarkerProfiles, params, currentPage, pageSize);
			 ResultSet resultSet = markerProfileStatement.executeQuery())
		{
			List<BrapiMarkerProfile> markerProfiles = getProfiles(resultSet);
			long totalCount = DatabaseUtils.getTotalCount(markerProfileStatement);

			result = new BrapiListResource<BrapiMarkerProfile>(markerProfiles, currentPage, pageSize, totalCount);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return result;
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

	// Given a ResultSet generated from the allMarkers query, returns a BrapiMarkerProfile object which has been initialized
	// with the information from the ResultSet
	private List<BrapiMarkerProfile> getProfiles(ResultSet resultSet)
		throws SQLException
	{
		List<BrapiMarkerProfile> profiles = new ArrayList<>();

		while (resultSet.next())
		{
			BrapiMarkerProfile profile = new BrapiMarkerProfile();
			profile.setMarkerProfileDbId(resultSet.getString("markerprofile_id"));
			profile.setGermplasmDbId(resultSet.getString("germinatebase_id"));
			profile.setUniqueDisplayName(resultSet.getString("germinatebase_name"));

			profiles.add(profile);
		}

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