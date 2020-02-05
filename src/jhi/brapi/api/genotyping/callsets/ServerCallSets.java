package jhi.brapi.api.genotyping.callsets;

import jhi.brapi.api.*;
import org.restlet.resource.*;

import java.util.*;

public class ServerCallSets extends BaseBrapiServerResource
{
	private CallSetDAO callSetDAO = new CallSetDAO();

	private String callSetDbId;
	private String callSetName;
	private String variantSetDbId;
	private String sampleDbId;
	private String germplasmDbId;

	@Override
	public void doInit()
	{
		super.doInit();

		callSetDbId = getQueryValue("callSetDbId");
		callSetName = getQueryValue("callSetName");
		variantSetDbId = getQueryValue("variantSetDbId");
		sampleDbId = getQueryValue("sampleDbId");
		germplasmDbId = getQueryValue("germplasmDbId");
	}

	@Get("json")
	public BrapiListResource<CallSet> getJson()
	{
		String folder = getContext().getParameters().getFirstValue("hdf5-folder");

		LinkedHashMap<String, List<String>> parameters = new LinkedHashMap<>();
		addParameter(parameters, "", callSetDbId);
		addParameter(parameters, "", callSetName);
		addParameter(parameters, "", variantSetDbId);
		addParameter(parameters, "", sampleDbId);
		addParameter(parameters, "", germplasmDbId);

		return callSetDAO.getAll(folder, variantSetDbId, currentPage, pageSize);
	}
}