package jhi.brapi.server;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

import org.restlet.resource.Get;

/**
 * Queries the database for the Germplasm (germinatebase?) with the given ID then returns a JSON (Jackson) representation of the Germplasm for API
 * clients to consume.
 */
public class Studies extends BaseBrapiServerResource
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
}