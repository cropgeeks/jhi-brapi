package jhi.brapi.api.phenotypes;

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

	public void setObservationDbId(String observationDbId)
	{
		this.observationDbId = observationDbId;
	}

	public String getObservationVariableDbId()
	{
		return observationVariableDbId;
	}

	public void setObservationVariableDbId(String observationVariableDbId)
	{
		this.observationVariableDbId = observationVariableDbId;
	}

	public String getObservationVariableName()
	{
		return observationVariableName;
	}

	public void setObservationVariableName(String observationVariableName)
	{
		this.observationVariableName = observationVariableName;
	}

	public String getSeason()
	{
		return season;
	}

	public void setSeason(String season)
	{
		this.season = season;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public String getObservationTimeStamp()
	{
		return observationTimeStamp;
	}

	public void setObservationTimeStamp(String observationTimeStamp)
	{
		this.observationTimeStamp = observationTimeStamp;
	}

	public String getCollector()
	{
		return collector;
	}

	public void setCollector(String collector)
	{
		this.collector = collector;
	}
}
