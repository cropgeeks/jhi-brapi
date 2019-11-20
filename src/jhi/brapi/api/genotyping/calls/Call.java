package jhi.brapi.api.genotyping.calls;

import java.util.*;

public class Call
{
	private Map<String, Object> additionalInfo = new HashMap<>();
	private String callSetDbId = "";
	private String callSetName = "";
	private List<Object> genotype = new ArrayList<>();
	private List<Double> genotype_likelihood = new ArrayList<>();
	private String phaseset = "";
	private String variantDbId = "";
	private String variantName = "";

	public Call()
	{
	}

	public Map<String, Object> getAdditionalInfo()
	{
		return additionalInfo;
	}

	public void setAdditionalInfo(Map<String, Object> additionalInfo)
	{
		this.additionalInfo = additionalInfo;
	}

	public String getCallSetDbId()
	{
		return callSetDbId;
	}

	public void setCallSetDbId(String callSetDbId)
	{
		this.callSetDbId = callSetDbId;
	}

	public String getCallSetName()
	{
		return callSetName;
	}

	public void setCallSetName(String callSetName)
	{
		this.callSetName = callSetName;
	}

	public List<Object> getGenotype()
	{
		return genotype;
	}

	public void setGenotype(List<Object> genotype)
	{
		this.genotype = genotype;
	}

	public List<Double> getGenotype_likelihood()
	{
		return genotype_likelihood;
	}

	public void setGenotype_likelihood(List<Double> genotype_likelihood)
	{
		this.genotype_likelihood = genotype_likelihood;
	}

	public String getPhaseset()
	{
		return phaseset;
	}

	public void setPhaseset(String phaseset)
	{
		this.phaseset = phaseset;
	}

	public String getVariantDbId()
	{
		return variantDbId;
	}

	public void setVariantDbId(String variantDbId)
	{
		this.variantDbId = variantDbId;
	}

	public String getVariantName()
	{
		return variantName;
	}

	public void setVariantName(String variantName)
	{
		this.variantName = variantName;
	}
}
