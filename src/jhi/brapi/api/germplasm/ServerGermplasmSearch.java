package jhi.brapi.api.germplasm;

import java.util.*;

import jhi.brapi.api.*;

import org.restlet.resource.*;

/**
 * Given an id, returns the markerprofile ids which relate to the germplasm indicated by id.
 */
public class ServerGermplasmSearch extends BaseBrapiServerResource
{
	private GermplasmDAO germplasmDAO = new GermplasmDAO();

	private List<String> germplasmPUIs;
	private List<String> germplasmDbIds;
	private List<String> germplasmSpecies;
	private List<String> germplasmGenus;
	private List<String> germplasmNames;
	private List<String> accessionNumbers;

	@Override
	public void doInit()
	{
		super.doInit();
		germplasmDbIds = parseGetParameterList("germplasmDbId");
		germplasmNames = parseGetParameterList("germplasmName");
		germplasmPUIs = parseGetParameterList("germplasmPUI");
	}

	@Get("json")
	public BrapiListResource<BrapiGermplasm> getJson()
	{
		Map<String, List<String>> parameters = new LinkedHashMap<>();
		addParameterPost(parameters, "germinatebase.id", germplasmDbIds);
		addParameterPost(parameters, "germinatebase.name", germplasmNames);
		addParameterPost(parameters, "genus", germplasmGenus);
		addParameterPost(parameters, "species", germplasmSpecies);

		return germplasmDAO.getMcpdForSearch(parameters, currentPage, pageSize);
	}

	private void addParameter(Map<String, List<String>> map, String key, String value)
	{
		if (value != null && value.length() != 0)
			map.put(key, Collections.singletonList(value));
	}

	@Post("json")
	public BrapiListResource<BrapiGermplasm> postJson(BrapiGermplasmPost params)
	{
		germplasmPUIs = params.getGermplasmPUIs();
		germplasmDbIds = params.getGermplasmDbIds();
		germplasmSpecies = params.getGermplasmSpecies();
		germplasmGenus = params.getGermplasmGenus();
		germplasmNames = params.getGermplasmNames();
		accessionNumbers = params.getAccessionNumbers();

		setPaginationParameters(params);

		return getJson();
	}

	private void addParameterPost(Map<String, List<String>> map, String key, List<String> value)
	{
		if (value != null && !value.isEmpty())
			map.put(key, value);
	}
}