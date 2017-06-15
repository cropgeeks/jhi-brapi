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

	private String species;

	@Override
	public void doInit()
	{
		super.doInit();
		species = getAttribute("species");
	}

	@Get("json")
	public BrapiListResource<BrapiGenomeMap> getJson()
	{
		// TODO: Filter on species? How?
		return mapDAO.getAll(currentPage, pageSize);
	}
}