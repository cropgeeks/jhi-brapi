package jhi.brapi.resource;

import java.util.*;

/**
 * Created by gs40939 on 12/05/2015.
 */
public class BrapiGermplasmMarkerProfiles
{
	private int germplasmId;
	private List<String> markerProfiles;

	public int getGermplasmId()
	{
		return germplasmId;
	}

	public void setGermplasmId(int germplasmId)
	{
		this.germplasmId = germplasmId;
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
			"germplasmId=" + germplasmId +
			", markerProfileIds=" + markerProfiles +
			'}';
	}
}
