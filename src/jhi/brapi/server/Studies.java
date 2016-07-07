package jhi.brapi.server;

import jhi.brapi.data.StudiesDAO;
import jhi.brapi.resource.BasicResource;
import jhi.brapi.resource.BrapiGermplasm;
import jhi.brapi.resource.BrapiStudies;
import jhi.brapi.resource.DataResult;
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

	private StudiesDAO studiesDAO = new StudiesDAO();

	private String programId;
	private String locationId;
	private String seasonId;

	@Override
	public void doInit()
	{
		super.doInit();

		this.programId = getQueryValue(PARAM_PROGRAM_ID);
		this.locationId = getQueryValue(PARAM_LOCATION_ID);
		this.seasonId = getQueryValue(PARAM_SEASON_ID);
	}

	@Get("json")
	public BasicResource<DataResult<BrapiStudies>> getJson()
	{
		BasicResource<DataResult<BrapiStudies>> result;

		result = studiesDAO.getAll(currentPage, pageSize);

		return result;
	}
}