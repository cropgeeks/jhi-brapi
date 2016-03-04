package jhi.brapi.server;

import java.util.*;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

import org.restlet.resource.*;

/**
 * Returns a list of all methods associated with a marker profile
 */
public class MarkerProfileMethods extends BaseBrapiServerResource
{
	private MarkerProfileMethodsDAO markerProfileMethodsDAO = new MarkerProfileMethodsDAO();

	@Override
	public void doInit()
	{
		super.doInit();

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
	public BasicResource<BrapiMarkerProfileMethod> getJson()
	{
		return markerProfileMethodsDAO.getAll(currentPage, pageSize);
	}
}