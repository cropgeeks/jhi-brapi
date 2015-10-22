package jhi.brapi.server;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

import com.google.inject.*;

import org.restlet.resource.*;

/**
 * Queries the database for the (Genome) MapList then returns a JSON (Jackson) representation of the MapList for API clients
 * to consume.
 */
public class MapListServerResource extends BaseBrapiServerResource
{
	@Inject
	private MapDAO mapDAO;

	@Get("json")
	public MapList getJson()
	{
		MapList mapList = mapDAO.getAll();

		return mapList;
	}
}