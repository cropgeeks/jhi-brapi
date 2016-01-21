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
		this.id = (String) getRequestAttributes().get("id");
	}

	@Get("json")
	public BasicResource<BrapiGermplasm> getJson()
	{
		BrapiGermplasm germplasm = germplasmDAO.getById(Integer.parseInt(id));

		List<BrapiGermplasm> result = new ArrayList<>();

		if (germplasm != null)
			result.add(germplasm);

		return new BasicResource<>(result);
	}
}