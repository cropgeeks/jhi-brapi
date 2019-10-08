package jhi.brapi.api.genotyping.variantsets;

import jhi.brapi.api.*;
import jhi.brapi.api.genotyping.genomemaps.*;
import org.restlet.resource.*;

import java.util.*;

public class ServerVariantSets extends BaseBrapiServerResource
{
	private VariantSetDAO variantSetDAO = new VariantSetDAO();

	@Override
	public void doInit()
	{
		super.doInit();
	}

	@Get("json")
	public BrapiListResource<VariantSet> getJson()
	{
		String folder = getContext().getParameters().getFirstValue("hdf5-folder");

		return variantSetDAO.getAll(folder, currentPage, pageSize);
	}
}