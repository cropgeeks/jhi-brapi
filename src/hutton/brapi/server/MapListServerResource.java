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
 * Queries the database for the (Genome) MapList then returns a JSON (Jackson) representation of the MapList for API clients
 * to consume.
 */
public class MapListServerResource extends SelfInjectingServerResource
{
	@Inject
	private MapDAO mapDAO;

	@Get("json")
	public MapList getJson()
	{
		MapList mapList = mapDAO.getAll();

		return mapList;
	}

	@Get("html")
	public Representation getHtml()
	{
		MapList mapList = mapDAO.getAll();

		if (mapList != null)
		{
			JacksonRepresentation<MapList> rep = new JacksonRepresentation<>(mapList);
			rep.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
			return rep;
		}

		throw new ResourceException(404);
	}
}