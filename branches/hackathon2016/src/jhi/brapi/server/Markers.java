package jhi.brapi.server;

import org.restlet.resource.*;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;
import jhi.brapi.resource.BrapiGermplasm.*;

/**
 * Queries the database for the Germplasm (germinatebase?) with the given ID then returns a JSON (Jackson) representation of the Germplasm for API
 * clients to consume.
 */
public class Markers extends BaseBrapiServerResource
{
	private static final String PARAM_NAME = "name";
	private static final String PARAM_MATCHING_METHOD = "matchMethod";

	private MarkerDAO markerDAO = new MarkerDAO();

	private String name;
	private MatchingMethod matchingMethod;

	@Override
	public void doInit()
	{
		super.doInit();

		this.name = getQueryValue(PARAM_NAME);
		this.matchingMethod = MatchingMethod.getValue(getQueryValue(PARAM_MATCHING_METHOD));
	}

	@Get("json")
	public BasicResource<DataResult<BrapiMarker>> getJson()
	{
		BasicResource<DataResult<BrapiMarker>> result;
		if (name == null || name.equals(""))
			result = markerDAO.getAll(currentPage, pageSize);
		else
			result = markerDAO.getByName(name, matchingMethod, currentPage, pageSize);

		return result;
	}
}