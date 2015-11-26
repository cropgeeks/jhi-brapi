package jhi.brapi.server;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

import org.restlet.resource.*;

/**
 * Queries the database for the Germplasm (germinatebase?) with the given ID then returns a JSON (Jackson)
 * representation of the Germplasm for API clients to consume.
 */
public class TraitListServerResource extends BaseBrapiServerResource
{
	private TraitDAO traitDAO = new TraitDAO();

	@Get("json")
	public TraitList getJson()
	{
		TraitList traitList = traitDAO.getAll();

		return traitList;
	}
}