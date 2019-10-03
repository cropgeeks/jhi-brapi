package jhi.brapi.api.genotyping.genomemaps;

import jhi.brapi.api.*;

import org.restlet.resource.*;

public class ServerGenomeMapLinkageGroupMarkers extends BaseBrapiServerResource
{
	private MapDAO mapDAO = new MapDAO();

	// The ID from the URI (need to get this in overridden doInit method)
	private String id;
	private String linkageGroup;
	private double min = Integer.MAX_VALUE;
	private double max = Integer.MIN_VALUE;

	@Override
	public void doInit()
	{
		super.doInit();
		id = (String)getRequestAttributes().get("id");
		linkageGroup = (String)getRequestAttributes().get("linkageGroupId");

		try
		{
			min = Double.parseDouble(getQueryValue("min"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		try
		{
			max = Double.parseDouble(getQueryValue("max"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Get("json")
	public BrapiListResource<BrapiLinkageGroupMarker> getJson()
	{
		return mapDAO.getByIdLinkageGroupMarkersRange(id, linkageGroup, min, max, currentPage, pageSize);
	}
}