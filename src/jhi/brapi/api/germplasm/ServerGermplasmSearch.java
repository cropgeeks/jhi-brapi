package jhi.brapi.api.germplasm;

import java.util.*;

import jhi.brapi.api.*;

import jhi.brapi.api.Status;
import jhi.brapi.api.markerprofiles.*;
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

		BrapiListResource<BrapiGermplasm> result = new BrapiListResource<BrapiGermplasm>();
		try
		{
			result = germplasmDAO.getMcpdForSearch(parameters, currentPage, pageSize);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result.getMetadata().getStatus().add(new Status("500", "The server could not complete the request"));
			setStatus(org.restlet.data.Status.SERVER_ERROR_INTERNAL);
			return result;
		}

		if (result.data().isEmpty())
		{
			result.getMetadata().getStatus().add(new Status("40", "No objects found for given parameters"));
			setStatus(org.restlet.data.Status.CLIENT_ERROR_BAD_REQUEST);
		}

		return result;
	}

	private void addParameter(Map<String, List<String>> map, String key, String value)
	{
		if (value != null && value.length() != 0)
			map.put(key, Collections.singletonList(value));
	}

	@Post("json")
	public BrapiListResource<BrapiGermplasm> postJson(BrapiGermplasmPost params)
	{
		if (params.getGermplasmPUIs() != null)
			germplasmPUIs = params.getGermplasmPUIs();
		if (params.getGermplasmDbIds() != null)
			germplasmDbIds = params.getGermplasmDbIds();
		if (params.getGermplasmSpecies() != null)
			germplasmSpecies = params.getGermplasmSpecies();
		if (params.getGermplasmGenus() != null)
			germplasmGenus = params.getGermplasmGenus();
		if (params.getGermplasmNames() != null)
			germplasmNames = params.getGermplasmNames();
		if (params.getAccessionNumbers() != null)
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