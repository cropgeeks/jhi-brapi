package jhi.brapi.api.phenotypes;

import org.restlet.resource.*;

import java.util.*;
import java.util.stream.*;

import jhi.brapi.api.*;

/**
 * Given an id, returns the markerprofile ids which relate to the germplasm indicated by id.
 */
public class ServerPhenotypesSearch extends BaseBrapiServerResource
{
	private PhenotypesDAO phenotypesDAO = new PhenotypesDAO();

	private List<String> germplasmDbIds;
	private List<String> observationVariableDbIds;
	private List<String> studyDbIds;
	private List<String> locationDbIds;
	private List<String> programDbIds;
	private List<String> seasonDbIds;
	private String observationLevel;

	@Override
	public void doInit()
	{
		super.doInit();

		germplasmDbIds = parseList("germplasmDbIds");
		observationVariableDbIds = parseList("observationVariableDbIds");
		studyDbIds = parseList("studyDbIds");
		locationDbIds = parseList("locationDbIds");
		programDbIds = parseList("programDbIds");
		seasonDbIds = parseList("seasonDbIds");
		observationLevel = getQueryValue("observationLevel");
	}

	private List<String> parseList(String input)
	{
		input = getQueryValue(input);

		if (input != null)
			return Arrays.stream(input.split(","))
						 .map(String::trim)
						 .collect(Collectors.toList());
		else
			return null;
	}

	@Get("json")
	public BrapiListResource<BrapiPhenotypes> getJson()
	{
		return getData();
	}

	@Post("json")
	public BrapiListResource<BrapiPhenotypes> postJson(BrapiPhenotypesPost params)
	{
		germplasmDbIds = params.getGermplasmDbIds();
		observationVariableDbIds = params.getObservationVariableDbIds();
		studyDbIds = params.getStudyDbIds();
		locationDbIds = params.getLocationDbIds();
		programDbIds = params.getProgramDbIds();
		seasonDbIds = params.getSeasonDbIds();
		observationLevel = params.getObservationLevel();

		setPaginationParameters(params);

		return getData();
	}

	private BrapiListResource<BrapiPhenotypes> getData()
	{
		Map<String, List<String>> parameters = new LinkedHashMap<>();
		addParameterPost(parameters, "phenotypedata.germinatebase_id", germplasmDbIds);
		addParameterPost(parameters, "phenotypedata.phenotype_id", observationVariableDbIds);
		addParameterPost(parameters, "phenotypedata.dataset_id", studyDbIds);
		addParameterPost(parameters, "phenotypedata.location_id", locationDbIds);
		// TODO: Other parameters

		return phenotypesDAO.getPhenotypesForSearch(parameters, currentPage, pageSize);
	}

	private void addParameterPost(Map<String, List<String>> map, String key, List<String> value)
	{
		if (value != null && !value.isEmpty())
			map.put(key, value);
	}
}