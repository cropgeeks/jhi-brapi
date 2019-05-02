package jhi.brapi.api.traits;

import jhi.brapi.api.*;

import org.restlet.resource.*;

/**
 * Queries the database for the ServerGermplasm (germinatebase?) with the given ID then returns a JSON (Jackson)
 * representation of the ServerGermplasm for API clients to consume.
 */
public class ServerTraits extends BaseBrapiServerResource
{
	private TraitDAO traitDAO = new TraitDAO();

	@Get("json")
	public BrapiListResource<BrapiTrait> getJson()
	{
		BrapiListResource<BrapiTrait> traitList = traitDAO.getAll();

		return traitList;
	}
}