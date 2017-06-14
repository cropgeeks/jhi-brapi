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

	public void setFactor(String factor)
	{
		this.factor = factor;
	}

	public String getModality()
	{
		return modality;
	}

	public void setModality(String modality)
	{
		this.modality = modality;
	}
}
