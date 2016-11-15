package jhi.brapi.server;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

import org.restlet.resource.*;

/**
 * Queries the database for the Locations then returns a JSON representation of the list of Locations for API
 * clients to consume.
 */
public class Locations extends BaseBrapiServerResource
{
	private LocationDAO locationDAO = new LocationDAO();

	@Get("json")
	public BrapiListResource<BrapiLocation> getJson()
	{
		return locationDAO.getAll(currentPage, pageSize);
	}
}