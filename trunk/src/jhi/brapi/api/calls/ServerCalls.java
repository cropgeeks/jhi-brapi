package jhi.brapi.api.calls;

import jhi.brapi.api.*;

public class ServerCalls extends BaseBrapiServerResource
{
	private String dataType;

	@Override
	public void doInit()
	{
		super.doInit();

		dataType = getQueryValue("datatype");
	}

	@Override
	public BrapiListResource<BrapiCall> getJson()
	{
		return CallDAO.getAll(dataType, currentPage, pageSize);
	}
}