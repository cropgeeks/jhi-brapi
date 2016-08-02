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
	private MarkerDAO markerDAO = new MarkerDAO();

	private String name;
	private String type;
	private MatchingMethod matchingMethod;

	@Override
	public void doInit()
	{
		super.doInit();

		this.name = getQueryValue("name");
		this.type = getQueryValue("type");
		this.matchingMethod = MatchingMethod.getValue(getQueryValue("matchMethod"));
	}

	@Get("json")
	public BasicResource<DataResult<BrapiMarker>> getJson()
	{
		BasicResource<DataResult<BrapiMarker>> result;
		if (name != null && !name.equals(""))
			result = markerDAO.getByName(name, matchingMethod, currentPage, pageSize);
		else if(type != null && !type.equals(""))
			result = markerDAO.getByType(type, currentPage, pageSize);
		else
			result = markerDAO.getAll(currentPage, pageSize);

		return result;
	}
}