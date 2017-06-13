package jhi.brapi.api.trials;

import org.restlet.resource.*;

import jhi.brapi.api.*;

public class ServerTrial extends BaseBrapiServerResource
{
	private TrialDAO trialDAO = new TrialDAO();

	private String id;

	@Override
	public void doInit()
	{
		id = (String) getRequestAttributes().get("id");
	}

	@Get("json")
	public BrapiBaseResource<BrapiTrial> getJson()
	{
		return trialDAO.getById(id);
	}
}
