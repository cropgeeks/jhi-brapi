package jhi.brapi.resource;

/**
 * Created by gs40939 on 30/04/2015.
 */
public class BrapiMarkerProfile
{
	private String markerProfileDbId;
	private int germplasmDbId;
	private int extractDbId;
	private String analysisMethod;
	private int resultCount;

	public String getMarkerProfileDbId()
	{
		return markerProfileDbId;
	}

	public void setMarkerProfileDbId(String markerProfileDbId)
	{
		this.markerProfileDbId = markerProfileDbId;
	}

	public int getGermplasmDbId()
	{
		return germplasmDbId;
	}

	public void setGermplasmDbId(int germplasmDbId)
	{
		this.germplasmDbId = germplasmDbId;
	}

	public int getExtractDbId()
	{
		return extractDbId;
	}

	public void setExtractDbId(int extractDbId)
	{
		this.extractDbId = extractDbId;
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

	@Override
	public String toString()
	{
		return "MarkerProfile{" +
			"markerProfileDbId=" + markerProfileDbId +
			", germplasmDbId=" + germplasmDbId +
			", extractDbId=" + extractDbId +
			", analysisMethod='" + analysisMethod + '\'' +
			", resultCount='" + resultCount + '\'' +
			'}';
	}
}
