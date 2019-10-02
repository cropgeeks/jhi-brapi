package jhi.brapi.api.core.studies;

public class ExperimentalDesign
{
	private String description = "";
	private String PUI = "";

	public ExperimentalDesign()
	{
	}

	public ExperimentalDesign(String description, String PUI)
	{
		this.description = description;
		this.PUI = PUI;
	}

	public String getDescription()
		{ return description; }

	public void setDescription(String description)
		{ this.description = description; }

	public String getPUI()
		{ return PUI; }

	public void setPUI(String PUI)
		{ this.PUI = PUI; }
}
