package jhi.brapi.api.core.studies;

import jhi.brapi.api.*;

import java.util.*;

public class BrapiStudiesPost extends BasicPost
{
	private boolean active;
	private List<String> commonCropNames;
	private List<String> externalReferenceIds;
	private List<String> externalReferenceSources;
	private List<String> germplasmDbIds;
	private List<String> germplasmNames;
	private List<String> locationDbIds;
	private List<String> locationNames;
	private List<String> observationVariableDbIds;
	private List<String> programDbIds;
	private List<String> programNames;
	private List<String> seasonDbIds;
	private List<String> sortBy;
	private List<String> sortOrder;
	private List<String> studyDbIds;
	private List<String> studyNames;
	private List<String> studyPUIs;
	private List<String> studyTypes;
	private List<String> trialDbIds;
	private List<String> trialNames;

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public List<String> getCommonCropNames()
	{
		return commonCropNames;
	}

	public void setCommonCropNames(List<String> commonCropNames)
	{
		this.commonCropNames = commonCropNames;
	}

	public List<String> getExternalReferenceIds()
	{
		return externalReferenceIds;
	}

	public void setExternalReferenceIds(List<String> externalReferenceIds)
	{
		this.externalReferenceIds = externalReferenceIds;
	}

	public List<String> getExternalReferenceSources()
	{
		return externalReferenceSources;
	}

	public void setExternalReferenceSources(List<String> externalReferenceSources)
	{
		this.externalReferenceSources = externalReferenceSources;
	}

	public List<String> getGermplasmDbIds()
	{
		return germplasmDbIds;
	}

	public void setGermplasmDbIds(List<String> germplasmDbIds)
	{
		this.germplasmDbIds = germplasmDbIds;
	}

	public List<String> getGermplasmNames()
	{
		return germplasmNames;
	}

	public void setGermplasmNames(List<String> germplasmNames)
	{
		this.germplasmNames = germplasmNames;
	}

	public List<String> getLocationDbIds()
	{
		return locationDbIds;
	}

	public void setLocationDbIds(List<String> locationDbIds)
	{
		this.locationDbIds = locationDbIds;
	}

	public List<String> getLocationNames()
	{
		return locationNames;
	}

	public void setLocationNames(List<String> locationNames)
	{
		this.locationNames = locationNames;
	}

	public List<String> getObservationVariableDbIds()
	{
		return observationVariableDbIds;
	}

	public void setObservationVariableDbIds(List<String> observationVariableDbIds)
	{
		this.observationVariableDbIds = observationVariableDbIds;
	}

	public List<String> getProgramDbIds()
	{
		return programDbIds;
	}

	public void setProgramDbIds(List<String> programDbIds)
	{
		this.programDbIds = programDbIds;
	}

	public List<String> getProgramNames()
	{
		return programNames;
	}

	public void setProgramNames(List<String> programNames)
	{
		this.programNames = programNames;
	}

	public List<String> getSeasonDbIds()
	{
		return seasonDbIds;
	}

	public void setSeasonDbIds(List<String> seasonDbIds)
	{
		this.seasonDbIds = seasonDbIds;
	}

	public List<String> getSortBy()
	{
		return sortBy;
	}

	public void setSortBy(List<String> sortBy)
	{
		this.sortBy = sortBy;
	}

	public List<String> getSortOrder()
	{
		return sortOrder;
	}

	public void setSortOrder(List<String> sortOrder)
	{
		this.sortOrder = sortOrder;
	}

	public List<String> getStudyDbIds()
	{
		return studyDbIds;
	}

	public void setStudyDbIds(List<String> studyDbIds)
	{
		this.studyDbIds = studyDbIds;
	}

	public List<String> getStudyNames()
	{
		return studyNames;
	}

	public void setStudyNames(List<String> studyNames)
	{
		this.studyNames = studyNames;
	}

	public List<String> getStudyPUIs()
	{
		return studyPUIs;
	}

	public void setStudyPUIs(List<String> studyPUIs)
	{
		this.studyPUIs = studyPUIs;
	}

	public List<String> getStudyTypes()
	{
		return studyTypes;
	}

	public void setStudyTypes(List<String> studyTypes)
	{
		this.studyTypes = studyTypes;
	}

	public List<String> getTrialDbIds()
	{
		return trialDbIds;
	}

	public void setTrialDbIds(List<String> trialDbIds)
	{
		this.trialDbIds = trialDbIds;
	}

	public List<String> getTrialNames()
	{
		return trialNames;
	}

	public void setTrialNames(List<String> trialNames)
	{
		this.trialNames = trialNames;
	}
}