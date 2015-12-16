package jhi.brapi.resource;

/**
 * Created by gs40939 on 30/04/2015.
 */
public class BrapiMarkerProfile
{
	private String markerprofileId;
	private String germplasmId;
	private int extractId;
	private String analysisMethod;
	private String encoding;
	private int resultCount;

	public int getResultCount()
	{
		return resultCount;
	}

	public void setResultCount(int resultCount)
	{
		this.resultCount = resultCount;
	}

	public String getMarkerprofileId()
	{
		return markerprofileId;
	}

	public void setMarkerprofileId(String markerprofileId)
	{
		this.markerprofileId = markerprofileId;
	}

	public String getGermplasmId()
	{
		return germplasmId;
	}

	public void setGermplasmId(String germplasmId)
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

	@Override
	public String toString()
	{
		return "MarkerProfile{" +
			"markerprofileId=" + markerprofileId +
			", germplasmId=" + germplasmId +
			", extractId=" + extractId +
			", analysisMethod='" + analysisMethod + '\'' +
			", encoding='" + encoding + '\'' +
			'}';
	}
}
