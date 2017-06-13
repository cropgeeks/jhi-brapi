package jhi.brapi.api.phenotypes;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

import jhi.brapi.api.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrapiPhenotypePost extends BasicPost
{
	private List<String> germplasmDbIds;
	private List<String> observationVariableDbIds;
	private List<String> studyDbIds;
	private List<String> locationDbIds;
	private List<String> programDbIds;
	private List<String> seasonDbIds;
	private String observationLevel;

	public List<String> getGermplasmDbIds()
	{
		return germplasmDbIds;
	}

	public BrapiPhenotypePost setGermplasmDbIds(List<String> germplasmDbIds)
	{
		this.germplasmDbIds = germplasmDbIds;
		return this;
	}

	public List<String> getObservationVariableDbIds()
	{
		return observationVariableDbIds;
	}

	public BrapiPhenotypePost setObservationVariableDbIds(List<String> observationVariableDbIds)
	{
		this.observationVariableDbIds = observationVariableDbIds;
		return this;
	}

	public List<String> getStudyDbIds()
	{
		return studyDbIds;
	}

	public BrapiPhenotypePost setStudyDbIds(List<String> studyDbIds)
	{
		this.studyDbIds = studyDbIds;
		return this;
	}

	public List<String> getLocationDbIds()
	{
		return locationDbIds;
	}

	public BrapiPhenotypePost setLocationDbIds(List<String> locationDbIds)
	{
		this.locationDbIds = locationDbIds;
		return this;
	}

	public List<String> getProgramDbIds()
	{
		return programDbIds;
	}

	public BrapiPhenotypePost setProgramDbIds(List<String> programDbIds)
	{
		this.programDbIds = programDbIds;
		return this;
	}

	public List<String> getSeasonDbIds()
	{
		return seasonDbIds;
	}

	public BrapiPhenotypePost setSeasonDbIds(List<String> seasonDbIds)
	{
		this.seasonDbIds = seasonDbIds;
		return this;
	}

	public String getObservationLevel()
	{
		return observationLevel;
	}

	public BrapiPhenotypePost setObservationLevel(String observationLevel)
	{
		this.observationLevel = observationLevel;
		return this;
	}
}