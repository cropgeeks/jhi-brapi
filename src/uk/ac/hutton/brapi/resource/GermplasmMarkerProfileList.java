package uk.ac.hutton.brapi.resource;

import java.util.*;

/**
 * Created by gs40939 on 12/05/2015.
 */
public class GermplasmMarkerProfileList
{
	private int id;
	private List<String> markerProfiles;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public List<String> getMarkerProfiles()
	{
		return markerProfiles;
	}

	public void setMarkerProfiles(List<String> markerProfileIds)
	{
		this.markerProfiles = markerProfileIds;
	}

	@Override
	public String toString()
	{
		return "GermplasmMarkerProfileList{" +
			"id=" + id +
			", markerProfileIds=" + markerProfiles +
			'}';
	}
}
