package jhi.brapi.api.calls;

import jhi.brapi.api.*;

public class ServerCalls extends BaseBrapiServerResource
{
	private String dataType = null;

	@Override
	public void doInit()
	{
		super.doInit();

		String dt = getQueryValue("datatype");
		if (dt != null)
			dataType = dt;

		String dT = getQueryValue("dataType");
		if (dT != null)
			dataType = dT;
	}

	@Override
	public BrapiListResource<BrapiCall> getJson()
	{
		BrapiListResource<BrapiCall> callResponse = CallDAO.getAll(dataType, currentPage, pageSize);

		return callResponse;
	}
}