package jhi.brapi.api.markerprofiles;

import java.util.*;

public class BrapiMarkerProfileData
{
	private String markerprofileDbId;
	private String germplasmDbId;
	private String extractDbId;
	private String analysisMethod;
	// Seems to be the best way to have Jackson automatically generate a data array in json with comma separated
	// key : value pairs as outlined in the apiary.io documentation for the API
	private List<Map<String, String>> data;

	public String getMarkerprofileDbId()
		{ return markerprofileDbId; }

	public void setMarkerprofileDbId(String markerprofileDbId)
		{ this.markerprofileDbId = markerprofileDbId; }

	public String getGermplasmDbId()
		{ return germplasmDbId; }

	public void setGermplasmDbId(String germplasmDbId)
		{ this.germplasmDbId = germplasmDbId; }

	public String getExtractDbId()
		{ return extractDbId; }

	public void setExtractDbId(String extractDbId)
		{ this.extractDbId = extractDbId; }

	public String getAnalysisMethod()
		{ return analysisMethod; }

	public void setAnalysisMethod(String analysisMethod)
		{ this.analysisMethod = analysisMethod; }

	public List<Map<String, String>> getData()
		{ return data; }

	public void setData(List<Map<String, String>> data)
		{ this.data = data; }
}