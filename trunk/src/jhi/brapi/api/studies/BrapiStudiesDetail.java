package jhi.brapi.api.studies;

import java.sql.Date;
import java.util.*;

import jhi.brapi.api.locations.*;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrapiStudiesDetail
{
	private String studyDbId;
	private String studyName;
	private String studyType;
	private List<String> seasons;
	private String trialDbId;
	private String trialName;
	private Date startDate;
	private Date endDate;
	private boolean active;
	private BrapiLocation location;
	private List<BrapiContact> contacts;
	private Object additionalInfo;

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

	public BrapiLocation getLocation()
		{ return location; }

	public void setLocation(BrapiLocation location)
		{ this.location = location; }

	public List<BrapiContact> getContacts()
		{ return contacts; }

	public void setContacts(List<BrapiContact> contacts)
		{ this.contacts = contacts; }

	public Object getAdditionalInfo()
		{ return additionalInfo; }

	public void setAdditionalInfo(Object additionalInfo)
		{ this.additionalInfo = additionalInfo; }

	public boolean isActive()
		{ return active; }

	public void setActive(boolean active)
		{ this.active = active; }
}