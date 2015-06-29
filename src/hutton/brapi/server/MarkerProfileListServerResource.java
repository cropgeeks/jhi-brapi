package hutton.brapi.server;

import hutton.brapi.data.*;
import hutton.brapi.resource.*;

import com.google.inject.*;

import org.restlet.resource.*;

/**
 * Queries the database for the Germplasm (germinatebase?) with the given ID then returns a JSON (Jackson)
 * representation of the Germplasm for API clients to consume.
 */
public class MarkerProfileListServerResource extends BaseBrapiServerResource
{
	@Inject
	private MarkerProfileDAO markerProfileDAO;

	@Get("json")
	public MarkerProfileList getJson()
	{
		MarkerProfileList markerProfileList = markerProfileDAO.getAll();

		if (markerProfileList != null)
			return markerProfileList;

		throw new ResourceException(404);
	}
}