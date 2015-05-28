package hutton.brapi.resource;

import java.util.*;

/**
 * Created by gs40939 on 30/04/2015.
 */
public class MarkerProfile
{
	private String markerprofileId;
	private int germplasmId;
	private int extractId;
	private String analysisMethod;
	private String encoding;
	// Seems to be the best way to have Jackson automatically generate a data array in json with comma separated
	// key : value pairs as outlined in the apiary.io documentation for the API
	private HashMap<String, String> data;

	public String getMarkerprofileId()
	{
		return markerprofileId;
	}

	public void setMarkerprofileId(String markerprofileId)
	{
		this.markerprofileId = markerprofileId;
	}

	public int getGermplasmId()
	{
		return germplasmId;
	}

	public void setGermplasmId(int germplasmId)
	{
		this.germplasmId = germplasmId;
	}

	public int getExtractId()
	{
		return extractId;
	}

	public void setExtractId(int extractId)
	{
		this.extractId = extractId;
	}

	public String getAnalysisMethod()
	{
		return analysisMethod;
	}

	public void setAnalysisMethod(String analysisMethod)
	{
		this.analysisMethod = analysisMethod;
	}

	public String getEncoding()
	{
		return encoding;
	}

	public void setEncoding(String encoding)
	{
		this.encoding = encoding;
	}

	public HashMap<String, String> getData()
	{
		return data;
	}

	public void setData(HashMap<String, String> data)
	{
		this.data = data;
	}

	@Override
	public String toString()
	{
		return "MarkerProfile{" +
			"markerprofileId=" + markerprofileId +
			", germplasmId=" + germplasmId +
			", extractId=" + extractId +
			", analysisMethod='" + analysisMethod + '\'' +
			", encoding='" + encoding + '\'' +
			", data=" + data +
			'}';
	}
}
