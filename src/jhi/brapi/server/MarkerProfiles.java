package jhi.brapi.server;

import java.util.List;
import jhi.brapi.data.*;
import jhi.brapi.resource.*;

import org.restlet.resource.*;

/**
 * Queries the database for the Germplasm (germinatebase?) with the given ID then returns a JSON (Jackson)
 * representation of the Germplasm for API clients to consume.
 */
public class MarkerProfiles extends BaseBrapiServerResource
{
	private MarkerProfileDAO markerProfileDAO = new MarkerProfileDAO();

	@Get("json")
	public BasicResource<BrapiMarkerProfile> getJson()
	{
		List<BrapiMarkerProfile> list = markerProfileDAO.getAll();

		return new BasicResource<BrapiMarkerProfile>(list);
	}
}