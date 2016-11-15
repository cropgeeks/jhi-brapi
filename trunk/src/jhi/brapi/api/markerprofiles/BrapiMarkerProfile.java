package jhi.brapi.api.markerprofiles;

public class BrapiMarkerProfile
{
	private String markerProfileDbId;
	private String germplasmDbId;
	private String uniqueDisplayName;
	private String extractDbId;
	private String analysisMethod;
	private int resultCount;
	private String sampleDbId;

	public String getMarkerProfileDbId()
		{ return markerProfileDbId; }

	public void setMarkerProfileDbId(String markerProfileDbId)
		{ this.markerProfileDbId = markerProfileDbId; }

	public String getGermplasmDbId()
		{ return germplasmDbId; }

	public void setGermplasmDbId(String germplasmDbId)
		{ this.germplasmDbId = germplasmDbId; }

	public String getUniqueDisplayName()
		{ return uniqueDisplayName; }

	public void setUniqueDisplayName(String uniqueDisplayName)
		{ this.uniqueDisplayName = uniqueDisplayName; }

	public String getExtractDbId()
		{ return extractDbId; }

	public void setExtractDbId(String extractDbId)
		{ this.extractDbId = extractDbId; }

	public String getAnalysisMethod()
		{ return analysisMethod; }

	public void setAnalysisMethod(String analysisMethod)
		{ this.analysisMethod = analysisMethod; }

	public int getResultCount()
		{ return resultCount; }

	public void setResultCount(int resultCount)
		{ this.resultCount = resultCount; }

	public String getSampleDbId()
		{ return sampleDbId; }

	public void setSampleDbId(String sampleDbId)
		{ this.sampleDbId = sampleDbId; }

	@Override
	public String toString()
	{
		return "MarkerProfile{" +
			"markerprofileDbId=" + markerProfileDbId +
			", germplasmDbId=" + germplasmDbId +
			", extractDbId=" + extractDbId +
			", analysisMethod='" + analysisMethod + '\'' +
			", resultCount='" + resultCount + '\'' +
			'}';
	}
}