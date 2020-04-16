package jhi.brapi.api.genotyping.variantsets;

import java.util.*;

public class Analysis
{
	private String analysisDbId = "";
	private String analysisName = "";
	private String created = "";
	private String description = "";
	private List<String> software = new ArrayList<>();
	private String type = "";
	private String updated = "";

	public Analysis()
	{
	}

	public String getAnalysisDbId()
	{
		return analysisDbId;
	}

	public void setAnalysisDbId(String analysisDbId)
	{
		this.analysisDbId = analysisDbId;
	}

	public String getAnalysisName()
	{
		return analysisName;
	}

	public void setAnalysisName(String analysisName)
	{
		this.analysisName = analysisName;
	}

	public String getCreated()
	{
		return created;
	}

	public void setCreated(String created)
	{
		this.created = created;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public List<String> getSoftware()
	{
		return software;
	}

	public void setSoftware(List<String> software)
	{
		this.software = software;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getUpdated()
	{
		return updated;
	}

	public void setUpdated(String updated)
	{
		this.updated = updated;
	}
}
