package jhi.brapi.api.core.studies;

import java.util.*;

import jhi.brapi.api.*;

import org.restlet.resource.*;

public class ServerStudies extends BaseBrapiServerResource
{
	// TODO: Add all the new query parameters
	private static final String PARAM_COMMON_CROP_NAME = "commonCropName";
	private static final String PARAM_PROGRAM_ID = "programDbId";
	private static final String PARAM_LOCATION_ID = "locationDbId";
	private static final String PARAM_SEASON_ID = "seasonDbId";
	private static final String PARAM_TRIAL_ID = "trialDbId";
	private static final String PARAM_STUDY_ID = "studyDbId";
	private static final String PARAM_ACTIVE = "active";
	private static final String PARAM_SORT_BY = "sortBy";
	private static final String PARAM_SORT_ORDER = "sortOrder";
	private static final String PARAM_STUDY_TYPE = "studyType";

	private StudiesDAO studiesDAO = new StudiesDAO();

	private String commonCropName;
	private String studyTypeDbId;
	private String programDbId;
	private String locationDbId;
	private String seasonDbId;
	private String trialDbId;
	private String studyDbId;
	private String studyType;
	private boolean active;
	private String sortBy;
	private String sortOrder;

	@Override
	public void doInit()
	{
		super.doInit();

		this.commonCropName = getQueryValue(PARAM_COMMON_CROP_NAME);
		this.programDbId = getQueryValue(PARAM_PROGRAM_ID);
		this.locationDbId = getQueryValue(PARAM_LOCATION_ID);
		this.seasonDbId = getQueryValue(PARAM_SEASON_ID);
		this.trialDbId = getQueryValue(PARAM_TRIAL_ID);
		this.studyDbId = getQueryValue(PARAM_STUDY_ID);
		this.studyType = getQueryValue(PARAM_STUDY_TYPE);
		this.active = Boolean.parseBoolean(getQueryValue(PARAM_ACTIVE));

		// TODO: implement user sorting
		this.sortBy = getQueryValue(PARAM_SORT_BY);
		this.sortOrder = getQueryValue(PARAM_SORT_ORDER);
	}

	@Get("json")
	public BrapiListResource<Study> getJson()
	{
		LinkedHashMap<String, List<String>> parameters = new LinkedHashMap<>();
		// TODO: passing in null keys for parameters we don't currently support filtering on...
		addParameter(parameters, null, commonCropName);
		addParameter(parameters, "experimenttypes.id", studyTypeDbId);
		addParameter(parameters, "experiments.id", programDbId);
		addParameter(parameters, "datasets.location_id", locationDbId);
		addParameter(parameters, null, seasonDbId);
		addParameter(parameters, "experiments.id", trialDbId);
		addParameter(parameters, "experiments.id", studyDbId);
		addParameter(parameters, null, ""+active);
		addParameter(parameters, "experimenttypes.description", studyType);

		return studiesDAO.getAll(parameters, currentPage, pageSize);
	}
}