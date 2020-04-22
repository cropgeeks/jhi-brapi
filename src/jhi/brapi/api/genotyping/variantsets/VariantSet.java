package jhi.brapi.api.genotyping.variantsets;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VariantSet
{
	private Map<String, Object> additionalInfo = new HashMap<>();
	private List<Analysis> analysis = new ArrayList<>();
	private List<Format> availableFormats = new ArrayList<>();
	private int callSetCount;
	private String referenceSetDbId = "";
	private String studyDbId = "";
	private int variantCount;
	private String variantSetDbId = "";
	private String variantSetName = "";

	public VariantSet()
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

	public List<Analysis> getAnalysis()
	{
		return analysis;
	}

	public void setAnalysis(List<Analysis> analysis)
	{
		this.analysis = analysis;
	}

	public List<Format> getAvailableFormats()
	{
		return availableFormats;
	}

	public void setAvailableFormats(List<Format> availableFormats)
	{
		this.availableFormats = availableFormats;
	}

	public int getCallSetCount()
	{
		return callSetCount;
	}

	public void setCallSetCount(int callSetCount)
	{
		this.callSetCount = callSetCount;
	}

	public String getReferenceSetDbId()
	{
		return referenceSetDbId;
	}

	public void setReferenceSetDbId(String referenceSetDbId)
	{
		this.referenceSetDbId = referenceSetDbId;
	}

	public String getStudyDbId()
	{
		return studyDbId;
	}

	public void setStudyDbId(String studyDbId)
	{
		this.studyDbId = studyDbId;
	}

	public int getVariantCount()
	{
		return variantCount;
	}

	public void setVariantCount(int variantCount)
	{
		this.variantCount = variantCount;
	}

	public String getVariantSetDbId()
	{
		return variantSetDbId;
	}

	public void setVariantSetDbId(String variantSetDbId)
	{
		this.variantSetDbId = variantSetDbId;
	}

	public String getVariantSetName()
	{
		return variantSetName;
	}

	public void setVariantSetName(String variantSetName)
	{
		this.variantSetName = variantSetName;
	}
}
