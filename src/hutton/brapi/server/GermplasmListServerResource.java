package hutton.brapi.server;

import hutton.brapi.data.*;
import hutton.brapi.resource.*;

import com.fasterxml.jackson.databind.*;

import com.google.inject.*;

import org.restlet.ext.guice.*;
import org.restlet.ext.jackson.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

/**
 * Queries the database for the Germplasm (germinatebase?) with the given ID then returns a JSON (Jackson)
 * representation of the Germplasm for API clients to consume.
 */
public class GermplasmListServerResource extends SelfInjectingServerResource
{
	@Inject
	private GermplasmDAO germplasmDAO;


	@Get
	public Representation retrieve()
	{
		GermplasmList germplasmList = germplasmDAO.getAll();

		JacksonRepresentation<GermplasmList> rep = new JacksonRepresentation<>(germplasmList);
		rep.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

		return rep;
	}

	@Put
	public void store(Representation germplasm)
	{
		throw new UnsupportedOperationException("Not implemented yet");
	}
}