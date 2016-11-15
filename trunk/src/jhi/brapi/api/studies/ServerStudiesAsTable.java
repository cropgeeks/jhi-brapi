package jhi.brapi.api.studies;

import jhi.brapi.api.*;

import org.restlet.resource.*;

/**
 * Queries the database for the ServerGermplasmSearch (germinatebase?) with the given ID then returns a JSON (Jackson) representation of the ServerGermplasmSearch for API
 * clients to consume.
 */
public class ServerStudiesAsTable extends BaseBrapiServerResource
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
	public BrapiBaseResource<BrapiStudiesAsTable> getJson()
	{
		BrapiBaseResource<BrapiStudiesAsTable> result;

		result = studiesDAO.getTableById(id);

		return result;
	}
}