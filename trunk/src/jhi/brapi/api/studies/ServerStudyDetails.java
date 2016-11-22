package jhi.brapi.api.studies;

import jhi.brapi.api.*;
import jhi.brapi.api.locations.*;

import org.restlet.resource.*;

/**
 * Queries the database for the ServerGermplasmSearch with the given ID then returns a JSON (Jackson)
 * representation of the ServerGermplasmSearch for API clients to consume.
 */
public class ServerStudyDetails extends BaseBrapiServerResource
{
	private StudiesDAO studiesDAO = new StudiesDAO();
	private LocationDAO locationDAO = new LocationDAO();

	private String id;

	@Override
	public void doInit()
	{
		super.doInit();
		this.id = (String)getRequestAttributes().get("id");
	}

	@Get("json")
	public BrapiBaseResource<BrapiStudies> getJson()
	{
		BrapiBaseResource<BrapiStudies> study = studiesDAO.getById(id);
		String locationId = study.getResult().getLocation().getLocationDbId();

		BrapiLocation location = locationDAO.getById(locationId).getResult();
		study.getResult().setLocation(location);

		return study;
	}
}