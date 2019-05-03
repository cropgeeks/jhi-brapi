package jhi.brapi.api.germplasm;

import jhi.brapi.api.*;
import org.restlet.resource.*;

/**
 * Queries the database for the ServerGermplasm pedigree with the given ID then returns a JSON (Jackson) representation of the ServerGermplasm pedigree for API
 * clients to consume.
 */
public class ServerGermplasmProgeny extends BaseBrapiServerResource
{
	private GermplasmDAO germplasmDAO = new GermplasmDAO();

	// The ID from the URI (need to get this in overridden doInit method)
	private String id;

	public void doInit()
	{
		super.doInit();
		this.id = (String)getRequestAttributes().get("id");
	}

	@Get("json")
	public BrapiBaseResource<BrapiGermplasmProgeny> getJson()
	{
		return germplasmDAO.getProgenyById(id);
	}
}