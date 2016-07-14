package jhi.brapi.server;

import jhi.brapi.data.StudiesDAO;
import jhi.brapi.resource.BasicResource;
import jhi.brapi.resource.BrapiStudies;
import jhi.brapi.resource.BrapiStudiesAsTable;
import jhi.brapi.resource.DataResult;
import org.restlet.resource.Get;

/**
 * Queries the database for the Germplasm (germinatebase?) with the given ID then returns a JSON (Jackson) representation of the Germplasm for API
 * clients to consume.
 */
public class StudiesAsTable extends BaseBrapiServerResource
{
	private StudiesDAO studiesDAO = new StudiesDAO();

	private String id;

	@Override
	public void doInit()
	{
		super.doInit();

		this.id = (String)getRequestAttributes().get("id");
	}

	@Get("json")
	public BasicResource<BrapiStudiesAsTable> getJson()
	{
		BasicResource<BrapiStudiesAsTable> result;

		result = studiesDAO.getTableById(id);

		return result;
	}
}