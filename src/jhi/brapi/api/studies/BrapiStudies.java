package jhi.brapi.api.studies;

import java.sql.Date;
import java.util.*;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrapiStudies
{
	private String studyDbId;
	private String name;
	private String trialDbId;
	private String trialName;
	private List<String> seasons;
	private String locationDbId;
	private String locationName;
	private String programDbId;
	private String programName;
	private Date startDate;
	private Date endDate;
	private String studyType;
	private String active;
	private Object additionalInfo;

	public String getStudyDbId()
		{ return studyDbId; }

	public void setStudyDbId(String studyDbId)
		{ this.studyDbId = studyDbId; }

	public String getName()
		{ return name; }

	public void setName(String name)
		{ this.name = name; }

	public String getStudyType()
		{ return studyType; }

	public void setStudyType(String studyType)
		{ this.studyType = studyType; }

	public List<String> getSeasons()
		{ return seasons; }

	public void setSeasons(List<String> seasons)
		{ this.seasons = seasons; }

	public String getTrialDbId()
		{ return trialDbId; }

	public void setTrialDbId(String trialDbId)
		{ this.trialDbId = trialDbId; }

	public String getTrialName()
		{ return trialName; }

	public void setTrialName(String trialName)
		{ this.trialName = trialName; }

	public Date getStartDate()
		{ return startDate; }

	public void setStartDate(Date startDate)
		{ this.startDate = startDate; }

	public Date getEndDate()
		{ return endDate; }

	public void setEndDate(Date endDate)
		{ this.endDate = endDate; }

	public String isActive()
		{ return active; }

	public void setActive(String active)
		{ this.active = active; }

	public Object getAdditionalInfo()
		{ return additionalInfo; }

	public void setAdditionalInfo(Object additionalInfo)
		{ this.additionalInfo = additionalInfo; }

	public String getLocationDbId()
		{ return locationDbId; }

	public void setLocationDbId(String locationDbId)
		{ this.locationDbId = locationDbId; }

	public String getLocationName()
		{ return locationName; }

	public void setLocationName(String locationName)
		{ this.locationName = locationName; }

	public String getProgramDbId()
		{ return programDbId; }

	public void setProgramDbId(String programDbId)
		{ this.programDbId = programDbId; }

	public String getProgramName()
		{ return programName; }

	public void setProgramName(String programName)
		{ this.programName = programName; }
}