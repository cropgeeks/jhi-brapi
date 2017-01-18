package jhi.brapi.api.studies;

import java.util.*;

import jhi.brapi.api.*;

import org.restlet.resource.*;

public class ServerStudies extends BaseBrapiServerResource
{
	private static final String PARAM_PROGRAM_ID = "programDbId";
	private static final String PARAM_LOCATION_ID = "locationDbId";
	private static final String PARAM_SEASON_ID = "seasonDbId";
	private static final String PARAM_STUDY_TYPE = "studyType";

	private StudiesDAO studiesDAO = new StudiesDAO();

	private String programId;
	private String locationId;
	private String seasonId;
	private String studyType;

	@Override
	public void doInit()
	{
		super.doInit();

		this.programId = getQueryValue(PARAM_PROGRAM_ID);
		this.locationId = getQueryValue(PARAM_LOCATION_ID);
		this.seasonId = getQueryValue(PARAM_SEASON_ID);
		this.studyType = getQueryValue(PARAM_STUDY_TYPE);

		// Fudge the param into an acceptable germinate value
		if (studyType != null && studyType.equals("trial"))
			studyType = "trials";
	}

	@Get("json")
	public BrapiListResource<BrapiStudies> getJson()
	{
		LinkedHashMap<String, List<String>> parameters = new LinkedHashMap<>();
		addParameter(parameters, "experimenttypes.description", studyType);
		addParameter(parameters, "experiments.id", programId);
		addParameter(parameters, "datasets.location_id", locationId);
		addParameter(parameters, "YEAR(phenotypedata.recording_date)", seasonId);

		return studiesDAO.getAll(parameters, currentPage, pageSize);
	}

	@Post("json")
	public BrapiListResource<BrapiStudies> postJson(BrapiStudiesPost params)
	{
		studyType = params.getStudyType();
		setPaginationParameters(params);

		return getJson();
	}

	private void addParameter(Map<String, List<String>> map, String key, String value)
	{
		if (value != null && value.length() != 0)
			map.put(key, Collections.singletonList(value));
	}
}