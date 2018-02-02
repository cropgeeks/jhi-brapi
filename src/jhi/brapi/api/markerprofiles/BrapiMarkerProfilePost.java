package jhi.brapi.api.markerprofiles;

import com.fasterxml.jackson.annotation.*;
import jhi.brapi.api.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrapiMarkerProfilePost extends BasicPost
{
	private String germplasmDbId;
	private String studyDbId;
	private String sampleDbId;
	private String extractDbId;

	public BrapiMarkerProfilePost()
	{
	}

	public BrapiMarkerProfilePost(String germplasmDbId, String studyDbId, String sampleDbId, String extractDbId)
	{
		this.germplasmDbId = germplasmDbId;
		this.studyDbId = studyDbId;
		this.sampleDbId = sampleDbId;
		this.extractDbId = extractDbId;
	}

	public String getGermplasmDbId()
		{ return germplasmDbId; }

	public void setGermplasmDbId(String germplasmDbId)
		{ this.germplasmDbId = germplasmDbId; }

	public String getStudyDbId()
		{ return studyDbId; }

	public void setStudyDbId(String studyDbId)
		{ this.studyDbId = studyDbId; }

	public String getSampleDbId()
		{ return sampleDbId; }

	public void setSampleDbId(String sampleDbId)
		{ this.sampleDbId = sampleDbId; }

	public String getExtractDbId()
		{ return extractDbId; }

	public void setExtractDbId(String extractDbId)
		{ this.extractDbId = extractDbId; }
}