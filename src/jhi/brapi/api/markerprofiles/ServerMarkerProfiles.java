package jhi.brapi.api.markerprofiles;

import jhi.brapi.api.*;

import org.restlet.resource.*;

/**
 * Queries the database for the ServerGermplasmSearch (germinatebase?) with the given ID then returns a JSON (Jackson)
 * representation of the ServerGermplasmSearch for API clients to consume.
 */
public class ServerMarkerProfiles extends BaseBrapiServerResource
{
	private MarkerProfileDAO markerProfileDAO = new MarkerProfileDAO();

	@Get("json")
	public BrapiListResource<BrapiMarkerProfile> getJson()
	{
		return markerProfileDAO.getAll(currentPage, pageSize);
	}
}