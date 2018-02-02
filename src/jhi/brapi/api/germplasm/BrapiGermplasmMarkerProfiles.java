package jhi.brapi.api.germplasm;

import java.util.*;

public class BrapiGermplasmMarkerProfiles
{
	private String germplasmDbId;
	private List<String> markerProfileDbIds;

	public String getGermplasmDbId()
		{ return germplasmDbId; }

	public void setGermplasmDbId(String germplasmDbId)
		{ this.germplasmDbId = germplasmDbId; }

	public List<String> getMarkerProfileDbIds()
		{ return markerProfileDbIds; }

	public void setMarkerProfileDbIds(List<String> markerProfileIds)
		{ this.markerProfileDbIds = markerProfileIds; }

	@Override
	public String toString()
	{
		return "GermplasmMarkerProfileList{" +
			"germplasmDbId=" + germplasmDbId +
			", markerProfileIds=" + markerProfileDbIds +
			'}';
	}
}