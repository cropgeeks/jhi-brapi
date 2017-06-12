package jhi.brapi.api.phenotypes;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrapiObservation
{
	private String observationDbId;
	private String observationVariableDbId;
	private String observationVariableName;
	private String season;
	private String value;
	private String observationTimeStamp;
	private String collector;

	public String getObservationDbId()
	{
		return observationDbId;
	}

	public BrapiObservation setObservationDbId(String observationDbId)
	{
		this.observationDbId = observationDbId;
		return this;
	}

	public String getObservationVariableDbId()
	{
		return observationVariableDbId;
	}

	public BrapiObservation setObservationVariableDbId(String observationVariableDbId)
	{
		this.observationVariableDbId = observationVariableDbId;
		return this;
	}

	public String getObservationVariableName()
	{
		return observationVariableName;
	}

	public BrapiObservation setObservationVariableName(String observationVariableName)
	{
		this.observationVariableName = observationVariableName;
		return this;
	}

	public String getSeason()
	{
		return season;
	}

	public BrapiObservation setSeason(String season)
	{
		this.season = season;
		return this;
	}

	public String getValue()
	{
		return value;
	}

	public BrapiObservation setValue(String value)
	{
		this.value = value;
		return this;
	}

	public String getObservationTimeStamp()
	{
		return observationTimeStamp;
	}

	public BrapiObservation setObservationTimeStamp(String observationTimeStamp)
	{
		this.observationTimeStamp = observationTimeStamp;
		return this;
	}

	public String getCollector()
	{
		return collector;
	}

	public BrapiObservation setCollector(String collector)
	{
		this.collector = collector;
		return this;
	}
}
