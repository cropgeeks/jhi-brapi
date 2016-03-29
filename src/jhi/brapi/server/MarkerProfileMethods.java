package jhi.brapi.server;

import java.util.*;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

import org.restlet.resource.*;

/**
 * Returns a list of all methods associated with a marker profile
 */
public class MarkerProfileMethods extends BaseBrapiServerResource
{
	private MarkerProfileMethodsDAO markerProfileMethodsDAO = new MarkerProfileMethodsDAO();

	@Get("json")
	public BasicResource<BrapiMarkerProfileMethod> getJson()
	{
		return markerProfileMethodsDAO.getAll(currentPage, pageSize);
	}
}