package hutton.brapi.server;

import java.sql.*;

import hutton.brapi.resource.*;

import com.fasterxml.jackson.databind.*;

import org.restlet.ext.jackson.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

/**
 * Queries the database for the Germplasm with the given ID then returns a JSON (Jackson) representation of the
 * Germplasm for API clients to consume.
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
			if (resultSet.first())
			{
				// Set the Germplasm bean using the data returned from the database
				Germplasm germplasm = new Germplasm();
				germplasm.setId(resultSet.getInt("id"));
				germplasm.setGermplasmName(resultSet.getString("number"));
				germplasm.setTaxonId(resultSet.getInt("taxonomy_id"));

				JacksonRepresentation rep = new JacksonRepresentation<Germplasm>(germplasm);
				rep.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

				return rep;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		throw new ResourceException(404);
	}

	@Put
	public void store(Representation germplasm)
	{
		throw new UnsupportedOperationException("Not implemented yet");
	}
}