package uk.ac.hutton.brapi.server;

import java.sql.*;
import java.util.*;

import uk.ac.hutton.brapi.resource.*;

import com.fasterxml.jackson.databind.*;

import org.restlet.ext.jackson.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

/**
 * Created by gs40939 on 12/05/2015.
 */
public class MarkerProfileServerResource extends ServerResource
{
	private String id;

	private int germinatebaseId;
	private int datasetId;

	private final String allMarkers = "select genotypes.allele1, genotypes.allele2, genotypes.marker_id, " +
		"genotypes.germinatebase_id, genotypes.dataset_id, markers.marker_name from genotypes INNER JOIN markers ON " +
		"genotypes.marker_id = markers.id INNER JOIN datasets ON genotypes.dataset_id = datasets.id where " +
		"germinatebase_id=? AND datasets.id=?";

	@Override
	public void doInit()
	{
		this.id = (String)getRequestAttributes().get("id");

		String[] tokens = id.split("-");
		datasetId = Integer.parseInt(tokens[0]);
		germinatebaseId = Integer.parseInt(tokens[1]);
	}

	@Get
	public Representation retrieve()
	{
		try (Connection con = Database.INSTANCE.getDataSource().getConnection())
		{
			// Get the basic information on the map
			PreparedStatement markerProfileStatement = con.prepareStatement(allMarkers);
			markerProfileStatement.setInt(1, germinatebaseId);
			markerProfileStatement.setInt(2, datasetId);
			MarkerProfile profile = getProfile(markerProfileStatement.executeQuery());

			JacksonRepresentation rep = new JacksonRepresentation<MarkerProfile>(profile);
			rep.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

			return rep;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

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
}