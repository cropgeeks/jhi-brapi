package jhi.brapi.api.genotyping.callsets;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CallSet
{
	private Map<String, Object> additionalInfo = new HashMap<>();
	private String callSetDbId = "";
	private String callSetName = "";
	private String created = "";
	private String sampleDbId = "";
	private String studyDbId = "";
	private String updated = "";
	private List<String> variantSetIds;

	public CallSet()
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

	public String getCreated()
	{
		return created;
	}

	public void setCreated(String created)
	{
		this.created = created;
	}

	public String getSampleDbId()
	{
		return sampleDbId;
	}

	public void setSampleDbId(String sampleDbId)
	{
		this.sampleDbId = sampleDbId;
	}

	public String getStudyDbId()
	{
		return studyDbId;
	}

	public void setStudyDbId(String studyDbId)
	{
		this.studyDbId = studyDbId;
	}

	public String getUpdated()
	{
		return updated;
	}

	public void setUpdated(String updated)
	{
		this.updated = updated;
	}

	public List<String> getVariantSetIds()
	{
		return variantSetIds;
	}

	public void setVariantSetIds(List<String> variantSetIds)
	{
		this.variantSetIds = variantSetIds;
	}
}
