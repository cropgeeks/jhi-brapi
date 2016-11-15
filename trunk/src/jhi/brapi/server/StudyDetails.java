package jhi.brapi.server;

import org.restlet.resource.*;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

/**
 * Queries the database for the Germplasm with the given ID then returns a JSON (Jackson)
 * representation of the Germplasm for API clients to consume.
 */
public class StudyDetails extends BaseBrapiServerResource
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