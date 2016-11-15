package jhi.brapi.api.locations;

import jhi.brapi.api.*;

import org.restlet.resource.*;

/**
 * Queries the database for the ServerLocations then returns a JSON representation of the list of ServerLocations for API
 * clients to consume.
 */
public class ServerLocations extends BaseBrapiServerResource
{
	private LocationDAO locationDAO = new LocationDAO();

	@Get("json")
	public BrapiListResource<BrapiLocation> getJson()
	{
		return locationDAO.getAll(currentPage, pageSize);
	}
}