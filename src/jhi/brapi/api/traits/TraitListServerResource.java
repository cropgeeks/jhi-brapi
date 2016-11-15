package jhi.brapi.api.traits;

import jhi.brapi.api.*;

import org.restlet.resource.*;

/**
 * Queries the database for the ServerGermplasmSearch (germinatebase?) with the given ID then returns a JSON (Jackson)
 * representation of the ServerGermplasmSearch for API clients to consume.
 */
public class TraitListServerResource extends BaseBrapiServerResource
{
	private TraitDAO traitDAO = new TraitDAO();

	@Get("json")
	public BrapiTraitList getJson()
	{
		BrapiTraitList traitList = traitDAO.getAll();

		return traitList;
	}
}