package hutton.brapi.data;

import java.sql.*;
import java.util.*;

import hutton.brapi.resource.*;

/**
 * Created by gs40939 on 19/05/2015.
 */
public class MarkerProfileDAOImpl implements MarkerProfileDAO
{
	private final String allMarkers = "select genotypes.allele1, genotypes.allele2, genotypes.marker_id, " +
		"genotypes.germinatebase_id, genotypes.dataset_id, markers.marker_name from genotypes INNER JOIN markers ON " +
		"genotypes.marker_id = markers.id INNER JOIN datasets ON genotypes.dataset_id = datasets.id where " +
		"germinatebase_id=? AND datasets.id=?";

	private final String markerCount = "select COUNT(DISTINCT genotypes.marker_id) AS markerCount, " +
		"genotypes.germinatebase_id, genotypes.dataset_id from genotypes INNER JOIN markers ON genotypes.marker_id = " +
		"markers.id INNER JOIN datasets ON genotypes.dataset_id = datasets.id where germinatebase_id=? AND datasets.id=?";

	/**
	 * Return a specific Map specified by the supplied id. Queries a database using the allMarkers query specified
	 * above.
	 *
	 * @param id	The id of the MarkerProfile to getJson
	 * @return		A MarkerProfile object identified by id (or null if none exists).
	 */
	@Override
	public MarkerProfile getById(String id)
	{
		String[] tokens = id.split("-");
		int datasetId = Integer.parseInt(tokens[0]);
		int germinatebaseId = Integer.parseInt(tokens[1]);

		try (Connection con = Database.INSTANCE.getDataSource().getConnection())
		{
			// Get the basic information on the map
			PreparedStatement markerProfileStatement = con.prepareStatement(allMarkers);
			markerProfileStatement.setInt(1, germinatebaseId);
			markerProfileStatement.setInt(2, datasetId);
			MarkerProfile profile = getProfile(markerProfileStatement.executeQuery());

			return profile;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	// Given a ResultSet generated from the allMarkers query, returns a MarkerProfile object which has been initialized
	// with the information from the ResultSet
	private MarkerProfile getProfile(ResultSet resultSet) throws SQLException
	{
		MarkerProfile profile = new MarkerProfile();
		HashMap<String, String> alleles = new HashMap<>();
		while (resultSet.next())
		{
			profile.setGermplasmId(resultSet.getInt("germinatebase_id"));
			profile.setId(resultSet.getInt("dataset_id") + "-" + resultSet.getInt("germinatebase_id"));
			alleles.put(resultSet.getString("marker_name"), resultSet.getString("allele1") + resultSet.getString("allele2"));
		}
		profile.setData(alleles);

		return profile;
	}

	/**
	 * Return a count of the Markers which are contained within the MarkerProfile in the form of a MarkerProfileCount.
	 * Uses the markerCount query defined above to getJson the data from the database.
	 *
	 * @param id	The id of the MarkerProfile for which a count is desired.
	 * @return		The MarkerProfileCount for the MarkerProfile with the given id.
	 */
	@Override
	public MarkerProfileCount getCountById(String id)
	{
		String[] tokens = id.split("-");
		int datasetId = Integer.parseInt(tokens[0]);
		int germinatebaseId = Integer.parseInt(tokens[1]);

		try (Connection con = Database.INSTANCE.getDataSource().getConnection())
		{
			// Get the basic information on the map
			PreparedStatement markerProfileStatement = con.prepareStatement(markerCount);
			markerProfileStatement.setInt(1, germinatebaseId);
			markerProfileStatement.setInt(2, datasetId);
			MarkerProfileCount profile = getProfileCount(markerProfileStatement.executeQuery());

			return profile;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	// Given a ResultSet generated from the markerCount query, returns a MarkerProfileCount object which has been
	// initialized with the information from the ResultSet
	private MarkerProfileCount getProfileCount(ResultSet resultSet) throws SQLException
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
