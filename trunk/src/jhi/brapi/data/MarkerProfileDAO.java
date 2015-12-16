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
			"genotypes.germinatebase_id, genotypes.dataset_id, markers.marker_name from genotypes INNER JOIN markers ON " +
			"genotypes.marker_id = markers.id INNER JOIN datasets ON genotypes.dataset_id = datasets.id where " +
			"germinatebase_id=? AND datasets.id=?";

	private final String allMarkerProfiles = "select genotypes.marker_id, genotypes.germinatebase_id, " +
			"genotypes.dataset_id, markers.marker_name from genotypes INNER JOIN markers ON " +
			"genotypes.marker_id = markers.id INNER JOIN datasets ON genotypes.dataset_id = datasets.id";

	private final String markerCount = "select COUNT(DISTINCT genotypes.marker_id) AS markerCount, " +
			"genotypes.germinatebase_id, genotypes.dataset_id from genotypes INNER JOIN markers ON genotypes.marker_id = " +
			"markers.id INNER JOIN datasets ON genotypes.dataset_id = datasets.id";

	public List<BrapiMarkerProfile> getAll()
	{
		try (Connection con = Database.INSTANCE.getDataSource().getConnection();
			 PreparedStatement markerProfileStatement = con.prepareStatement(allMarkerProfiles);
			 ResultSet resultSet = markerProfileStatement.executeQuery())
		{
			return getProfiles(resultSet);
		}
		catch (SQLException e) { e.printStackTrace(); }

		return null;
	}

	/**
	 * Return a specific Map specified by the supplied id. Queries a database using the allMarkers query specified
	 * above.
	 *
	 * @param id	The id of the BrapiMarkerProfile to getJson
	 * @return		A BrapiMarkerProfile object identified by id (or null if none exists).
	 */
	public MarkerProfileData getById(String id)
	{
		String[] tokens = id.split("-");
		int datasetId = Integer.parseInt(tokens[0]);
		int germinatebaseId = Integer.parseInt(tokens[1]);

		try (Connection con = Database.INSTANCE.getDataSource().getConnection();
			 PreparedStatement markerProfileStatement = createByIdStatement(con, allMarkers, germinatebaseId, datasetId);
			 ResultSet resultSet = markerProfileStatement.executeQuery())
		{
			MarkerProfileData profile = getProfile(resultSet);

			return profile;
		}
		catch (SQLException e) { e.printStackTrace(); }

		return null;
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
		HashMap<String, BrapiMarkerProfile> markerProfiles = new HashMap<>();
		HashMap<String, Integer> markerProfileCounts = new HashMap<>();

		while (resultSet.next())
		{
			String markerprofileId = resultSet.getInt("dataset_id") + "-" + resultSet.getInt("germinatebase_id");
			String germplasmId = resultSet.getString("germinatebase_id");
			BrapiMarkerProfile profile = markerProfiles.get(markerprofileId);
			if (profile == null)
			{
				profile = new BrapiMarkerProfile();
				profile.setMarkerprofileId(markerprofileId);
				profile.setGermplasmId(germplasmId);
				markerProfiles.put(markerprofileId, profile);
				markerProfileCounts.put(markerprofileId, 1);
				System.out.println();
			}
			else
			{
				int count = markerProfileCounts.get(markerprofileId);
				markerProfileCounts.put(markerprofileId, ++count);
				markerProfiles.get(markerprofileId).setResultCount(count);
			}
		}

		return new ArrayList<>(markerProfiles.values());
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
			profile.setGermplasmId(resultSet.getInt("germinatebase_id"));
			profile.setMarkerprofileId(resultSet.getInt("dataset_id") + "-" + resultSet.getInt("germinatebase_id"));
			alleles.put(resultSet.getString("marker_name"), resultSet.getString("allele1") + resultSet.getString("allele2"));
		}
		profile.setData(alleles);

		return profile;
	}

	/**
	 * Return a count of the Markers which are contained within the BrapiMarkerProfile in the form of a MarkerProfileCount.
	 * Uses the markerCount query defined above to getJson the data from the database.
	 *
	 * @param id	The id of the BrapiMarkerProfile for which a count is desired.
	 * @return		The MarkerProfileCount for the BrapiMarkerProfile with the given id.
	 */
	public MarkerProfileCount getCountById(String id)
	{
		String[] tokens = id.split("-");
		int datasetId = Integer.parseInt(tokens[0]);
		int germinatebaseId = Integer.parseInt(tokens[1]);

		try (Connection con = Database.INSTANCE.getDataSource().getConnection();
			 PreparedStatement markerProfileStatement = createByIdStatement(con, markerCount, germinatebaseId, datasetId);
			 ResultSet resultSet = markerProfileStatement.executeQuery())
		{
			MarkerProfileCount profile = getProfileCount(resultSet);

			return profile;
		}
		catch (SQLException e) { e.printStackTrace(); }

		return null;
	}

	// Given a ResultSet generated from the markerCount query, returns a MarkerProfileCount object which has been
	// initialized with the information from the ResultSet
	private MarkerProfileCount getProfileCount(ResultSet resultSet)
		throws SQLException
	{
		MarkerProfileCount profileCount = new MarkerProfileCount();
		while (resultSet.next())
		{
			profileCount.setMarkerProfileId(resultSet.getInt("dataset_id") + "-" + resultSet.getInt("germinatebase_id"));
			profileCount.setGermplasmId(resultSet.getInt("germinatebase_id"));
			profileCount.setResultCount(resultSet.getInt("markerCount"));
		}

		return profileCount;
	}
}
