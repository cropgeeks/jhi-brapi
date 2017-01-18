package jhi.brapi.api.studies;

import jhi.brapi.api.*;
import jhi.brapi.api.locations.*;

import org.restlet.resource.*;

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
	public BrapiBaseResource<BrapiStudiesDetail> getJson()
	{
		return studiesDAO.getById(id);
	}
}