package uk.ac.hutton.brapi.server;

import java.sql.*;
import java.util.*;

import uk.ac.hutton.brapi.resource.*;
import uk.ac.hutton.brapi.resource.Map;

import com.fasterxml.jackson.databind.*;

import org.restlet.ext.jackson.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

/**
 * Queries the database for the (Genome) MapList then returns a JSON (Jackson) representation of the MapList for API clients
 * to consume.
 */
public class MapListServerResource extends ServerResource
{
	// Selects the id, description and created_date from the MapList table, and carries out an inner join with the
	// mapdefinitions table so we can get a count of markers (by marker_id) and chromosomes.
	private final String mapsQuery = "SELECT maps.id, maps.description, maps.created_on, COUNT(DISTINCT " +
		"mapdefinitions.marker_id) AS markercount, COUNT(DISTINCT mapdefinitions.chromosome) AS chromosomeCount " +
		"from maps INNER JOIN mapdefinitions ON maps.id = mapdefinitions.map_id";

	@Get
	public Representation retrieve()
	{
		try (Connection con = Database.INSTANCE.getDataSource().getConnection())
		{
			PreparedStatement statement = con.prepareStatement(mapsQuery);
			MapList mapList = getMapsFromResultSet(statement.executeQuery());

			JacksonRepresentation rep = new JacksonRepresentation<MapList>(mapList);
			rep.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

			return rep;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Takes a resultSet and iterates over it adding each map object in turn to a list of Map objects, which is then
	 * put in the MapList wrapper (easing Jackson translation of the object to JSON and back).
	 *
	 * @param resultSet The resultset returned from the mapsQuery
	 * @return MapList 	(a wrapper around a List of Map objects)
	 * @throws SQLException
	 */
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

	@Put
	public void store(Representation map)
	{
		throw new UnsupportedOperationException("Not implemented yet");
	}
}