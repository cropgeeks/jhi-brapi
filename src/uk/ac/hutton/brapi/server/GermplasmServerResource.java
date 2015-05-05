package uk.ac.hutton.brapi.server;

import java.sql.*;

import org.restlet.ext.jackson.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

/**
 * Queries the database for the Germplasm (germinatebase?) with the given ID then returns a JSON (Jackson)
 * representation of the Germplasm for API clients to consume.
 */
public class GermplasmServerResource extends ServerResource
{
	// The ID from the URI (need to get this in overridden doInit method)
	private String id;

	// Simply selects all fields from germinatebase where the given id matches the id from the URI
	private final String getSpecificLine = "select * from germinatebase where id=?";

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
			// Prepare statement with ID from URI
			PreparedStatement statement = con.prepareStatement(getSpecificLine);
			statement.setInt(1, Integer.parseInt(id));

			// Get the first result returned from the database
			ResultSet resultSet = statement.executeQuery();
			resultSet.first();

			// Set the Germplasm bean using the data returned from the database
			uk.ac.hutton.brapi.resource.Germplasm germplasm = new uk.ac.hutton.brapi.resource.Germplasm();
			germplasm.setId(resultSet.getInt("id"));
			germplasm.setGermplasmName(resultSet.getString("number"));
			germplasm.setTaxonId(resultSet.getInt("taxonomy_id"));

			return new JacksonRepresentation<uk.ac.hutton.brapi.resource.Germplasm>(germplasm);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	@Put
	public void store(Representation germplasm)
	{
		throw new UnsupportedOperationException("Not implemented yet");
	}
}