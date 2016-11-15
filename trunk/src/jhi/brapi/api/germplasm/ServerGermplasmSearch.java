package jhi.brapi.api.germplasm;

import jhi.brapi.api.*;
import jhi.brapi.api.germplasm.BrapiGermplasm.*;

import org.restlet.resource.*;

/**
 * Queries the database for the ServerGermplasmSearch (germinatebase?) with the given ID then returns a JSON (Jackson) representation of the ServerGermplasmSearch for API
 * clients to consume.
 */
public class ServerGermplasmSearch extends BaseBrapiServerResource
{
	private GermplasmDAO germplasmDAO = new GermplasmDAO();

	private String name;
	private MatchingMethod matchingMethod;

	@Override
	public void doInit()
	{
		super.doInit();

		this.name = getQueryValue("germplasmName");
		this.matchingMethod = MatchingMethod.getValue(getQueryValue("matchMethod"));
	}

	@Get("json")
	public BrapiListResource<BrapiGermplasm> getJson()
	{
		BrapiListResource<BrapiGermplasm> result;
		if (name == null || name.equals(""))
			result = germplasmDAO.getAll(currentPage, pageSize);
		else
			result = germplasmDAO.getByName(name, matchingMethod, currentPage, pageSize);

		return result;
	}

	@Post("json")
	public BrapiListResource<BrapiGermplasm> postJson(BrapiGermplasmPost params)
	{
		setParams(params);

		return getJson();
	}

	private void setParams(BrapiGermplasmPost params)
	{
		setPaginationParameters(params);

		this.name = params.getGermplasmName();
		// TODO: mathingMethod not in the docs yet, wait for this to happen
	}
}