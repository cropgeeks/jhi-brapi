package jhi.brapi.api.traits;

import java.util.*;

public class BrapiTrait
{
	private String traitDbId;
	private String traitId;
	private String name;
	private String description;
	private String defaultValue;
	private List<String> observationVariables;

	public String getTraitDbId()
		{ return traitDbId; }

	public void setTraitDbId(String traitDbId)
		{ this.traitDbId = traitDbId; }

	public String getName()
		{ return name; }

	public void setName(String name)
		{ this.name = name; }

	public String getDefaultValue()
		{ return defaultValue; }

	public void setDefaultValue(String defaultValue)
		{ this.defaultValue = defaultValue; }

	public String getTraitId()
		{ return traitId; }

	public void setTraitId(String traitId)
		{ this.traitId = traitId; }

	public String getDescription()
		{ return description; }

	public void setDescription(String description)
		{ this.description = description; }

	public List<String> getObservationVariables()
		{ return observationVariables; }

	public void setObservationVariables(List<String> observationVariables)
		{ this.observationVariables = observationVariables; }
}