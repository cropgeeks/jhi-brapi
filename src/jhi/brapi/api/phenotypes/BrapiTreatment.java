package jhi.brapi.api.phenotypes;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrapiTreatment
{
	private String factor;
	private String modality;

	public String getFactor()
	{
		return factor;
	}

	public BrapiTreatment setFactor(String factor)
	{
		this.factor = factor;
		return this;
	}

	public String getModality()
	{
		return modality;
	}

	public BrapiTreatment setModality(String modality)
	{
		this.modality = modality;
		return this;
	}
}
