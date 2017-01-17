package jhi.brapi.api.markers;

import java.util.*;

public class BrapiMarker
{
	private String markerDbId;
	private String defaultDisplayName;
	private List<String> synonyms = new ArrayList<>();
	private List<String> refAlt = new ArrayList<>();
	private String type;
	private List<String> analysisMethods = new ArrayList<>();

	public String getMarkerDbId()
		{ return markerDbId; }

	public void setMarkerDbId(String markerDbId)
		{ this.markerDbId = markerDbId; }

	public String getDefaultDisplayName()
		{ return defaultDisplayName; }

	public void setDefaultDisplayName(String defaultDisplayName)
		{ this.defaultDisplayName = defaultDisplayName; }

	public List<String> getSynonyms()
		{ return synonyms; }

	public void setSynonyms(List<String> synonyms)
		{ this.synonyms = synonyms; }

	public List<String> getRefAlt()
		{ return refAlt; }

	public void setRefAlt(List<String> refAlt)
		{ this.refAlt = refAlt;}

	public String getType()
		{ return type; }

	public void setType(String type)
		{ this.type = type; }

	public List<String> getAnalysisMethods()
		{ return analysisMethods; }

	public void setAnalysisMethods(List<String> analysisMethods)
		{ this.analysisMethods = analysisMethods; }

	public enum MatchingMethod
	{
		EXACT,
		WILDCARD;

		public static MatchingMethod getValue(String input)
		{
			if (input == null || input.equals(""))
			{
				return EXACT;
			}
			else
			{
				try
				{
					return MatchingMethod.valueOf(input.toUpperCase());
				}
				catch (Exception e)
				{
					// TODO: Return a 501 HTTP error code
					return EXACT;
				}
			}
		}
	}
}