package jhi.brapi.api.genomemaps;

import jhi.brapi.api.*;

import org.restlet.resource.*;

import java.util.*;

public class ServerGenomeMapMarkerData extends BaseBrapiServerResource
{
	private MapDAO mapDAO = new MapDAO();

	// The ID from the URI (need to get this in overridden doInit method)
	private String id;
	private List<String> linkageGroups;

	@Override
	public void doInit()
	{
		super.doInit();
		id = (String)getRequestAttributes().get("id");
//		linkageGroups = parseGetParameterList("linkageGroupId");
	}

	@Get("json")
	public BrapiListResource<BrapiMarkerPosition> getJson()
	{
		return mapDAO.getByIdMarkers(id, linkageGroups, currentPage, pageSize);
	}
}