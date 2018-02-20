package jhi.brapi.api.genomemaps;

import jhi.brapi.api.*;

import jhi.brapi.client.*;
import org.restlet.data.*;
import org.restlet.data.Status;
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
	private String type;

	@Override
	public void doInit()
	{
		super.doInit();
		species = getAttribute("species");
		type = getAttribute("type");
	}

	@Get("json")
	public BrapiListResource<BrapiGenomeMap> getJson()
	{
		// TODO: Filter on species? How?
		// TODO: Filter on type, how?
		BrapiListResource<BrapiGenomeMap> maps = mapDAO.getAll(currentPage, pageSize);

		setHttpResponseCode(maps.getMetadata().getStatus());

		return maps;
	}

	@Post("json")
	public BrapiListResource<BrapiGenomeMap> postJson(BrapiGenomeMapSearchPost mapSearchPost)
	{
		if (mapSearchPost.getSpecies() != null)
			species = mapSearchPost.getSpecies();
		if (mapSearchPost.getType() != null)
			type = mapSearchPost.getType();

		setPaginationParameters(mapSearchPost);

		return getJson();
	}
}