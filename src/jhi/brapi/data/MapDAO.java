package jhi.brapi.data;

import jhi.brapi.resource.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Specifies the public interface which any BrapiGenomeMap data accessing classes must implement.
 */
public class MapDAO
{
	// Selects the id, description and created_date from the MapList table, and carries out an inner join with the
	// mapdefinitions table so we can get a count of markers (by marker_id) and chromosomes.
	private final String mapsQuery = "SELECT maps.id, maps.description, maps.created_on, COUNT(DISTINCT " +
			"mapdefinitions.marker_id) AS markercount, COUNT(DISTINCT mapdefinitions.chromosome) AS chromosomeCount " +
			"from maps INNER JOIN mapdefinitions ON maps.id = mapdefinitions.map_id LIMIT ?, ?";

	private final String mapsCountQuery = "SELECT COUNT(1) AS total_count from maps";

	private final String detailQuery = "SELECT maps.id, maps.description, maps.created_on, COUNT(DISTINCT " +
			"mapdefinitions.marker_id) AS markercount, COUNT(DISTINCT mapdefinitions.chromosome) AS chromosomeCount from " +
			"maps INNER JOIN mapdefinitions ON maps.id = mapdefinitions.map_id WHERE maps.id=?";

	private final String entriesQuery = "SELECT maps.id, mapdefinitions.marker_id, mapdefinitions.chromosome, " +
			"mapdefinitions.definition_start, markers.marker_name from maps INNER JOIN mapdefinitions ON maps.id = " +
			"mapdefinitions.map_id INNER JOIN markers ON mapdefinitions.marker_id = markers.id WHERE maps.id=?";

	private final String detailByChromQuery = "SELECT maps.id, maps.description, maps.created_on, COUNT(DISTINCT " +
			"mapdefinitions.marker_id) AS markercount, COUNT(DISTINCT mapdefinitions.chromosome) AS chromosomeCount from " +
			"maps INNER JOIN mapdefinitions ON maps.id = mapdefinitions.map_id WHERE maps.id=? AND mapdefinitions.chromosome=?";

	private final String entriesByChromQuery = "SELECT maps.id, mapdefinitions.marker_id, mapdefinitions.chromosome, " +
			"mapdefinitions.definition_start, markers.marker_name from maps INNER JOIN mapdefinitions ON maps.id = " +
			"mapdefinitions.map_id INNER JOIN markers ON mapdefinitions.marker_id = markers.id WHERE maps.id=? AND mapdefinitions.chromosome=?";

	private final String linkageGroupQuery = "SELECT map_id, chromosome, MAX(definition_end) AS max, COUNT(marker_id) " +
			"AS number_markers FROM mapdefinitions WHERE map_id=? GROUP BY chromosome";

	private final String mapMarkersQuery = "SELECT map_id, chromosome, definition_start, marker_id, markers.marker_name" +
			" FROM mapdefinitions JOIN markers ON markers.id = mapdefinitions.marker_id where map_id=? LIMIT ?, ?";

	private final String mapMarkersCountQuery = "SELECT COUNT(1) AS total_count, map_id, chromosome, definition_start, marker_id, markers.marker_name" +
		" FROM mapdefinitions JOIN markers ON markers.id = mapdefinitions.marker_id where map_id=?";

	/**
	 * Queries the database (using mapQuery defined above) for the complete list of Maps which the database holds.
	 *
	 * @return A MapList object which is a wrapper around a List of BrapiGenomeMap objects.
	 */
	public BasicResource<DataResult<BrapiGenomeMap>> getAll(int currentPage, int pageSize)
	{
		BasicResource<DataResult<BrapiGenomeMap>> result = new BasicResource<>();

		long totalCount = DatabaseUtils.getTotalCount(mapsCountQuery);

		if (totalCount != -1)
		{
			try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
				 PreparedStatement statement = DatabaseUtils.createLimitStatement(con, mapsQuery, currentPage, pageSize);
				 ResultSet resultSet = statement.executeQuery())
			{
				result = new BasicResource<DataResult<BrapiGenomeMap>>(new DataResult<>(getMapsFromResultSet(resultSet)), currentPage, pageSize, totalCount);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

		return result;
	}

	// Takes a resultSet and iterates over it adding each map object in turn to a list of BrapiGenomeMap objects, which is then
	// put in the MapList wrapper (easing Jackson translation of the object to JSON and back).
	private List<BrapiGenomeMap> getMapsFromResultSet(ResultSet resultSet)
		throws SQLException
	{
		List<BrapiGenomeMap> maps = new ArrayList<>();

		while (resultSet.next())
		{
			BrapiGenomeMap map = new BrapiGenomeMap();
			map.setMapId(resultSet.getInt("id"));
			map.setName(resultSet.getString("description"));
			map.setPublishedDate(resultSet.getDate("created_on"));
			map.setLinkageGroupCount(resultSet.getInt("chromosomeCount"));
			map.setMarkerCount(resultSet.getInt("markerCount"));
			maps.add(map);
		}

		return maps;
	}

	/**
	 * Queries the database (using detailQuery and entiresQuery defined above) for the information on the map identified
	 * by the id given and the entries (MapEntry objects) which make up the detail of the map.
	 *
	 * @param id	The id of the
	 * @return 		A BrapiMapMetaData object which itself holds a List of MapEntry objects. Or null if no
	 * 				BrapiMapMetaData exists for the supplied id.
	 */
	public BasicResource<BrapiMapMetaData> getById(String id)
	{
		BasicResource<BrapiMapMetaData> result = new BasicResource<>();
		BrapiMapMetaData mapDetail = new BrapiMapMetaData();

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement mapStatement = DatabaseUtils.createByIdStatement(con, detailQuery, id);
			 ResultSet resultSet = mapStatement.executeQuery())
		{
			mapDetail = getMapDetailFromResultSet(resultSet);
		}
		catch (SQLException e) { e.printStackTrace(); }

		// Quit now if we didn't find a map with the given ID
		if (mapDetail != null)
		{
			// Get the list of entries for the map (effectively the markers)
			try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
				 PreparedStatement entriesStatement = DatabaseUtils.createByIdStatement(con, linkageGroupQuery, id);
				 ResultSet resultSet = entriesStatement.executeQuery())
			{
				List<LinkageGroup> linkageGroups = getLinkageGroupsFromResultSet(resultSet);
				mapDetail.setLinkageGroups(linkageGroups);

				result = new BasicResource<BrapiMapMetaData>(mapDetail);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

		return result;
	}

	// Takes a ResultSet which should represent the result of the detailQuery defined above and returns a new BrapiMapMetaData
	// object which has been initialized with the values from the ResultSet
	private BrapiMapMetaData getMapDetailFromResultSet(ResultSet resultSet)
		throws SQLException
	{
		if (resultSet.first())
		{
			// Passing an ID that doesn't match a Map will still return a row
			// from the query (containing count columns of zero), so we need an
			// extra check to see if there's a Map in the result or not
			if (resultSet.getString("description") != null)
			{
				BrapiMapMetaData mapDetail = new BrapiMapMetaData();
				mapDetail.setName(resultSet.getString("description"));

				return mapDetail;
			}
		}

		return null;
	}

	// Takes a ResultSet which should represent the result of the linkageGroupsQuery defined above and returns a List of
	// LinkageGroup objects, each of which has been initialized with the values from the ResultSet
	private List<LinkageGroup> getLinkageGroupsFromResultSet(ResultSet resultSet)
		throws SQLException
	{
		List<LinkageGroup> linkageGroups = new ArrayList<>();

		while (resultSet.next())
		{
			LinkageGroup linkageGroup = new LinkageGroup();
			linkageGroup.setLinkageGroupId(resultSet.getString("chromosome"));
			linkageGroup.setMaxPosition(resultSet.getInt("max"));
			linkageGroup.setNumberMarkers(resultSet.getInt("number_markers"));
			linkageGroups.add(linkageGroup);
		}

		return linkageGroups;
	}

	/**
	 * Queries the database (using detailQuery and entiresQuery defined above) for the information on the map identified
	 * by the id given and the entries (MapEntry objects) which make up the detail of the map.
	 *
	 * @param id	The id of the
	 * @return 		A BrapiMapMetaData object which itself holds a List of MapEntry objects. Or null if no
	 * 				BrapiMapMetaData exists for the supplied id.
	 */
	public BrapiMapMetaData getByIdAndChromosome(int id, String chromosome)
	{
		BrapiMapMetaData mapDetail = new BrapiMapMetaData();
		try (Connection con = jhi.brapi.data.Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement mapStatement = createDetailByChromStatement(con, id, chromosome);
			 ResultSet resultSet = mapStatement.executeQuery())
		{
			mapDetail = getMapDetailFromResultSet(resultSet);
		}
		catch (SQLException e) { e.printStackTrace(); }

		return mapDetail;
	}

	private PreparedStatement createByChromStatement(Connection con, String query, int id, String chromosome)
		throws SQLException
	{
		PreparedStatement statement = con.prepareStatement(query);
		// Get the basic information on the map
		statement.setInt(1, id);
		statement.setString(2, chromosome);

		return statement;
	}

	private PreparedStatement createDetailByChromStatement(Connection con, int id, String chromosome)
		throws SQLException
	{
		return createByChromStatement(con, detailByChromQuery, id, chromosome);
	}

	private PreparedStatement createEntriesByChromStatement(Connection con, int id, String chromosome)
		throws SQLException
	{
		return createByChromStatement(con, entriesByChromQuery, id, chromosome);
	}

	public BasicResource<DataResult<BrapiMarkerPosition>> getByIdMarkers(String id, String[] chromosomes, int currentPage, int pageSize)
	{
		BasicResource<DataResult<BrapiMarkerPosition>> result = new BasicResource<>();

		long totalCount = DatabaseUtils.getTotalCountById(mapMarkersCountQuery, id);

		if (totalCount != -1)
		{
			try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
				 PreparedStatement mapStatement = createByIdStatementMarkers(con, mapMarkersQuery, id, chromosomes, currentPage, pageSize);
				 ResultSet resultSet = mapStatement.executeQuery())
			{
				result = new BasicResource<DataResult<BrapiMarkerPosition>>(new DataResult(getMapMarkersListFromResultSet(resultSet)), currentPage, pageSize, totalCount);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

		return result;
	}

	private PreparedStatement createByIdStatementMarkers(Connection con, String query, String id, String[] chromosomes, int currentPage, int pageSize)
		throws SQLException
	{
		// Tweak the statement to include optional chromosome filters
		for (int i = 0; i < chromosomes.length; i++)
		{
			if (i == 0)
				query += " AND mapdefinitions.chromosome=?";
			else
				query += " OR mapdefinitions.chromosome=?";
		}

		PreparedStatement statement = con.prepareStatement(query);
		// Get the basic information on the map
		statement.setString(1, id);
		int current = 2;
		for (int i = 0; i < chromosomes.length; i++)
		{
			statement.setString(i + 2, chromosomes[i]);
			current = i+2;
		}

		statement.setInt(current++, PaginationUtils.getLowLimit(currentPage, pageSize));
		statement.setInt(current++, pageSize);

		return statement;
	}

	// Takes a ResultSet which should represent the result of the linkageGroupsQuery defined above and returns a List of
	// LinkageGroup objects, each of which has been initialized with the values from the ResultSet
	private ArrayList<BrapiMarkerPosition> getMapMarkersListFromResultSet(ResultSet resultSet)
		throws SQLException
	{
		ArrayList<BrapiMarkerPosition> mapMarkers = new ArrayList<>();

		while (resultSet.next())
		{
			BrapiMarkerPosition mapMarker = new BrapiMarkerPosition();
			mapMarker.setMarkerId(resultSet.getString("marker_id"));
			mapMarker.setMarkerName(resultSet.getString("marker_name"));
			mapMarker.setLocation(resultSet.getString("definition_start"));
			mapMarker.setLinkageGroup(resultSet.getString("chromosome"));
			mapMarkers.add(mapMarker);
		}

		return mapMarkers;
	}
}