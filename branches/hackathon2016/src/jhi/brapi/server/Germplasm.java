package jhi.brapi.server;

import org.restlet.resource.*;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;
import jhi.brapi.resource.BrapiGermplasm.*;

/**
 * Queries the database for the Germplasm (germinatebase?) with the given ID then returns a JSON (Jackson) representation of the Germplasm for API
 * clients to consume.
 */
public class Germplasm extends BaseBrapiServerResource
{
	private static final String PARAM_NAME = "name";
	private static final String PARAM_MATCHING_METHOD = "matchMethod";

	private GermplasmDAO germplasmDAO = new GermplasmDAO();

	private String name;
	private MatchingMethod matchingMethod;

	@Override
	public void doInit()
	{
		super.doInit();

		this.name = getQueryValue(PARAM_NAME);
		this.matchingMethod = BrapiGermplasm.MatchingMethod.getValue(getQueryValue(PARAM_MATCHING_METHOD));
	}

	@Get("json")
	public BasicResource<DataResult<BrapiGermplasm>> getJson()
	{
		BasicResource<DataResult<BrapiGermplasm>> result;
		if (name == null || name.equals(""))
			result = germplasmDAO.getAll(currentPage, pageSize);
		else
			result = germplasmDAO.getByName(name, matchingMethod, currentPage, pageSize);

		return result;
	}
}