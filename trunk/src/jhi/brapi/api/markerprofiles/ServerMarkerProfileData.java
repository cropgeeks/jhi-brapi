package jhi.brapi.api.markerprofiles;

import jhi.brapi.api.*;
import jhi.brapi.util.*;

import org.restlet.resource.*;

/**
 * Given an id returns the markers and associated alleles which are associated with the markerprofile with that id.
 */
public class ServerMarkerProfileData extends BaseBrapiServerResource
{
	private MarkerProfileDAO markerProfileDAO = new MarkerProfileDAO();

	private String id;
	private GenotypeEncodingParams params = new GenotypeEncodingParams();

	@Override
	public void doInit()
	{
		super.doInit();
		this.id = (String)getRequestAttributes().get("id");

		getQuery().forEach(parameter ->
		{
			if (parameter.getName().equals("unknownString"))
				params.setUnknownString(parameter.getValue());
			else if (parameter.getName().equals("sepPhased"))
				params.setSepPhased(parameter.getValue());
			else if (parameter.getName().equals("sepUnphased"))
				params.setSepUnphased(parameter.getValue());
			else if (parameter.getName().equals("expandHomozygotes"))
				params.setCollapse(!Boolean.valueOf(parameter.getValue()));
		});
	}

	@Get("json")
	public BrapiBaseResource<BrapiMarkerProfileData> getJson()
	{
		return markerProfileDAO.getById(getContext(), id, params, currentPage, pageSize);
	}
}