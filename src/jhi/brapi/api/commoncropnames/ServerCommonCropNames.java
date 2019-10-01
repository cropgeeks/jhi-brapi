package jhi.brapi.api.commoncropnames;

import org.restlet.resource.*;

import jhi.brapi.api.*;

public class ServerCommonCropNames extends BaseBrapiServerResource
{
	private CommonCropNamesDAO commonCropNamesDAO = new CommonCropNamesDAO();

	@Get("json")
	public BrapiListResource<String> getJson()
	{
		return commonCropNamesDAO.getAll(currentPage, pageSize);
	}
}
