package jhi.brapi.api.genomemaps;

import jhi.brapi.api.*;

import jhi.brapi.api.Status;
import jhi.brapi.client.*;
import org.restlet.data.*;
import org.restlet.resource.*;

/**
 * Given an id, returns the information on the map indicated by the id. This extends to its name and among other things,
 * a list of markers which are on the given map and their positions.
 */
public class ServerGenomeMapMetaData extends BaseBrapiServerResource
{
	private MapDAO mapDAO = new MapDAO();

	private String id;

	@Override
	public void doInit()
	{
		super.doInit();
		this.id = (String)getRequestAttributes().get("id");
	}

	@Get("json")
	public BrapiBaseResource<BrapiMapMetaData> getJson()
	{
		BrapiBaseResource<BrapiMapMetaData> result = mapDAO.getById(id, currentPage, pageSize);

		setHttpResponseCode(result.getMetadata().getStatus());

		return result;
	}
}