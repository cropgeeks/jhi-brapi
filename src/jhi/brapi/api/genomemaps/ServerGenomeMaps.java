package jhi.brapi.api.genomemaps;

import jhi.brapi.api.*;

import org.restlet.resource.*;

/**
 * Queries the database for the (Genome) MapList then returns a JSON (Jackson)
 * representation of the MapList for API clients
 * to consume.
 */
public class ServerGenomeMaps extends BaseBrapiServerResource
{
	private MapDAO mapDAO = new MapDAO();

	@Get("json")
	public BrapiListResource<BrapiGenomeMap> getJson()
	{
		return mapDAO.getAll(currentPage, pageSize);
	}
}