package hutton.brapi.server;

import hutton.brapi.data.*;
import hutton.brapi.resource.*;

import com.fasterxml.jackson.databind.*;
import com.google.inject.*;

import org.restlet.ext.guice.*;
import org.restlet.ext.jackson.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

/**
 * Given an id returns the markers and associated alleles which are associated with the markerprofile with that id.
 */
public class MarkerProfileCountServerResource extends SelfInjectingServerResource
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
	public MarkerProfileCount retrieve()
	{
		MarkerProfileCount profileCount = markerProfileDAO.getCountById(id);

		if (profileCount != null)
			return profileCount;

		throw new ResourceException(404);
	}
}