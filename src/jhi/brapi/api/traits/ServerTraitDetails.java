package jhi.brapi.api.traits;

import jhi.brapi.api.*;

import org.restlet.resource.*;

/**
 * Queries the database for the ServerGermplasm with the given ID then returns a JSON (Jackson)
 * representation of the ServerGermplasm for API clients to consume.
 */
public class ServerTraitDetails extends BaseBrapiServerResource
{
	private TraitDAO traitDAO = new TraitDAO();

	private String id;

	@Override
	public void doInit()
	{
		super.doInit();
		this.id = (String)getRequestAttributes().get("id");
	}

	@Get("json")
	public BrapiBaseResource<BrapiTrait> getJson()
	{
		BrapiBaseResource<BrapiTrait> trait = traitDAO.getById(Integer.parseInt(id));

		return trait;
	}
}