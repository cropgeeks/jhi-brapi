package uk.ac.hutton.brapi.server;

import java.sql.*;
import java.util.*;

import uk.ac.hutton.brapi.resource.*;

import com.fasterxml.jackson.databind.*;

import org.restlet.ext.jackson.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

/**
 * Created by gs40939 on 24/04/2015.
 */
public class MapServerResource extends ServerResource
{
	private String id;

	private final String detailQuery = "SELECT maps.id, maps.description, maps.created_on, COUNT(DISTINCT " +
		"mapdefinitions.marker_id) AS markercount, COUNT(DISTINCT mapdefinitions.chromosome) AS chromosomeCount from " +
		"maps INNER JOIN mapdefinitions ON maps.id = mapdefinitions.map_id WHERE maps.id=?";

	private final String entriesQuery = "SELECT maps.id, mapdefinitions.marker_id, mapdefinitions.chromosome, " +
		"mapdefinitions.definition_start, markers.marker_name from maps INNER JOIN mapdefinitions ON maps.id = " +
		"mapdefinitions.map_id INNER JOIN markers ON mapdefinitions.marker_id = markers.id WHERE maps.id=?";

	@Override
	public void doInit()
	{
		this.id = (String)getRequestAttributes().get("id");
	}

	@Get
	public Representation retrieve()
	{
		try (Connection con = Database.INSTANCE.getDataSource().getConnection())
		{
			// Get the basic information on the map
			PreparedStatement mapStatement = con.prepareStatement(detailQuery);
			mapStatement.setInt(1, Integer.parseInt(id));
			MapDetail mapDetail = getMapDetailFromResultSet(mapStatement.executeQuery());

			// Get the list of entries for the map (effectively the markers)
			PreparedStatement entriesStatement = con.prepareStatement(entriesQuery);
			entriesStatement.setInt(1, Integer.parseInt(id));
			List<MapEntry> mapEntries = getMapEntriesFromResultSet(entriesStatement.executeQuery());
			mapDetail.setEntries(mapEntries);

			JacksonRepresentation rep = new JacksonRepresentation<MapDetail>(mapDetail);
			rep.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

			return rep;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private MapDetail getMapDetailFromResultSet(ResultSet resultSet) throws SQLException
	{
		resultSet.first();

		MapDetail mapDetail = new MapDetail();
		mapDetail.setId(resultSet.getInt("id"));
		mapDetail.setName(resultSet.getString("description"));

		return mapDetail;
	}

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

	@Put
	public void store(Representation map)
	{
		throw new UnsupportedOperationException("Not implemented yet");
	}
}