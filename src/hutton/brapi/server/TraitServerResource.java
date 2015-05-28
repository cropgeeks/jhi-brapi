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
 * Queries the database for the Germplasm with the given ID then returns a JSON (Jackson)
 * representation of the Germplasm for API clients to consume.
 */
public class TraitServerResource extends SelfInjectingServerResource
{
	@Inject
	private TraitDAO traitDAO;

	private String id;

	@Override
	public void doInit()
	{
		super.doInit();
		this.id = (String)getRequestAttributes().get("id");
	}

	@Get
	public Representation retrieve()
	{
		Trait trait = traitDAO.getById(Integer.parseInt(id));

		JacksonRepresentation<Trait> rep = new JacksonRepresentation<>(trait);
		rep.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

		return rep;
	}
}