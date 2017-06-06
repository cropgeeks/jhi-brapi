package jhi.brapi.api.germplasm;

import jhi.brapi.api.*;

import org.restlet.resource.*;

/**
 * Given an id, return the BrapiGermplasm for that id.
 */
public class ServerGermplasm extends BaseBrapiServerResource
{
	private GermplasmDAO germplasmDAO = new GermplasmDAO();

	private String id;

	@Override
	public void doInit()
	{
		super.doInit();
		this.id = getRequestAttributes().get("id").toString();
	}

	@Get("json")
	public BrapiBaseResource<BrapiGermplasm> getJson()
	{
		return germplasmDAO.getMcpdFor(id);
	}
}
