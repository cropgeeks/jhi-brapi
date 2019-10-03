package jhi.brapi.api.genotyping.genomemaps;

import jhi.brapi.api.*;

import org.restlet.resource.*;

public class ServerGenomeMapLinkageGroups extends BaseBrapiServerResource
{
	private MapDAO mapDAO = new MapDAO();

	private String id;

	@Override
	public void doInit()
	{
		super.doInit();

		id = (String)getRequestAttributes().get("id");
	}

	@Get("json")
	public BrapiListResource<LinkageGroup> getJson()
	{
		return mapDAO.getByIdLinkageGroups(id, currentPage, pageSize);
	}
}