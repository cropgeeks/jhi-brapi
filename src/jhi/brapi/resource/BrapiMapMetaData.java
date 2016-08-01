package jhi.brapi.resource;

import java.util.*;

/**
 * Created by gs40939 on 28/04/2015.
 */
public class BrapiMapMetaData
{
	private String             mapDbId;
	private String             name;
	private String             type;
	private String             unit;
	private List<LinkageGroup> linkageGroups;

	public String getMapDbId()
	{
		return mapDbId;
	}

	public void setMapDbId(String mapDbId)
	{
		this.mapDbId = mapDbId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getUnit()
	{
		return unit;
	}

	public void setUnit(String unit)
	{
		this.unit = unit;
	}

	public List<LinkageGroup> getLinkageGroups()
	{
		return linkageGroups;
	}

	public void setLinkageGroups(List<LinkageGroup> linkageGroups)
	{
		this.linkageGroups = linkageGroups;
	}

	@Override
	public String toString()
	{
		return "MapDetail{" +
			"name='" + name + '\'' +
			", type='" + type + '\'' +
			", unit='" + unit + '\'' +
			'}';
	}
}
