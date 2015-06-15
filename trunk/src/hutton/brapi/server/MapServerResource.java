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
 * Given an id, returns the information on the map indicated by the id. This extends to its name and among other things,
 * a list of markers which are on the given map and their positions.
 */
public class MapServerResource extends SelfInjectingServerResource
{
	@Inject
	private MapDAO mapDAO;

	private String id;

	@Override
	public void doInit()
	{
		super.doInit();
		this.id = (String)getRequestAttributes().get("id");
	}

	@Get("json")
	public MapDetail retrieve()
	{
		MapDetail mapDetail = mapDAO.getById(Integer.parseInt(id));

		if (mapDetail != null)
			return mapDetail;

		throw new ResourceException(404);
	}
}