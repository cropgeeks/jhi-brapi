package jhi.brapi.server;

import org.restlet.resource.*;

import java.util.*;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

/**
 * Queries the database for the Germplasm with the given ID then returns a JSON (Jackson) representation of the Germplasm for API clients to consume.
 */
public class GermplasmServerResource extends BaseBrapiServerResource
{
	private GermplasmDAO germplasmDAO = new GermplasmDAO();

	// The ID from the URI (need to get this in overridden doInit method)
	private String id;

	@Override
	public void doInit()
	{
		super.doInit();
		this.id = getRequestAttributes().get("id").toString();
	}

	@Get("json")
	public BasicResource<BrapiGermplasm> getJson()
	{
		return germplasmDAO.getById(id);
	}
}