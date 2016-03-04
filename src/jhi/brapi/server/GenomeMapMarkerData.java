package jhi.brapi.server;

import java.util.*;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

import org.restlet.resource.*;

/**
 * Created by gs40939 on 17/06/2015.
 */
public class GenomeMapMarkerData extends BaseBrapiServerResource
{
	private MapDAO mapDAO = new MapDAO();

	// The ID from the URI (need to get this in overridden doInit method)
	private String id;
	private String[] linkageGroups;

	@Override
	public void doInit()
	{
		super.doInit();
		id = (String)getRequestAttributes().get("id");
		linkageGroups = getLinkageGroups(getQueryValue("linkageGroupIdList"));

		try
		{
			this.pageSize = Integer.parseInt(getQueryValue(PARAM_PAGE_SIZE));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		try
		{
			this.currentPage = Integer.parseInt(getQueryValue(PARAM_CURRENT_PAGE));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Get("json")
	public BasicResource<BrapiMarker> getJson()
	{
		return mapDAO.getByIdMarkers(id, linkageGroups, currentPage, pageSize);
	}

	private String[] getLinkageGroups(String linkageGroupIdList)
	{
		if (linkageGroupIdList == null)
			return new String[] {};

		// Split by comma
		String[] linkageGroup = linkageGroupIdList.split(",");

		// Trim out any leading/trailing spaces, just in case
		for (int i = 0; i < linkageGroup.length; i++)
			linkageGroup[i] = linkageGroup[i].trim();

		return linkageGroup;
	}
}
