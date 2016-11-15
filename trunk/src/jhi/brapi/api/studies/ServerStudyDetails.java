package jhi.brapi.api.studies;

import jhi.brapi.api.*;

import org.restlet.resource.*;

/**
 * Queries the database for the ServerGermplasmSearch with the given ID then returns a JSON (Jackson)
 * representation of the ServerGermplasmSearch for API clients to consume.
 */
public class ServerStudyDetails extends BaseBrapiServerResource
{
	private StudiesDAO studiesDAO = new StudiesDAO();

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
		return studiesDAO.getById(id);
	}
}