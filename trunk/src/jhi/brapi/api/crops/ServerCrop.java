package jhi.brapi.api.crops;

import org.restlet.resource.*;

import jhi.brapi.api.*;

public class ServerCrop extends BaseBrapiServerResource
{
	private CropDAO cropDAO = new CropDAO();

	@Get("json")
	public BrapiListResource<String> getJson()
	{
		return cropDAO.getAll(currentPage, pageSize);
	}
}
