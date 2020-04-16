package jhi.brapi.api.genotyping.variantsets;

import jhi.brapi.api.*;
import jhi.brapi.api.genotyping.callsets.*;

import org.restlet.resource.*;

public class ServerVariantSetCalls extends BaseBrapiServerResource
{
	private VariantSetDAO variantSetDAO = new VariantSetDAO();

	private String id;

	@Override
	public void doInit()
	{
		super.doInit();
		this.id = getRequestAttributes().get("id").toString();
	}

	@Get("json")
	public BrapiMasterDetailResourcePageToken<CallSetCalls> getJson()
	{
		String folder = getContext().getParameters().getFirstValue("hdf5-folder");

		if (pageToken != null)
			currentPage = Integer.parseInt(pageToken);

		System.out.println("pageSize: " + pageSize);

		return variantSetDAO.getVariantSetCallsById(folder, id, currentPage, pageSize);
	}
}
