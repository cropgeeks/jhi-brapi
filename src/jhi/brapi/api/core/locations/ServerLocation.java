package jhi.brapi.api.core.locations;

import jhi.brapi.api.*;
import org.restlet.resource.*;

/**
 * Queries the database for the ServerLocations then returns a JSON representation of the list of ServerLocations for API
 * clients to consume.
 */
public class ServerLocation extends BaseBrapiServerResource
{
	private LocationDAO locationDAO = new LocationDAO();

	private String id;

	@Override
	public void doInit()
	{
		super.doInit();
		this.id = getRequestAttributes().get("id").toString();
	}

	@Get("json")
	public BrapiBaseResource<Location> getJson()
	{
		return locationDAO.getById(id);
	}
}