package jhi.brapi.server;

import java.util.*;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

import org.restlet.resource.*;

/**
 * Given an id, returns the information on the map indicated by the id. This extends to its name and among other things,
 * a list of markers which are on the given map and their positions.
 */
public class MapServerResource extends BaseBrapiServerResource
{
	private MapDAO mapDAO = new MapDAO();

	private String id;

	@Override
	public void doInit()
	{
		super.doInit();
		this.id = (String)getRequestAttributes().get("id");
	}

	@Get("json")
	public BasicResource<MapDetail> getJson()
	{
		MapDetail mapDetail = mapDAO.getById(Integer.parseInt(id));

		List<MapDetail> list = new ArrayList<>();
		if (mapDetail != null)
			list.add(mapDetail);

		return new BasicResource<MapDetail>(list);
	}
}