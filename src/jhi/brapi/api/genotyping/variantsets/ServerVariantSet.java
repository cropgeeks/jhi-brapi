package jhi.brapi.api.genotyping.variantsets;

import jhi.brapi.api.*;
import jhi.brapi.api.core.studies.*;
import org.restlet.resource.*;

public class ServerVariantSet extends BaseBrapiServerResource
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
	public BrapiBaseResource<VariantSet> getJson()
	{
		String folder = getContext().getParameters().getFirstValue("hdf5-folder");

		return variantSetDAO.getById(folder, id);
	}
}