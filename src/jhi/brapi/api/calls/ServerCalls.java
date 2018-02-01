package jhi.brapi.api.calls;

import static org.restlet.data.Status.*;

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
		BrapiListResource<BrapiCall> callResponse = CallDAO.getAll(dataType, currentPage, pageSize);



		return callResponse;
	}
}