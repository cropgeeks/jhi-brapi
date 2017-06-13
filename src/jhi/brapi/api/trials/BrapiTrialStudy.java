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

	public BrapiTrialStudy setStudyDbId(String studyDbId)
	{
		this.studyDbId = studyDbId;
		return this;
	}

	public String getStudyName()
	{
		return studyName;
	}

	public BrapiTrialStudy setStudyName(String studyName)
	{
		this.studyName = studyName;
		return this;
	}

	public String getLocationName()
	{
		return locationName;
	}

	public BrapiTrialStudy setLocationName(String locationName)
	{
		this.locationName = locationName;
		return this;
	}
}
