package hutton.brapi.resource;

import java.util.*;

/**
 * Created by gs40939 on 28/04/2015.
 */
public class MapDetail
{
	private String name;
	private String type;
	private String unit;
	private List<MapEntry> entries;

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

	public List<MapEntry> getEntries()
	{
		return entries;
	}

	public void setEntries(List<MapEntry> entries)
	{
		this.entries = entries;
	}

	@Override
	public String toString()
	{
		return "MapDetail{" +
			"name='" + name + '\'' +
			", type='" + type + '\'' +
			", unit='" + unit + '\'' +
			", entries=" + entries +
			'}';
	}
}
