package jhi.brapi.server;

import java.util.*;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

import org.restlet.resource.*;

/**
 * Given an id, returns the markerprofile ids which relate to the germplasm indicated by id.
 */
public class GermplasmMcpdSearch extends BaseBrapiServerResource
{
	private GermplasmDAO germplasmDAO = new GermplasmDAO();

	private String pui;
	private String dbId;
	private String genus;
	private String species;
	private String name;
	private String subtaxa;
	private String panel;
	private String collection;

	@Override
	public void doInit()
	{
		super.doInit();
		this.pui = getQueryValue("germplasmPUI");
		this.dbId = getQueryValue("germplasmDbId");
		this.genus = getQueryValue("germplasmGenus");
		this.species = getQueryValue("germplasmSpecies");
		this.name = getQueryValue("germplasmName");
		this.subtaxa = getQueryValue("germplasmSubTaxa");
		this.panel = getQueryValue("panel");
		this.collection = getQueryValue("collection");
	}

	@Get("json")
	public BrapiListResource<BrapiGermplasmMcpd> getJson()
	{
		LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
		addParameter(parameters, "germinatebase.general_identifier", pui);
		addParameter(parameters, "germinatebase.id", dbId);
		addParameter(parameters, "taxonomies.genus", genus);
		addParameter(parameters, "taxonomies.species", species);
		addParameter(parameters, "germinatebase.name", name);
		addParameter(parameters, "subtaxa.taxonomic_identifier", subtaxa);
		// TODO: panel, collection?

		if(parameters.size() < 1)
			parameters.put("1", "1");

		return germplasmDAO.getMcpdForSearch(parameters, currentPage, pageSize);
	}

	private void addParameter(Map<String, String> map, String key, String value)
	{
		if (value != null && value.length() != 0)
			map.put(key, value);
	}
}
