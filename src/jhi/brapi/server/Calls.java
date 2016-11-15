package jhi.brapi.server;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

public class Calls extends BaseBrapiServerResource
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
