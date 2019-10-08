package jhi.brapi.api.genotyping.callsets;

import jhi.brapi.api.*;
import org.restlet.resource.*;

public class ServerCallSets extends BaseBrapiServerResource
{
	private CallSetDAO callSetDAO = new CallSetDAO();

	@Override
	public void doInit()
	{
		super.doInit();
	}

	@Get("json")
	public BrapiListResource<CallSet> getJson()
	{
		String folder = getContext().getParameters().getFirstValue("hdf5-folder");

		return callSetDAO.getAll(folder, currentPage, pageSize);
	}
}