package jhi.brapi.data;

import jhi.brapi.resource.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gs40939 on 19/05/2015.
 */
public class MarkerProfileDAO
{
	private final String allMarkers = "select genotypes.allele1, genotypes.allele2, genotypes.marker_id, " +
		"genotypes.germinatebase_id, genotypes.dataset_id, CONCAT(genotypes.dataset_id, '-', genotypes.germinatebase_id) " +
		"AS markerprofile_id, markers.marker_name from genotypes INNER JOIN markers ON genotypes.marker_id = markers.id " +
		"INNER JOIN datasets ON genotypes.dataset_id = datasets.id where germinatebase_id=? AND datasets.id=?";

	private final String allMarkerProfiles = "SELECT DISTINCT markerprofile_id, germinatebase_id FROM (select " +
		"genotypes.marker_id, genotypes.germinatebase_id, genotypes.dataset_id, markers.marker_name, " +
		"CONCAT(genotypes.dataset_id, '-', genotypes.germinatebase_id) AS markerprofile_id from genotypes INNER JOIN " +
		"markers ON genotypes.marker_id = markers.id INNER JOIN datasets ON genotypes.dataset_id = datasets.id) AS " +
		"markerprofiles LIMIT ?, ?";

	private final String allMarkerProfilesCount = "SELECT COUNT(DISTINCT markerprofile_id) AS total_count FROM (select " +
		"genotypes.marker_id, genotypes.germinatebase_id, genotypes.dataset_id, markers.marker_name, " +
		"CONCAT(genotypes.dataset_id, '-', genotypes.germinatebase_id) AS markerprofile_id from genotypes INNER JOIN " +
		"markers ON genotypes.marker_id = markers.id INNER JOIN datasets ON genotypes.dataset_id = datasets.id) AS " +
		"markerprofiles";


	// No longer used / not in the API... can ressurrect if need be
	private final String markerCount = "select COUNT(DISTINCT genotypes.marker_id) AS markerCount, " +
			"genotypes.germinatebase_id, genotypes.dataset_id from genotypes INNER JOIN markers ON genotypes.marker_id = " +
			"markers.id INNER JOIN datasets ON genotypes.dataset_id = datasets.id";

	public BasicResource<DataResult<BrapiMarkerProfile>> getAll(int currentPage, int pageSize)
	{
		BasicResource<DataResult<BrapiMarkerProfile>> result = new BasicResource<>();

		long totalCount = DatabaseUtils.getTotalCount(allMarkerProfilesCount);

		if (totalCount != -1)
		{
			try (Connection con = Database.INSTANCE.getDataSource().getConnection();
				 PreparedStatement markerProfileStatement = DatabaseUtils.createLimitStatement(con, allMarkerProfiles, currentPage, pageSize);
				 ResultSet resultSet = markerProfileStatement.executeQuery())
			{
				result = new BasicResource<DataResult<BrapiMarkerProfile>>(new DataResult(getProfiles(resultSet)), currentPage, pageSize, totalCount);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
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
	public BasicResource<MarkerProfileData> getById(String id)
	{
		BasicResource<MarkerProfileData> result = new BasicResource<>();

		String[] tokens = id.split("-");
		int datasetId = Integer.parseInt(tokens[0]);
		int germinatebaseId = Integer.parseInt(tokens[1]);

		try (Connection con = Database.INSTANCE.getDataSource().getConnection();
			 PreparedStatement markerProfileStatement = createByIdStatement(con, allMarkers, germinatebaseId, datasetId);
			 ResultSet resultSet = markerProfileStatement.executeQuery())
		{
			result = new BasicResource<>(getProfile(resultSet));
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
			profile.setMarkerprofileDbId(resultSet.getString("markerprofile_id"));
			profile.setGermplasmDbId(Integer.parseInt(resultSet.getString("germinatebase_id")));

			profiles.add(profile);
		}

		return profiles;
	}

	// Given a ResultSet generated from the allMarkers query, returns a BrapiMarkerProfile object which has been initialized
	// with the information from the ResultSet
	private MarkerProfileData getProfile(ResultSet resultSet)
		throws SQLException
	{
		MarkerProfileData profile = new MarkerProfileData();
		HashMap<String, String> alleles = new HashMap<>();
		while (resultSet.next())
		{
			profile.setGermplasmId(resultSet.getString("germinatebase_id"));
			profile.setMarkerprofileId(resultSet.getString("markerprofile_id"));
			alleles.put(resultSet.getString("marker_name"), resultSet.getString("allele1") + resultSet.getString("allele2"));
		}
		profile.setData(alleles);

		return profile;
	}
}
