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

		germplasmDbIds = parseGetParameterList("germplasmDbIds");
		observationVariableDbIds = parseGetParameterList("observationVariableDbIds");
		studyDbIds = parseGetParameterList("studyDbIds");
		locationDbIds = parseGetParameterList("locationDbIds");
		programDbIds = parseGetParameterList("programDbIds");
		seasonDbIds = parseGetParameterList("seasonDbIds");
		observationLevel = getQueryValue("observationLevel");
	}

	@Get("json")
	public BrapiListResource<BrapiPhenotype> getJson()
	{
		return getData();
	}

	@Post("json")
	public BrapiListResource<BrapiPhenotype> postJson(BrapiPhenotypePost params)
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

	private BrapiListResource<BrapiPhenotype> getData()
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