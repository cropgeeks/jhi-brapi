package uk.ac.hutton.brapi.server;

import java.sql.*;
import java.util.*;

import uk.ac.hutton.brapi.resource.Map;

import org.restlet.ext.jackson.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

/**
 * Queries the database for the (Genome) Maps then returns a JSON (Jackson) representation of the Maps for API clients
 * to consume.
 */
public class MapsServerResource extends ServerResource
{
	// Selects the id, description and created_date from the Maps table, and carries out an inner join with the
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
			uk.ac.hutton.brapi.resource.Maps maps = getMapsFromResultSet(statement.executeQuery());

			return new JacksonRepresentation<uk.ac.hutton.brapi.resource.Maps>(maps);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Takes a resultSet and iterates over it adding each map object in turn to a list of Map objects, which is then
	 * put in the Maps wrapper (easing Jackson translation of the object to JSON and back).
	 *
	 * @param resultSet The resultset returned from the mapsQuery
	 * @return Maps 	(a wrapper around a List of Map objects)
	 * @throws SQLException
	 */
	private uk.ac.hutton.brapi.resource.Maps getMapsFromResultSet(ResultSet resultSet) throws SQLException
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

		uk.ac.hutton.brapi.resource.Maps maps = new uk.ac.hutton.brapi.resource.Maps();
		maps.setMaps(resultSetMaps);

		return maps;
	}

	@Put
	public void store(Representation map)
	{
		throw new UnsupportedOperationException("Not implemented yet");
	}
}