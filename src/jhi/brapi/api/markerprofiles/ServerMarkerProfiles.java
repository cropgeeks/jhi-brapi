package jhi.brapi.api.markerprofiles;


import java.util.*;

import jhi.brapi.api.*;

import org.restlet.resource.*;

/**
 * Queries the database for the ServerGermplasmSearch (germinatebase?) with the given ID then returns a JSON (Jackson)
 * representation of the ServerGermplasmSearch for API clients to consume.
 */
public class ServerMarkerProfiles extends BaseBrapiServerResource
{
	private MarkerProfileDAO markerProfileDAO = new MarkerProfileDAO();

	private String germplasmId;
	private String studyId;
	private String sampleId;
	private String extractId;
	private String methodId;

	@Override
	public void doInit()
	{
		super.doInit();

		germplasmId = getQueryValue("germplasmDbId");
		studyId = getQueryValue("studyDbId");
		sampleId = getQueryValue("sampleDbId");
		extractId = getQueryValue("extractDbId");
		methodId = getQueryValue("methodDbId");
	}

	private void addParameter(Map<String, String> map, String key, String value)
	{
		if (value != null && value.length() != 0)
			map.put(key, value);
	}

	@Get("json")
	public BrapiListResource<BrapiMarkerProfile> getJson()
	{
		LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
		addParameter(parameters, "germinatebase_id", germplasmId);
		addParameter(parameters, "dataset_id", studyId);

		// Provide a default for the WHERE clause which won't filter the results
		if(parameters.size() < 1)
			parameters.put("1", "1");

		return markerProfileDAO.getAll(parameters, currentPage, pageSize);
	}
}