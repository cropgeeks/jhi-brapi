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

	@Get("json")
	public GermplasmList retrieve()
	{
		GermplasmList germplasmList = germplasmDAO.getAll();

		if (germplasmList != null)
			return germplasmList;

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
		GermplasmList germplasmList = germplasmDAO.getAll();

		if (germplasmList != null)
		{
			JacksonRepresentation<GermplasmList> rep = new JacksonRepresentation<>(germplasmList);
			rep.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

			return rep;
		}

		throw new ResourceException(404);
	}
}