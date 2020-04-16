package jhi.brapi.api.genotyping.callsets;

import jhi.brapi.api.*;
import org.restlet.resource.*;

public class ServerCallSet extends BaseBrapiServerResource
{
	private CallSetDAO callSetDAO = new CallSetDAO();

	private String id;

	@Override
	public void doInit()
	{
		super.doInit();
		this.id = getRequestAttributes().get("id").toString();
	}

	@Get("json")
	public BrapiBaseResource<CallSet> getJson()
	{
		String folder = getContext().getParameters().getFirstValue("hdf5-folder");

		return callSetDAO.getById(folder, id);
	}
}