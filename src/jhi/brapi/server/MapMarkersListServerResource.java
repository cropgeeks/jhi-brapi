package jhi.brapi.server;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

import org.restlet.resource.*;

/**
 * Created by gs40939 on 17/06/2015.
 */
public class MapMarkersListServerResource extends BaseBrapiServerResource
{
	private MapDAO mapDAO = new MapDAO();

	// The ID from the URI (need to get this in overridden doInit method)
	private String id;

	@Override
	public void doInit()
	{
		super.doInit();
		this.id = (String)getRequestAttributes().get("id");
	}

	@Get("json")
	public MapMarkersList getJson()
	{
		MapMarkersList mapList = mapDAO.getByIdMarkers(Integer.parseInt(id));

		return mapList;
	}
}
