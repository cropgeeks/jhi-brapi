package jhi.brapi.api.studies;

import java.sql.Date;
import java.util.*;

import jhi.brapi.api.seasons.*;

public class BrapiStudies
{
	private boolean active;
	private Map<String, Object> additionalInfo;
	private String commonCropName;
	private String documentationURL;
	private Date endDate;
	private String locationDbId;
	private String locationName;
	private String name;
	private String programDbId;
	private String programName;
	private List<BrapiSeason> seasons;
	private Date startDate;
	private String studyDbId;
	private String studyName;
	private String studyType;
	private String studyTypeDbId;
	private String studyTypeName;
	private String trialDbId;
	private String trialName;

	public boolean isActive()
		{ return active; }

	public void setActive(boolean active)
		{ this.active = active; }

	public Map<String, Object> getAdditionalInfo()
		{ return additionalInfo; }

	public void setAdditionalInfo(Map<String, Object> additionalInfo)
		{ this.additionalInfo = additionalInfo; }

	public String getCommonCropName()
		{ return commonCropName; }

	public void setCommonCropName(String commonCropName)
		{ this.commonCropName = commonCropName; }

	public String getDocumentationURL()
		{ return documentationURL; }

	public void setDocumentationURL(String documentationURL)
		{ this.documentationURL = documentationURL; }

	public Date getEndDate()
		{ return endDate; }

	public void setEndDate(Date endDate)
		{ this.endDate = endDate; }

	public String getLocationDbId()
		{ return locationDbId; }

	public void setLocationDbId(String locationDbId)
		{ this.locationDbId = locationDbId; }

	public String getLocationName()
		{ return locationName; }

	public void setLocationName(String locationName)
		{ this.locationName = locationName; }

	public String getName()
		{ return name; }

	public void setName(String name)
		{ this.name = name; }

	public String getProgramDbId()
		{ return programDbId; }

	public void setProgramDbId(String programDbId)
		{ this.programDbId = programDbId; }

	public String getProgramName()
		{ return programName; }

	public void setProgramName(String programName)
		{ this.programName = programName; }

	public List<BrapiSeason> getSeasons()
		{ return seasons; }

	public void setSeasons(List<BrapiSeason> seasons)
		{ this.seasons = seasons; }

	public Date getStartDate()
		{ return startDate; }

	public void setStartDate(Date startDate)
		{ this.startDate = startDate; }

	public String getStudyDbId()
		{ return studyDbId; }

	public void setStudyDbId(String studyDbId)
		{ this.studyDbId = studyDbId; }

	public String getStudyName()
		{ return studyName; }

	public void setStudyName(String studyName)
		{ this.studyName = studyName; }

	public String getStudyType()
		{ return studyType; }

	public void setStudyType(String studyType)
		{ this.studyType = studyType; }

	public String getStudyTypeDbId()
		{ return studyTypeDbId; }

	public void setStudyTypeDbId(String studyTypeDbId)
		{ this.studyTypeDbId = studyTypeDbId; }

	public String getStudyTypeName()
		{ return studyTypeName; }

	public void setStudyTypeName(String studyTypeName)
		{ this.studyTypeName = studyTypeName; }

	public String getTrialDbId()
		{ return trialDbId; }

	public void setTrialDbId(String trialDbId)
		{ this.trialDbId = trialDbId; }

	public String getTrialName()
		{ return trialName; }

	public void setTrialName(String trialName)
		{ this.trialName = trialName; }
}