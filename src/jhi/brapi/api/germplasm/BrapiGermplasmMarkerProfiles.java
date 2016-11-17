package jhi.brapi.api.germplasm;

import java.util.*;

public class BrapiGermplasmMarkerProfiles
{
	private String germplasmDbId;
	private List<String> markerProfiles;

	public String getGermplasmDbId()
		{ return germplasmDbId; }

	public void setGermplasmDbId(String germplasmDbId)
		{ this.germplasmDbId = germplasmDbId; }

	public List<String> getMarkerProfiles()
		{ return markerProfiles; }

	public void setMarkerProfiles(List<String> markerProfileIds)
		{ this.markerProfiles = markerProfileIds; }

	@Override
	public String toString()
	{
		return "GermplasmMarkerProfileList{" +
			"germplasmDbId=" + germplasmDbId +
			", markerProfileIds=" + markerProfiles +
			'}';
	}
}