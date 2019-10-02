package jhi.brapi.api.core.locations;

import jhi.brapi.api.*;

import org.restlet.resource.*;

/**
 * Queries the database for the ServerLocations then returns a JSON representation of the list of ServerLocations for API
 * clients to consume.
 */
public class ServerLocations extends BaseBrapiServerResource
{
	private LocationDAO locationDAO = new LocationDAO();

	private String locationType;

	@Override
	public void doInit()
	{
		super.doInit();

		this.locationType = getQueryValue("locationType");
	}

	@Get("json")
	public BrapiListResource<Location> getJson()
	{
		if (locationType == null || locationType.equals(""))
			return locationDAO.getAll(currentPage, pageSize);
		else
			return locationDAO.getByLocationType(locationType, currentPage, pageSize);
	}
}