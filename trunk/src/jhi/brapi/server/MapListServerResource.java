package jhi.brapi.server;

import java.util.List;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

import org.restlet.resource.*;

/**
 * Queries the database for the (Genome) MapList then returns a JSON (Jackson) representation of the MapList for API clients
 * to consume.
 */
public class MapListServerResource extends BaseBrapiServerResource
{
	private MapDAO mapDAO = new MapDAO();

	@Get("json")
	public BasicResource<Map> getJson()
	{
		List<Map> maps = mapDAO.getAll();

		return new BasicResource<Map>(maps);
	}
}