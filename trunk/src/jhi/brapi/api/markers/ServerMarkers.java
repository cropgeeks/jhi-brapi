package jhi.brapi.api.markers;

import jhi.brapi.api.*;
import jhi.brapi.api.germplasm.BrapiGermplasm.*;

import org.restlet.resource.*;

/**
 * Queries the database for the ServerGermplasmSearch (germinatebase?) with the given ID then returns a JSON (Jackson) representation of the ServerGermplasmSearch for API
 * clients to consume.
 */
public class ServerMarkers extends BaseBrapiServerResource
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
	public BrapiListResource<BrapiMarker> getJson()
	{
		BrapiListResource<BrapiMarker> result;
		if (name != null && !name.equals(""))
			result = markerDAO.getByName(name, matchingMethod, currentPage, pageSize);
		else if(type != null && !type.equals(""))
			result = markerDAO.getByType(type, currentPage, pageSize);
		else
			result = markerDAO.getAll(currentPage, pageSize);

		return result;
	}
}