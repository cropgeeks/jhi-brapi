package jhi.brapi.server;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

import org.restlet.resource.*;

/**
 * Queries the database for the Germplasm (germinatebase?) with the given ID then returns a JSON (Jackson)
 * representation of the Germplasm for API clients to consume.
 */
public class MarkerProfileListServerResource extends BaseBrapiServerResource
{
	private MarkerProfileDAO markerProfileDAO = new MarkerProfileDAOImpl();

	@Get("json")
	public MarkerProfileList getJson()
	{
		MarkerProfileList markerProfileList = markerProfileDAO.getAll();

		if (markerProfileList != null)
			return markerProfileList;

		throw new ResourceException(404);
	}
}