package jhi.brapi.api.genotyping.genomemaps;

import jhi.brapi.api.*;

import org.restlet.resource.*;

import java.util.*;

/**
 * Queries the database for the (Genome) MapList then returns a JSON (Jackson)
 * representation of the MapList for API clients
 * to consume.
 */
public class ServerGenomeMaps extends BaseBrapiServerResource
{
	private MapDAO mapDAO = new MapDAO();

	private String commonCropName;
	private String scientificName;
	private String type;
	private String programDbId;
	private String trialDbId;
	private String studyDbId;

	@Override
	public void doInit()
	{
		super.doInit();

		commonCropName = getQueryValue("commonCropName");
		scientificName = getQueryValue("scientificName");
		type = getQueryValue("type");
		programDbId = getQueryValue("programDbId");
		trialDbId = getQueryValue("trialDbId");
		studyDbId = getQueryValue("studyDbId");
	}

	@Get("json")
	public BrapiListResource<GenomeMap> getJson()
	{
		LinkedHashMap<String, List<String>> parameters = new LinkedHashMap<>();
		addParameter(parameters, null, commonCropName);
		addParameter(parameters, null, scientificName);
		addParameter(parameters, null, type);
		addParameter(parameters, null, programDbId);
		addParameter(parameters, null, trialDbId);
		addParameter(parameters, null, studyDbId);

		return mapDAO.getAll(parameters, currentPage, pageSize);
	}
}