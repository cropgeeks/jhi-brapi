package jhi.brapi.api.trials;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrapiTrialStudy
{
	private String studyDbId;
	private String studyName;
	private String locationName;

	public String getStudyDbId()
	{
		return studyDbId;
	}

	public void setStudyDbId(String studyDbId)
	{
		this.studyDbId = studyDbId;
	}

	public String getStudyName()
	{
		return studyName;
	}

	public void setStudyName(String studyName)
	{
		this.studyName = studyName;
	}

	public String getLocationName()
	{
		return locationName;
	}

	public void setLocationName(String locationName)
	{
		this.locationName = locationName;
	}
}
