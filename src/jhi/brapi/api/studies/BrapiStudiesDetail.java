package jhi.brapi.api.studies;

import java.sql.Date;
import java.util.*;

import jhi.brapi.api.Seasons.*;
import jhi.brapi.api.locations.*;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrapiStudiesDetail
{
	private boolean active;
	private Map<String, Object> additionalInfo;
	private List<BrapiContact> contacts;
	private List<BrapiDataLinks> dataLinks;
	private String documentationURL;
	private Date endDate;
	private BrapiLastUpdate lastUpdate;
	private String license;
	private BrapiLocation location;
	private List<String> seasons;
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

	public List<BrapiContact> getContacts()
		{ return contacts; }

	public void setContacts(List<BrapiContact> contacts)
		{ this.contacts = contacts; }

	public List<BrapiDataLinks> getDataLinks()
		{ return dataLinks; }

	public void setDataLinks(List<BrapiDataLinks> dataLinks)
		{ this.dataLinks = dataLinks; }

	public String getDocumentationURL()
		{ return documentationURL; }

	public void setDocumentationURL(String documentationURL)
		{ this.documentationURL = documentationURL; }

	public Date getEndDate()
		{ return endDate; }

	public void setEndDate(Date endDate)
		{ this.endDate = endDate; }

	public BrapiLastUpdate getLastUpdate()
		{ return lastUpdate; }

	public void setLastUpdate(BrapiLastUpdate lastUpdate)
		{ this.lastUpdate = lastUpdate; }

	public String getLicense()
		{ return license; }

	public void setLicense(String license)
		{ this.license = license; }

	public BrapiLocation getLocation()
		{ return location; }

	public void setLocation(BrapiLocation location)
		{ this.location = location; }

	public List<String> getSeasons()
		{ return seasons; }

	public void setSeasons(List<String> seasons)
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