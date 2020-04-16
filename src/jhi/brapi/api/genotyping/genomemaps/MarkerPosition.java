package jhi.brapi.api.genotyping.genomemaps;

import java.util.*;

public class MarkerPosition
{
	private Map<String, Object> additionalInfo = new HashMap<>();
	private String linkageGroupName = "";
	private String mapDbId = "";
	private String mapName = "";
	private String variantDbId;
	private String variantName;
	private String position;

	public MarkerPosition()
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

	public String getLinkageGroupName()
	{
		return linkageGroupName;
	}

	public void setLinkageGroupName(String linkageGroupName)
	{
		this.linkageGroupName = linkageGroupName;
	}

	public String getMapDbId()
	{
		return mapDbId;
	}

	public void setMapDbId(String mapDbId)
	{
		this.mapDbId = mapDbId;
	}

	public String getMapName()
	{
		return mapName;
	}

	public void setMapName(String mapName)
	{
		this.mapName = mapName;
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

	public String getPosition()
	{
		return position;
	}

	public void setPosition(String position)
	{
		this.position = position;
	}
}