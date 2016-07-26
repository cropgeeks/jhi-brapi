package jhi.brapi.resource;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by gs40939 on 16/06/2015.
 */
public class BrapiStudiesAsTable
{
	private String studyDbId;
	private List<String> observationVariableDbId;
	private List<String> observationVariableName;

	private List<List<String>> data;

	public String getStudyDbId()
	{
		return studyDbId;
	}

	public void setStudyDbId(String studyDbId)
	{
		this.studyDbId = studyDbId;
	}

	public List<String> getObservationVariableDbId()
	{
		return observationVariableDbId;
	}

	public void setObservationVariableDbId(List<String> observationVariableDbId)
	{
		this.observationVariableDbId = observationVariableDbId;
	}

	public List<String> getObservationVariableName()
	{
		return observationVariableName;
	}

	public void setObservationVariableName(List<String> observationVariableName)
	{
		this.observationVariableName = observationVariableName;
	}

	public List<List<String>> getData()
	{
		return data;
	}

	public void setData(List<List<String>> data)
	{
		this.data = data;
	}
}
