package jhi.brapi.api.trials;

import org.restlet.resource.*;

import java.util.*;

import jhi.brapi.api.*;

public class ServerTrialList extends BaseBrapiServerResource
{
	private TrialDAO trialDAO = new TrialDAO();

	private String  programDbId;
	private String  locationDbId;
	private Boolean active;
	private String  sortBy;
	private String  sortOrder;

	@Override
	public void doInit()
	{
		super.doInit();

		programDbId = getQueryValue("programDbId");
		locationDbId = getQueryValue("locationDbId");

		try
		{
			active = Boolean.parseBoolean(getQueryValue("active"));
		}
		catch (Exception e)
		{
			active = null;
		}

		sortBy = getQueryValue("sortBy");
		sortOrder = getQueryValue("sortOrder");
	}

	@Get("json")
	public BrapiListResource<BrapiTrial> getJson()
	{
		Map<String, List<String>> parameters = new LinkedHashMap<>();
		if (programDbId != null)
			addParameterPost(parameters, "datasets.experiment_id", Collections.singletonList(programDbId));
		if (locationDbId != null)
			addParameterPost(parameters, "datasets.location_id", Collections.singletonList(locationDbId));

		getValidatedSortBy();

		return trialDAO.getAll(parameters, sortBy, currentPage, pageSize);
	}

	private void addParameterPost(Map<String, List<String>> map, String key, List<String> value)
	{
		if (value != null && !value.isEmpty())
			map.put(key, value);
	}

	private void getValidatedSortBy()
	{
		if (sortBy != null && !sortBy.isEmpty())
		{
			switch (sortBy)
			{
				case "trialDbId":
					sortBy = "datasets.id";
					break;
				case "trialName":
					sortBy = "datasets.name";
					break;
				case "programDbId":
					sortBy = "experiments.id";
					break;
				case "programName":
					sortBy = "experiments.name";
					break;
				case "startDate":
					sortBy = "datasets.date_start";
					break;
				case "endDate":
					sortBy = "datasets.date_end";
					break;
				case "active":
				default:
					sortBy = "1";
					break;
			}
		}
		else
		{
			sortBy = "1";
		}

		if (sortOrder != null && !sortOrder.isEmpty())
		{
			switch (sortOrder.toLowerCase())
			{
				case "asc":
					sortOrder = "ASC";
					break;
				case "desc":
					sortOrder = "DESC";
					break;
				default:
					sortOrder = "ASC";
			}
		}
		else
		{
			sortOrder = "ASC";
		}

		sortBy = "ORDER BY " + sortBy + " " + sortOrder;
	}
}