package jhi.brapi.api.markerprofiles;

import java.util.*;

public class BrapiMarkerProfileData
{
	private String markerprofileId;
	private String germplasmId;
	private String extractId;
	private String analysisMethod;
	// Seems to be the best way to have Jackson automatically generate a data array in json with comma separated
	// key : value pairs as outlined in the apiary.io documentation for the API
	private List<Map<String, String>> data;

	public String getMarkerprofileId()
		{ return markerprofileId; }

	public void setMarkerprofileId(String markerprofileId)
		{ this.markerprofileId = markerprofileId; }

	public String getGermplasmId()
		{ return germplasmId; }

	public void setGermplasmId(String germplasmId)
		{ this.germplasmId = germplasmId; }

	public String getExtractId()
		{ return extractId; }

	public void setExtractId(String extractId)
		{ this.extractId = extractId; }

	public String getAnalysisMethod()
		{ return analysisMethod; }

	public void setAnalysisMethod(String analysisMethod)
		{ this.analysisMethod = analysisMethod; }

	public List<Map<String, String>> getData()
		{ return data; }

	public void setData(List<Map<String, String>> data)
		{ this.data = data; }
}