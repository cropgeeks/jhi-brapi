package jhi.brapi.server;

import java.util.List;
import jhi.brapi.data.*;
import jhi.brapi.resource.*;

import org.restlet.resource.*;

/**
 * Queries the database for the Germplasm (germinatebase?) with the given ID then returns a JSON (Jackson)
 * representation of the Germplasm for API clients to consume.
 */
public class MarkerProfiles extends BaseBrapiServerResource
{
	private MarkerProfileDAO markerProfileDAO = new MarkerProfileDAO();

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
	public BasicResource<BrapiMarkerProfile> getJson()
	{
		return markerProfileDAO.getAll(currentPage, pageSize);
	}
}