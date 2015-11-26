package jhi.brapi.server;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

import org.restlet.resource.*;

/**
 * Returns a list of all methods associated with a marker profile
 */
public class MarkerProfileMethodsListServerResource extends BaseBrapiServerResource
{
	private MarkerProfileMethodsDAO markerProfileMethodsDAO = new MarkerProfileMethodsDAO();

	@Get("json")
	public MarkerProfileMethodList getJson()
	{
		MarkerProfileMethodList methodList = markerProfileMethodsDAO.getAll();

		if (methodList != null)
			return methodList;

		throw new ResourceException(404);
	}
}