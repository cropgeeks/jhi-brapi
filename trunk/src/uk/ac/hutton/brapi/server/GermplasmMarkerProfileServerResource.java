package uk.ac.hutton.brapi.server;

import java.util.*;
import java.sql.*;

import com.fasterxml.jackson.databind.*;

import org.restlet.ext.jackson.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

/**
 * Created by gs40939 on 30/04/2015.
 */
public class GermplasmMarkerProfileServerResource extends ServerResource
{
	private int id;

	private final String allMarkers = "select genotypes.allele1, genotypes.allele2, genotypes.marker_id, " +
		"genotypes.germinatebase_id, markers.marker_name from genotypes INNER JOIN markers ON genotypes.marker_id = " +
		"markers.id where germinatebase_id=?";

	private final String markersFromMap = "select genotypes.allele1, genotypes.allele2, genotypes.marker_id, " +
		"genotypes.germinatebase_id, markers.marker_name from genotypes INNER JOIN markers ON genotypes.marker_id = " +
		"markers.id INNER JOIN mapdefinitions ON genotypes.marker_id = mapdefinitions.marker_id where germinatebase_id=? " +
		"AND mapdefinitions.map_id=?";

	@Override
	public void doInit()
	{
		this.id = Integer.parseInt(getRequestAttributes().get("id").toString());
		getLogger().info("ID: " + id);
	}

	@Get
	public Representation retrieve()
	{
		getLogger().info("########## ALL MAPS ###########");
		try (Connection con = Database.INSTANCE.getDataSource().getConnection())
		{
			PreparedStatement statement = con.prepareStatement(allMarkers);
			statement.setInt(1, id);

			uk.ac.hutton.brapi.resource.MarkerProfile profile = getProfile(statement.executeQuery());

			JacksonRepresentation rep = new JacksonRepresentation<uk.ac.hutton.brapi.resource.MarkerProfile>(profile);
			rep.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

			return rep;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	@Get("?map={map}")
	public Representation retrieveOnMap()
	{
		getLogger().info("########## SPECIFIC MAP ###########");
		try (Connection con = Database.INSTANCE.getDataSource().getConnection())
		{
			PreparedStatement statement = con.prepareStatement(markersFromMap);
			statement.setInt(1, id);
			statement.setInt(2, Integer.parseInt(getQueryValue("map")));

			uk.ac.hutton.brapi.resource.MarkerProfile profile = getProfile(statement.executeQuery());

			JacksonRepresentation rep = new JacksonRepresentation<uk.ac.hutton.brapi.resource.MarkerProfile>(profile);
			rep.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

			return rep;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private uk.ac.hutton.brapi.resource.MarkerProfile getProfile(ResultSet resultSet) throws SQLException
	{
		uk.ac.hutton.brapi.resource.MarkerProfile profile = new uk.ac.hutton.brapi.resource.MarkerProfile();
		HashMap<String, String> alleles = new HashMap<>();
		while (resultSet.next())
		{
			profile.setGermplasmId(resultSet.getInt("germinatebase_id"));
			alleles.put(resultSet.getString("marker_name"), resultSet.getString("allele1") + resultSet.getString("allele2"));
		}
		profile.setData(alleles);

		return profile;
	}

	@Put
	public void store(Representation germplasm)
	{
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
