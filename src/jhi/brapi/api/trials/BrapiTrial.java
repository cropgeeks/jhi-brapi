package jhi.brapi.api.trials;

import java.time.*;
import java.util.*;

public class BrapiTrial
{
	private String trialDbId;
	private String trialName;
	private String programDbId;
	private String programName;
	private String startDate;
	private String endDate;
	private String active;
	private List<BrapiTrialStudy> studies;
	private Map<String, String> additionalInfo;

	public String getTrialDbId()
	{
		return trialDbId;
	}

	public void setTrialDbId(String trialDbId)
	{
		this.trialDbId = trialDbId;
	}

	public String getTrialName()
	{
		return trialName;
	}

	public void setTrialName(String trialName)
	{
		this.trialName = trialName;
	}

	public String getProgramDbId()
	{
		return programDbId;
	}

	public void setProgramDbId(String programDbId)
	{
		this.programDbId = programDbId;
	}

	public String getProgramName()
	{
		return programName;
	}

	public void setProgramName(String programName)
	{
		this.programName = programName;
	}

	public String getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = Instant.ofEpochMilli(startDate.getTime()).toString();;
	}

	public String getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = Instant.ofEpochMilli(endDate.getTime()).toString();;
	}

	public String getActive()
	{
		return active;
	}

	public void setActive(String active)
	{
		this.active = active;
	}

	public List<BrapiTrialStudy> getStudies()
	{
		return studies;
	}

	public void setStudies(List<BrapiTrialStudy> studies)
	{
		this.studies = studies;
	}

	public Map<String, String> getAdditionalInfo()
	{
		return additionalInfo;
	}

	public void setAdditionalInfo(Map<String, String> additionalInfo)
	{
		this.additionalInfo = additionalInfo;
	}
}
