package jhi.brapi.resource;

/**
 * Created by gs40939 on 30/04/2015.
 */
public class BrapiMarkerProfile
{
	private String markerprofileDbId;
	private int germplasmDbId;
	private String germplasmName;
	private int extractDbId;
	private String analysisMethod;
	private int resultCount;

	public String getMarkerprofileDbId()
	{
		return markerprofileDbId;
	}

	public void setMarkerprofileDbId(String markerprofileDbId)
	{
		this.markerprofileDbId = markerprofileDbId;
	}

	public int getGermplasmDbId()
	{
		return germplasmDbId;
	}

	public void setGermplasmDbId(int germplasmDbId)
	{
		this.germplasmDbId = germplasmDbId;
	}

	public String getGermplasmName()
	{
		return germplasmName;
	}

	public void setGermplasmName(String germplasmName)
	{
		this.germplasmName = germplasmName;
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
			"markerprofileDbId=" + markerprofileDbId +
			", germplasmDbId=" + germplasmDbId +
			", extractDbId=" + extractDbId +
			", analysisMethod='" + analysisMethod + '\'' +
			", resultCount='" + resultCount + '\'' +
			'}';
	}
}
