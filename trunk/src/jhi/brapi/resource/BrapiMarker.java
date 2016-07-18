package jhi.brapi.resource;

import java.util.*;

/**
 * Created by gs40939 on 17/06/2015.
 */
public class BrapiMarker
{
	private String       markerDbId;
	private String       defaultDisplayName;
	private List<String> synonyms;

	public String getMarkerDbId()
	{
		return markerDbId;
	}

	public void setMarkerDbId(String markerDbId)
	{
		this.markerDbId = markerDbId;
	}

	public String getDefaultDisplayName()
	{
		return defaultDisplayName;
	}

	public void setDefaultDisplayName(String defaultDisplayName)
	{
		this.defaultDisplayName = defaultDisplayName;
	}

	public List<String> getSynonyms()
	{
		return synonyms;
	}

	public void setSynonyms(List<String> synonyms)
	{
		this.synonyms = synonyms;
	}
}
