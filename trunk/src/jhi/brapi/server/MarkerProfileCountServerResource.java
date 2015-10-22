package jhi.brapi.server;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

import com.google.inject.*;

import org.restlet.resource.*;

/**
 * Given an id returns the markers and associated alleles which are associated with the markerprofile with that id.
 */
public class MarkerProfileCountServerResource extends BaseBrapiServerResource
{
	@Inject
	private MarkerProfileDAO markerProfileDAO;

	private String id;

	@Override
	public void doInit()
	{
		super.doInit();
		this.id = (String)getRequestAttributes().get("id");
	}

	@Get("json")
	public MarkerProfileCount getJson()
	{
		MarkerProfileCount profileCount = markerProfileDAO.getCountById(id);

		if (profileCount != null)
			return profileCount;

		throw new ResourceException(404);
	}
}