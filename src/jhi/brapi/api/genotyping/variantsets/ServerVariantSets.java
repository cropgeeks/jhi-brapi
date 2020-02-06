package jhi.brapi.api.genotyping.variantsets;

import jhi.brapi.api.*;
import jhi.brapi.api.genotyping.genomemaps.*;
import org.restlet.resource.*;

import java.util.*;

public class ServerVariantSets extends BaseBrapiServerResource
{
	private VariantSetDAO variantSetDAO = new VariantSetDAO();

	private String callSetDbId;
	private String studyName;
	private String variantDbId;
	private String variantSetDbId;
	private String studyDbId;

	@Override
	public void doInit()
	{
		super.doInit();

		this.callSetDbId = getQueryValue("callSetDbId");
		this.studyName = getQueryValue("studyName");
		this.variantDbId = getQueryValue("variantDbId");
		this.variantSetDbId = getQueryValue("variantSetDbId");
		this.studyDbId = getQueryValue("studyDbId");
	}

	@Get("json")
	public BrapiListResource<VariantSet> getJson()
	{
		String folder = getContext().getParameters().getFirstValue("hdf5-folder");

		LinkedHashMap<String, List<String>> parameters = new LinkedHashMap<>();
		addParameter(parameters, "experiments.id", studyDbId);
		addParameter(parameters, "experimenttypes.id", ""+1);

		return variantSetDAO.getAll(folder, parameters, currentPage, pageSize);
	}
}