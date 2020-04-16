package jhi.brapi.api.core.studies;

public class EnvironmentParameter
{
	private String description = "";
	private String parameterName = "";
	private String parameterPUI = "";
	private String unit = "";
	private String unitPUI = "";
	private String value = "";
	private String valuePUI = "";

	public EnvironmentParameter()
	{
	}

	public EnvironmentParameter(String description, String parameterName, String parameterPUI, String unit, String unitPUI, String value, String valuePUI)
	{
		this.description = description;
		this.parameterName = parameterName;
		this.parameterPUI = parameterPUI;
		this.unit = unit;
		this.unitPUI = unitPUI;
		this.value = value;
		this.valuePUI = valuePUI;
	}

	public String getDescription()
		{ return description; }

	public void setDescription(String description)
		{ this.description = description; }

	public String getParameterName()
		{ return parameterName; }

	public void setParameterName(String parameterName)
		{ this.parameterName = parameterName; }

	public String getParameterPUI()
		{ return parameterPUI; }

	public void setParameterPUI(String parameterPUI)
		{ this.parameterPUI = parameterPUI; }

	public String getUnit()
		{ return unit; }

	public void setUnit(String unit)
		{ this.unit = unit; }

	public String getUnitPUI()
		{ return unitPUI; }

	public void setUnitPUI(String unitPUI)
		{ this.unitPUI = unitPUI; }

	public String getValue()
		{ return value; }

	public void setValue(String value)
		{ this.value = value; }

	public String getValuePUI()
		{ return valuePUI; }

	public void setValuePUI(String valuePUI)
		{ this.valuePUI = valuePUI; }
}
