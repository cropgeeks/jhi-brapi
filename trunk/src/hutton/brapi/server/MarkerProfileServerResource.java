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
public class MarkerProfileServerResource extends SelfInjectingServerResource
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

	@Get("json")
	public Representation getJson()
	{
		MarkerProfile profile = markerProfileDAO.getById(id);

		if (profile != null)
		{
			JacksonRepresentation<MarkerProfile> rep = new JacksonRepresentation(profile);

			return rep;
		}

		throw new ResourceException(404);
	}

	/**
	 * This is an example of serving the browser (or anything requesting html) the formatted / pretty-printed version
	 * of the JSON as opposed to the standard JSON which has no spacing (thus saves size for transferring data).
	 *
	 * @return
	 */
	@Get("html")
	public Representation getHtml()
	{
		MarkerProfile profile = markerProfileDAO.getById(id);

		if (profile != null)
		{
			JacksonRepresentation<MarkerProfile> rep = new JacksonRepresentation(profile);
			rep.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

			return rep;
		}

		throw new ResourceException(404);
	}
}