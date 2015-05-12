package uk.ac.hutton.brapi.server;

import java.util.*;
import java.sql.*;

import uk.ac.hutton.brapi.resource.*;

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

	private final String markrerProfileIdQuery = "select DISTINCT(dataset_id), germinatebase_id from genotypes where germinatebase_id=?";

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
			PreparedStatement statement = con.prepareStatement(markrerProfileIdQuery);
			statement.setInt(1, id);

			GermplasmMarkerProfileList profileList = getProfileList(statement.executeQuery());

			JacksonRepresentation rep = new JacksonRepresentation<GermplasmMarkerProfileList>(profileList);
			rep.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

			return rep;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private GermplasmMarkerProfileList getProfileList(ResultSet resultSet) throws SQLException
	{
		GermplasmMarkerProfileList profileList = new GermplasmMarkerProfileList();
		List<String> markerProfileIdList = new ArrayList<>();
		while (resultSet.next())
		{
			profileList.setId(resultSet.getInt("germinatebase_id"));
			markerProfileIdList.add(resultSet.getInt("dataset_id") + "-" + resultSet.getInt("germinatebase_id"));
		}
		profileList.setMarkerProfiles(markerProfileIdList);

		return profileList;
	}

	@Put
	public void store(Representation germplasm)
	{
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
