package hutton.brapi.server;

import java.sql.*;
import java.util.*;

import hutton.brapi.resource.*;

import com.fasterxml.jackson.databind.*;

import org.restlet.ext.jackson.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

/**
 * Queries the database for the Germplasm (germinatebase?) with the given ID then returns a JSON (Jackson)
 * representation of the Germplasm for API clients to consume.
 */
public class GermplasmListServerResource extends ServerResource
{
	// Simply selects all fields from germinatebase
	private final String getLines = "select * from germinatebase";

	@Get
	public Representation retrieve()
	{
		try (Connection con = Database.INSTANCE.getDataSource().getConnection())
		{
			// Prepare statement with ID from URI
			PreparedStatement statement = con.prepareStatement(getLines);

			// Get the first result returned from the database
			ResultSet resultSet = statement.executeQuery();
			List<Germplasm> germplasmList = new ArrayList<>();

			while (resultSet.next())
			{
				// Set the Germplasm bean using the data returned from the database
				Germplasm germplasm = new Germplasm();
				germplasm.setId(resultSet.getInt("id"));
				germplasm.setGermplasmName(resultSet.getString("number"));
				germplasm.setTaxonId(resultSet.getInt("taxonomy_id"));

				germplasmList.add(germplasm);
			}

			GermplasmList germplasmResult = new GermplasmList();
			germplasmResult.setGermplasm(germplasmList);

			JacksonRepresentation rep = new JacksonRepresentation<GermplasmList>(germplasmResult);
			rep.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

			return rep;
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