package jhi.brapi.api.markerprofiles;

import com.fasterxml.jackson.annotation.*;
import jhi.brapi.api.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrapiMarkerProfilePost extends BasicPost
{
	private String markerprofileDbId;
	private String studyDbId;
	private String sampleDbId;
	private String extractDbId;

	public BrapiMarkerProfilePost()
	{
	}

	public BrapiMarkerProfilePost(String markerprofileDbId, String studyDbId, String sampleDbId, String extractDbId)
	{
		this.markerprofileDbId = markerprofileDbId;
		this.studyDbId = studyDbId;
		this.sampleDbId = sampleDbId;
		this.extractDbId = extractDbId;
	}

	public String getMarkerprofileDbId()
		{ return markerprofileDbId; }

	public void setMarkerprofileDbId(String markerprofileDbId)
		{ this.markerprofileDbId = markerprofileDbId; }

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