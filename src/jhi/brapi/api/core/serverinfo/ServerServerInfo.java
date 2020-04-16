package jhi.brapi.api.core.serverinfo;

import jhi.brapi.api.*;

public class ServerServerInfo extends BaseBrapiServerResource
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
	public BrapiBaseResource<BrapiServerInfo> getJson()
	{
		BrapiBaseResource<BrapiServerInfo> serverInfoResponse = ServerInfoDAO.getAll(dataType);

		return serverInfoResponse;
	}
}