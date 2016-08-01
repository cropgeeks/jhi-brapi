package jhi.brapi.resource;

import java.util.*;

/**
 * Created by gs40939 on 12/05/2015.
 */
public class BrapiGermplasmMarkerProfiles
{
	private int          germplasmDbId;
	private List<String> markerProfiles;

	public int getGermplasmDbId()
	{
		return germplasmDbId;
	}

	public void setGermplasmDbId(int germplasmDbId)
	{
		this.germplasmDbId = germplasmDbId;
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
			"germplasmDbId=" + germplasmDbId +
			", markerProfileIds=" + markerProfiles +
			'}';
	}
}
