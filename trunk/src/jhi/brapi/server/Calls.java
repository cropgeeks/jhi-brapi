package jhi.brapi.server;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

/**
 * @author Sebastian Raubach
 */
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
	public BasicResource<DataResult<BrapiCall>> getJson()
	{
		return CallDAO.getAll(dataType, currentPage, pageSize);
	}
}
