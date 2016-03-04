package jhi.brapi.server;

import java.util.List;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

import org.restlet.resource.*;

/**
 * Queries the database for the (Genome) MapList then returns a JSON (Jackson)
 * representation of the MapList for API clients
 * to consume.
 */
public class GenomeMaps extends BaseBrapiServerResource
{
	private MapDAO mapDAO = new MapDAO();

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
	public BasicResource<BrapiGenomeMap> getJson()
	{
		return mapDAO.getAll(currentPage, pageSize);
	}
}