package jhi.brapi.api.phenotypes;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

import jhi.brapi.api.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrapiPhenotypesPost extends BasicPost
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

	public BrapiPhenotypesPost setGermplasmDbIds(List<String> germplasmDbIds)
	{
		this.germplasmDbIds = germplasmDbIds;
		return this;
	}

	public List<String> getObservationVariableDbIds()
	{
		return observationVariableDbIds;
	}

	public BrapiPhenotypesPost setObservationVariableDbIds(List<String> observationVariableDbIds)
	{
		this.observationVariableDbIds = observationVariableDbIds;
		return this;
	}

	public List<String> getStudyDbIds()
	{
		return studyDbIds;
	}

	public BrapiPhenotypesPost setStudyDbIds(List<String> studyDbIds)
	{
		this.studyDbIds = studyDbIds;
		return this;
	}

	public List<String> getLocationDbIds()
	{
		return locationDbIds;
	}

	public BrapiPhenotypesPost setLocationDbIds(List<String> locationDbIds)
	{
		this.locationDbIds = locationDbIds;
		return this;
	}

	public List<String> getProgramDbIds()
	{
		return programDbIds;
	}

	public BrapiPhenotypesPost setProgramDbIds(List<String> programDbIds)
	{
		this.programDbIds = programDbIds;
		return this;
	}

	public List<String> getSeasonDbIds()
	{
		return seasonDbIds;
	}

	public BrapiPhenotypesPost setSeasonDbIds(List<String> seasonDbIds)
	{
		this.seasonDbIds = seasonDbIds;
		return this;
	}

	public String getObservationLevel()
	{
		return observationLevel;
	}

	public BrapiPhenotypesPost setObservationLevel(String observationLevel)
	{
		this.observationLevel = observationLevel;
		return this;
	}
}