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
 * Given an id returns the markers and associated alleles which are associated with the markerprofile with that id.
 */
public class MarkerProfileCountServerResource extends SelfInjectingServerResource
{
	@Inject
	private MarkerProfileDAO markerProfileDAO;

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
		MarkerProfileCount profileCount = markerProfileDAO.getCountById(id);

		if (profileCount != null)
		{
			JacksonRepresentation<MarkerProfileCount> rep = new JacksonRepresentation(profileCount);
			rep.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

			return rep;
		}

		throw new ResourceException(404);
	}
}