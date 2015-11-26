package jhi.brapi.server;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

import org.restlet.resource.*;

/**
 * Queries the database for the Germplasm with the given ID then returns a JSON (Jackson)
 * representation of the Germplasm for API clients to consume.
 */
public class TraitServerResource extends BaseBrapiServerResource
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
	public Trait getJson()
	{
		Trait trait = traitDAO.getById(Integer.parseInt(id));

		return trait;
	}
}