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
 * Returns a list of all methods associated with a marker profile
 */
public class MarkerProfileMethodsListServerResource extends SelfInjectingServerResource
{
	@Inject
	private MarkerProfileMethodsDAO markerProfileMethodsDAO;

	@Get("json")
	public MarkerProfileMethodList retrieve()
	{
		MarkerProfileMethodList methodList = markerProfileMethodsDAO.getAll();

		if (methodList != null)
			return methodList;

		throw new ResourceException(404);
	}
}