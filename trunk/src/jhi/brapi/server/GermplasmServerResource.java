package jhi.brapi.server;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

import com.google.inject.*;

import org.restlet.resource.*;

/**
 * Queries the database for the Germplasm with the given ID then returns a JSON (Jackson) representation of the
 * Germplasm for API clients to consume.
 */
public class GermplasmServerResource extends BaseBrapiServerResource<Germplasm>
{
	@Inject
	private GermplasmDAO germplasmDAO;

	// The ID from the URI (need to get this in overridden doInit method)
	private String id;

	@Override
	public void doInit()
	{
		super.doInit();
		this.id = (String)getRequestAttributes().get("id");
	}

	@Get("json")
	public Germplasm getJson()
	{
		Germplasm germplasm = germplasmDAO.getById(Integer.parseInt(id));

		if (germplasm != null)
			return germplasm;

		throw new ResourceException(404);
	}
}