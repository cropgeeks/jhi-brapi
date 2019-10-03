package jhi.brapi.api.genotyping.genomemaps;

import jhi.brapi.api.*;
import org.restlet.resource.*;

import java.util.*;

/**
 * Queries the database for the (Genome) MapList then returns a JSON (Jackson)
 * representation of the MapList for API clients
 * to consume.
 */
public class ServerGenomeMap extends BaseBrapiServerResource
{
	private MapDAO mapDAO = new MapDAO();

	private String id;

	@Override
	public void doInit()
	{
		super.doInit();
		this.id = getRequestAttributes().get("id").toString();
	}

	@Get("json")
	public BrapiBaseResource<GenomeMap> getJson()
	{
		return mapDAO.getById(id, currentPage, pageSize);
	}
}