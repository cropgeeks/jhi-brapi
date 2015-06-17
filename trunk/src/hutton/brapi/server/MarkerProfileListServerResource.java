package hutton.brapi.server;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.Inject;
import hutton.brapi.data.GermplasmDAO;
import hutton.brapi.data.MarkerProfileDAO;
import hutton.brapi.resource.GermplasmList;
import hutton.brapi.resource.MarkerProfileList;
import org.restlet.engine.connector.Method;
import org.restlet.ext.guice.SelfInjectingServerResource;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;

/**
 * Queries the database for the Germplasm (germinatebase?) with the given ID then returns a JSON (Jackson)
 * representation of the Germplasm for API clients to consume.
 */
public class MarkerProfileListServerResource extends SelfInjectingServerResource
{
	@Inject
	private MarkerProfileDAO markerProfileDAO;

	@Get("json")
	public MarkerProfileList retrieve()
	{
		MarkerProfileList markerProfileList = markerProfileDAO.getAll();

		if (markerProfileList != null)
			return markerProfileList;

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
		MarkerProfileList markerProfileList = markerProfileDAO.getAll();

		if (markerProfileList != null)
		{
			JacksonRepresentation<MarkerProfileList> rep = new JacksonRepresentation<>(markerProfileList);
			rep.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

			return rep;
		}

		throw new ResourceException(404);
	}
}