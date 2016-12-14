package jhi.brapi.api.studies;

import jhi.brapi.api.*;

import org.restlet.resource.*;

/**
 * Queries the database for the ServerGermplasmSearch (germinatebase?) with the given ID then returns a JSON (Jackson) representation of the ServerGermplasmSearch for API
 * clients to consume.
 */
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
	}

	@Get("json")
	public BrapiListResource<BrapiStudies> getJson()
	{
		BrapiListResource<BrapiStudies> result;

		result = studiesDAO.getAll(currentPage, pageSize, studyType, programId, locationId, seasonId);

		return result;
	}

	@Post("json")
	public BrapiListResource<BrapiStudies> postJson(BrapiStudiesPost params)
	{
		setFromPostBody(params);
		setParams(params);

		return getJson();
	}

	private void setFromPostBody(BrapiStudiesPost params)
	{
		studyType = params.getStudyType();
	}

	private void setParams(BrapiStudiesPost params)
	{
		setPaginationParameters(params);
	}
}