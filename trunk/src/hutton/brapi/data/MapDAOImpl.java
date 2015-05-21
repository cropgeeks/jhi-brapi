package hutton.brapi.data;

import java.sql.*;
import java.util.*;

import hutton.brapi.resource.Map;
import hutton.brapi.resource.*;

/**
 * An implementation of the GermplasmDAO interface which provides access via a database.
 */
public class MapDAOImpl implements MapDAO
{
	// Selects the id, description and created_date from the MapList table, and carries out an inner join with the
	// mapdefinitions table so we can get a count of markers (by marker_id) and chromosomes.
	private final String mapsQuery = "SELECT maps.id, maps.description, maps.created_on, COUNT(DISTINCT " +
		"mapdefinitions.marker_id) AS markercount, COUNT(DISTINCT mapdefinitions.chromosome) AS chromosomeCount " +
		"from maps INNER JOIN mapdefinitions ON maps.id = mapdefinitions.map_id";

	private final String detailQuery = "SELECT maps.id, maps.description, maps.created_on, COUNT(DISTINCT " +
		"mapdefinitions.marker_id) AS markercount, COUNT(DISTINCT mapdefinitions.chromosome) AS chromosomeCount from " +
		"maps INNER JOIN mapdefinitions ON maps.id = mapdefinitions.map_id WHERE maps.id=?";

	private final String entriesQuery = "SELECT maps.id, mapdefinitions.marker_id, mapdefinitions.chromosome, " +
		"mapdefinitions.definition_start, markers.marker_name from maps INNER JOIN mapdefinitions ON maps.id = " +
		"mapdefinitions.map_id INNER JOIN markers ON mapdefinitions.marker_id = markers.id WHERE maps.id=?";

	/**
	 * Queries the database (using mapQuery defined above) for the complete list of Maps which the database holds.
	 *
	 * @return A MapList object which is a wrapper around a List of Map objects.
	 */
	@Override
	public MapList getAll()
	{
		MapList mapList = new MapList();

		try (Connection con = Database.INSTANCE.getDataSource().getConnection())
		{
			PreparedStatement statement = con.prepareStatement(mapsQuery);
			mapList = getMapsFromResultSet(statement.executeQuery());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return mapList;
	}

	// Takes a resultSet and iterates over it adding each map object in turn to a list of Map objects, which is then
	// put in the MapList wrapper (easing Jackson translation of the object to JSON and back).
	private MapList getMapsFromResultSet(ResultSet resultSet) throws SQLException
	{
		List<Map> resultSetMaps = new ArrayList<>();

		while (resultSet.next())
		{
			Map map = new Map();
			map.setId(resultSet.getInt("id"));
			map.setName(resultSet.getString("description"));
			map.setDate(resultSet.getDate("created_on"));
			map.setChromosomeCount(resultSet.getInt("chromosomeCount"));
			map.setMarkerCount(resultSet.getInt("markerCount"));
			resultSetMaps.add(map);
		}

		MapList mapList = new MapList();
		mapList.setMaps(resultSetMaps);

		return mapList;
	}

	/**
	 * Queries the database (using detailQuery and entiresQuery defined above) for the information on the map identified
	 * by the id given and the entries (MapEntry objects) which make up the detail of the map.
	 *
	 * @param id	The id of the
	 * @return 		A MapDetail object which itself holds a List of MapEntry objects. Or null if no MapDetail exists for
	 * the supplied id.
	 */
	@Override
	public MapDetail getById(int id)
	{
		try (Connection con = hutton.brapi.data.Database.INSTANCE.getDataSource().getConnection())
		{
			// Get the basic information on the map
			PreparedStatement mapStatement = con.prepareStatement(detailQuery);
			mapStatement.setInt(1, id);
			MapDetail mapDetail = getMapDetailFromResultSet(mapStatement.executeQuery());

			// Get the list of entries for the map (effectively the markers)
			PreparedStatement entriesStatement = con.prepareStatement(entriesQuery);
			entriesStatement.setInt(1, id);
			List<MapEntry> mapEntries = getMapEntriesFromResultSet(entriesStatement.executeQuery());
			mapDetail.setEntries(mapEntries);

			return mapDetail;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	// Takes a ResultSet which should represent the result of the detailQuery defined above and returns a new MapDetail
	// object which has been initialized with the values from the ResultSet
	private MapDetail getMapDetailFromResultSet(ResultSet resultSet) throws SQLException
	{
		resultSet.first();

		MapDetail mapDetail = new MapDetail();
		mapDetail.setId(resultSet.getInt("id"));
		mapDetail.setName(resultSet.getString("description"));

		return mapDetail;
	}

	// Takes a ResultSet which should represent the result of the entriesQuery defined above and returns a List of
	// MapEntry objects, each of which has been initialized with the values from the ResultSet
	private List<MapEntry> getMapEntriesFromResultSet(ResultSet resultSet) throws SQLException
	{
		List<MapEntry> mapEntries = new ArrayList<>();

		while (resultSet.next())
		{
			MapEntry mapEntry = new MapEntry();
			mapEntry.setMarkerId(resultSet.getInt("marker_id"));
			mapEntry.setMarkerName(resultSet.getString("marker_name"));
			mapEntry.setLocation("" + resultSet.getDouble("definition_start"));
			mapEntry.setChromosome(resultSet.getString("chromosome"));
			mapEntries.add(mapEntry);
		}

		return mapEntries;
	}
}
