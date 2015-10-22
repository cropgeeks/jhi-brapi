package jhi.brapi.resource;

/**
 * Created by gs40939 on 19/05/2015.
 */
public class MarkerProfileCount
{
	private String markerProfileId;
	private int germplasmId;
	private int extractId;
	private String analysisMethod;
	private int resultCount;

	public String getMarkerProfileId()
	{
		return markerProfileId;
	}

	public void setMarkerProfileId(String markerProfileId)
	{
		this.markerProfileId = markerProfileId;
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

	public int getResultCount()
	{
		return resultCount;
	}

	public void setResultCount(int resultCount)
	{
		this.resultCount = resultCount;
	}
}