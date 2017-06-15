package jhi.brapi.api.genomemaps;

import java.sql.*;
import java.util.*;

import jhi.brapi.api.*;
import jhi.brapi.util.*;

/**
 * Specifies the public interface which any BrapiGenomeMap data accessing classes must implement.
 */
public class MapDAO
{
	// Selects the id, description and created_date from the MapList table, and carries out an inner join with the
	// mapdefinitions table so we can get a count of markers (by marker_id) and chromosomes.
	private final String mapsQuery = "SELECT SQL_CALC_FOUND_ROWS maps.id, maps.description, maps.created_on, COUNT(DISTINCT " +
			"mapdefinitions.marker_id) AS markercount, COUNT(DISTINCT mapdefinitions.chromosome) AS chromosomeCount " +
			"from maps INNER JOIN mapdefinitions ON maps.id = mapdefinitions.map_id GROUP BY maps.id LIMIT ?, ?";

	private final String detailQuery = "SELECT maps.id, maps.description, maps.created_on, COUNT(DISTINCT " +
			"mapdefinitions.marker_id) AS markercount, COUNT(DISTINCT mapdefinitions.chromosome) AS chromosomeCount from " +
			"maps INNER JOIN mapdefinitions ON maps.id = mapdefinitions.map_id WHERE maps.id=? GROUP BY maps.id";

	private final String detailByChromQuery = "SELECT maps.id, maps.description, maps.created_on, COUNT(DISTINCT " +
			"mapdefinitions.marker_id) AS markercount, COUNT(DISTINCT mapdefinitions.chromosome) AS chromosomeCount from " +
			"maps INNER JOIN mapdefinitions ON maps.id = mapdefinitions.map_id WHERE maps.id=? AND mapdefinitions.chromosome=? GROUP BY maps.id";

	private final String linkageGroupQuery = "SELECT map_id, chromosome, MAX(definition_end) AS max, COUNT(marker_id) " +
			"AS number_markers FROM mapdefinitions WHERE map_id=? GROUP BY chromosome";

	private final String mapMarkersQuery = "SELECT SQL_CALC_FOUND_ROWS map_id, chromosome, definition_start, marker_id, markers.marker_name" +
			" FROM mapdefinitions JOIN markers ON markers.id = mapdefinitions.marker_id where map_id=? %s LIMIT ?, ?";

	private final String mapMarkerChromosomeRangeQuery = "SELECT SQL_CALC_FOUND_ROWS map_id, definition_start, marker_id, " +
		"markers.marker_name FROM mapdefinitions JOIN markers ON markers.id = mapdefinitions.marker_id where map_id " +
		"= ? AND mapdefinitions.chromosome = ? %s LIMIT ?, ?";

	/**
	 * Queries the database (using mapQuery defined above) for the complete list of Maps which the database holds.
	 *
	 * @return A MapList object which is a wrapper around a List of BrapiGenomeMap objects.
	 */
	public BrapiListResource<BrapiGenomeMap> getAll(int currentPage, int pageSize)
	{
		BrapiListResource<BrapiGenomeMap> result = new BrapiListResource<>();

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = DatabaseUtils.createLimitStatement(con, mapsQuery, currentPage, pageSize);
			 ResultSet resultSet = statement.executeQuery())
		{
			List<BrapiGenomeMap> genomeMaps = getMapsFromResultSet(resultSet);
			long totalCount = DatabaseUtils.getTotalCount(statement);

			result = new BrapiListResource<BrapiGenomeMap>(genomeMaps, currentPage, pageSize, totalCount);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
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
			map.setMapDbId(resultSet.getString("id"));
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
	public BrapiBaseResource<BrapiMapMetaData> getById(String id)
	{
		BrapiBaseResource<BrapiMapMetaData> result = new BrapiBaseResource<>();
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
			try (Connection con = jhi.brapi.util.Database.INSTANCE.getDataSourceGerminate().getConnection();
				 PreparedStatement entriesStatement = DatabaseUtils.createByIdStatement(con, linkageGroupQuery, id);
				 ResultSet resultSet = entriesStatement.executeQuery())
			{
				List<BrapiLinkageGroup> linkageGroups = getLinkageGroupsFromResultSet(resultSet);
				mapDetail.setLinkageGroups(linkageGroups);

				result = new BrapiBaseResource<BrapiMapMetaData>(mapDetail);
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
	// BrapiLinkageGroup objects, each of which has been initialized with the values from the ResultSet
	private List<BrapiLinkageGroup> getLinkageGroupsFromResultSet(ResultSet resultSet)
		throws SQLException
	{
		List<BrapiLinkageGroup> linkageGroups = new ArrayList<>();

		while (resultSet.next())
		{
			BrapiLinkageGroup linkageGroup = new BrapiLinkageGroup();
			linkageGroup.setLinkageGroupId(resultSet.getString("chromosome"));
			linkageGroup.setMaxPosition(resultSet.getDouble("max"));
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
		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
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

	public BrapiListResource<BrapiMarkerPosition> getByIdMarkers(String id, List<String> chromosomes, int currentPage, int pageSize)
	{
		BrapiListResource<BrapiMarkerPosition> result = new BrapiListResource<>();

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement mapStatement = createByIdStatementMarkers(con, mapMarkersQuery, id, chromosomes == null ? new String[0] : chromosomes.toArray(new String[chromosomes.size()]), currentPage, pageSize);
			 ResultSet resultSet = mapStatement.executeQuery())
		{
			List<BrapiMarkerPosition> markerPositions = getMapMarkersListFromResultSet(resultSet);
			long totalCount = DatabaseUtils.getTotalCount(mapStatement);

			result = new BrapiListResource<BrapiMarkerPosition>(markerPositions, currentPage, pageSize, totalCount);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return result;
	}

	private PreparedStatement createByIdStatementMarkers(Connection con, String query, String id, String[] chromosomes, int currentPage, int pageSize)
		throws SQLException
	{
		// Tweak the statement to include optional chromosome filters#

		String mapBits = "";
		for (int i = 0; i < chromosomes.length; i++)
		{
			if (i == 0)
				mapBits += " AND (mapdefinitions.chromosome=?";
			else
				mapBits += " OR mapdefinitions.chromosome=?";

			if(i == chromosomes.length - 1)
				mapBits += ")";
		}

		String formatted = String.format(query, mapBits);

		PreparedStatement statement = con.prepareStatement(formatted);
		// Get the basic information on the map
		int position = 1;
		statement.setString(position++, id);
		for (int i = 0; i < chromosomes.length; i++)
		{
			statement.setString(position++, chromosomes[i]);
		}

		statement.setInt(position++, DatabaseUtils.getLimitStart(currentPage, pageSize));
		statement.setInt(position++, pageSize);

		return statement;
	}

	// Takes a ResultSet which should represent the result of the linkageGroupsQuery defined above and returns a List of
	// BrapiLinkageGroup objects, each of which has been initialized with the values from the ResultSet
	private ArrayList<BrapiMarkerPosition> getMapMarkersListFromResultSet(ResultSet resultSet)
		throws SQLException
	{
		ArrayList<BrapiMarkerPosition> mapMarkers = new ArrayList<>();

		while (resultSet.next())
		{
			BrapiMarkerPosition mapMarker = new BrapiMarkerPosition();
			mapMarker.setMarkerDbId(resultSet.getString("marker_id"));
			mapMarker.setMarkerName(resultSet.getString("marker_name"));
			mapMarker.setLocation(resultSet.getString("definition_start"));
			mapMarker.setLinkageGroup(resultSet.getString("chromosome"));
			mapMarkers.add(mapMarker);
		}

		return mapMarkers;
	}

	public BrapiListResource<BrapiLinkageGroupMarker> getByIdLinkageGroupMarkersRange(String id, String chromosome, double min, double max, int currentPage, int pageSize)
	{
		BrapiListResource<BrapiLinkageGroupMarker> result = new BrapiListResource<>();

		try (Connection con = jhi.brapi.util.Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement mapStatement = createByChromStatement(con, mapMarkerChromosomeRangeQuery, id, chromosome, min, max, currentPage, pageSize);
			 ResultSet resultSet = mapStatement.executeQuery())
		{
			ArrayList<BrapiLinkageGroupMarker> markers = getLinkageGroupMarkersFromResultSet(resultSet);
			long totalCount = DatabaseUtils.getTotalCount(mapStatement);

			result = new BrapiListResource<BrapiLinkageGroupMarker>(markers, currentPage, pageSize, totalCount);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return result;
	}

	private PreparedStatement createByChromStatement(Connection con, String query, String id, String chromosome, double min, double max, int currentPage, int pageSize)
		throws SQLException
	{
		String minS = " AND mapdefinitions.definition_start >= ?";
		String maxS = " AND mapdefinitions.definition_end <= ?";

		StringBuilder builder = new StringBuilder();
		if (min != Integer.MAX_VALUE)
			builder.append(minS);
		if (max != Integer.MIN_VALUE)
			builder.append(maxS);

		String formatted = String.format(query, builder.toString());

		PreparedStatement statement = con.prepareStatement(formatted);
		// Get the basic information on the map
		statement.setString(1, id);
		statement.setString(2, chromosome);
		int position = 3;
		if (min != Integer.MAX_VALUE)
			statement.setDouble(position++, min);
		if (max != Integer.MIN_VALUE)
			statement.setDouble(position++, max);
		statement.setInt(position++, DatabaseUtils.getLimitStart(currentPage, pageSize));
		statement.setInt(position++, pageSize);

		return statement;
	}

	// Takes a ResultSet which should represent the result of the linkageGroupsQuery defined above and returns a List of
	// BrapiLinkageGroup objects, each of which has been initialized with the values from the ResultSet
	private ArrayList<BrapiLinkageGroupMarker> getLinkageGroupMarkersFromResultSet(ResultSet resultSet)
		throws SQLException
	{
		ArrayList<BrapiLinkageGroupMarker> markers = new ArrayList<>();

		while (resultSet.next())
		{
			BrapiLinkageGroupMarker marker = new BrapiLinkageGroupMarker();
			marker.setMarkerDbId(resultSet.getString("marker_id"));
			marker.setMarkerName(resultSet.getString("marker_name"));
			marker.setLocation(resultSet.getString("definition_start"));
			markers.add(marker);
		}

		return markers;
	}
}