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

	private MarkerProfilesSearchParams params = new MarkerProfilesSearchParams();

	@Override
	public void doInit()
	{
		super.doInit();

		if (getQuery().getNames().contains("germplasmDbId"))
			params.setGermplasm(getQueryValue("germplasmDbId"));
		if (getQuery().getNames().contains("studyDbId"))
			params.setStudy(getQueryValue("studyDbId"));
		if (getQuery().getNames().contains("sampleDbId"))
			params.setStudy(getQueryValue("sampleDbId"));
		if (getQuery().getNames().contains("extractDbId"))
			params.setStudy(getQueryValue("extractDbId"));
		if (getQuery().getNames().contains("methodDbId"))
			params.setStudy(getQueryValue("methodDbId"));
	}

	@Get("json")
	public BrapiListResource<BrapiMarkerProfile> getJson()
	{
		return markerProfileDAO.getAll(params, currentPage, pageSize);
	}
}