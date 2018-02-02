package jhi.brapi.api.studies;

import java.util.*;

public class BrapiStudiesAsTable
{
	private List<String> headerRow;
	private List<String> observationVariableDbId;
	private List<String> observationVariableName;

	private List<List<String>> data;

	public List<String> getHeaderRow()
		{ return headerRow; }

	public void setHeaderRow(List<String> headerRow)
		{ this.headerRow = headerRow; }

	public List<String> getObservationVariableDbId()
		{ return observationVariableDbId; }

	public void setObservationVariableDbId(List<String> observationVariableDbId)
		{ this.observationVariableDbId = observationVariableDbId; }

	public List<String> getObservationVariableName()
		{ return observationVariableName; }

	public void setObservationVariableName(List<String> observationVariableName)
		{ this.observationVariableName = observationVariableName; }

	public List<List<String>> getData()
		{ return data; }

	public void setData(List<List<String>> data)
		{ this.data = data; }
}