package jhi.brapi.api.markerprofiles;

import jhi.brapi.api.*;

import org.restlet.resource.*;

/**
 * Given an id returns the markers and associated alleles which are associated with the markerprofile with that id.
 */
public class ServerMarkerProfileData extends BaseBrapiServerResource
{
	private MarkerProfileDAO markerProfileDAO = new MarkerProfileDAO();

	private String id;

	@Override
	public void doInit()
	{
		super.doInit();
		this.id = (String)getRequestAttributes().get("id");
	}

	@Get("json")
	public BrapiBaseResource<BrapiMarkerProfileData> getJson()
	{
		return markerProfileDAO.getById(id);
	}
}