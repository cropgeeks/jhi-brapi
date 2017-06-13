package jhi.brapi.api.trials;

import jhi.brapi.api.studies.*;

import java.util.*;

public class BrapiTrial
{
	private String trialDbId;
	private String trialName;
	private String programDbId;
	private String programName;
	private Date startDate;
	private Date endDate;
	private String active;
	private List<BrapiTrialStudy> studies;
	private Map<String, String> additionalInfo;

	public String getTrialDbId()
	{
		return trialDbId;
	}

	public BrapiTrial setTrialDbId(String trialDbId)
	{
		this.trialDbId = trialDbId;
		return this;
	}

	public String getTrialName()
	{
		return trialName;
	}

	public BrapiTrial setTrialName(String trialName)
	{
		this.trialName = trialName;
		return this;
	}

	public String getProgramDbId()
	{
		return programDbId;
	}

	public BrapiTrial setProgramDbId(String programDbId)
	{
		this.programDbId = programDbId;
		return this;
	}

	public String getProgramName()
	{
		return programName;
	}

	public BrapiTrial setProgramName(String programName)
	{
		this.programName = programName;
		return this;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public BrapiTrial setStartDate(Date startDate)
	{
		this.startDate = startDate;
		return this;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public BrapiTrial setEndDate(Date endDate)
	{
		this.endDate = endDate;
		return this;
	}

	public String getActive()
	{
		return active;
	}

	public BrapiTrial setActive(String active)
	{
		this.active = active;
		return this;
	}

	public List<BrapiTrialStudy> getStudies()
	{
		return studies;
	}

	public BrapiTrial setStudies(List<BrapiTrialStudy> studies)
	{
		this.studies = studies;
		return this;
	}

	public Map<String, String> getAdditionalInfo()
	{
		return additionalInfo;
	}

	public BrapiTrial setAdditionalInfo(Map<String, String> additionalInfo)
	{
		this.additionalInfo = additionalInfo;
		return this;
	}
}
