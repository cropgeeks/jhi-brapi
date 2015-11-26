package jhi.brapi.server;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

import org.restlet.resource.*;

/**
 * Given an id returns the markers and associated alleles which are associated with the markerprofile with that id.
 */
public class MarkerProfileServerResource extends BaseBrapiServerResource
{
	private MarkerProfileDAO markerProfileDAO = new MarkerProfileDAOImpl();

	private String id;

	@Override
	public void doInit()
	{
		super.doInit();
		this.id = (String)getRequestAttributes().get("id");
	}

	@Get("json")
	public MarkerProfileData getJson()
	{
		MarkerProfileData profile = markerProfileDAO.getById(id);

		if (profile != null)
			return profile;

		throw new ResourceException(404);
	}
}