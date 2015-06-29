package hutton.brapi.server;

import hutton.brapi.data.*;
import hutton.brapi.resource.*;

import com.google.inject.*;

import org.restlet.resource.*;

/**
 * Given an id returns the markers and associated alleles which are associated with the markerprofile with that id.
 */
public class MarkerProfileServerResource extends BaseBrapiServerResource
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
	public MarkerProfileData getJson()
	{
		MarkerProfileData profile = markerProfileDAO.getById(id);

		if (profile != null)
			return profile;

		throw new ResourceException(404);
	}
}