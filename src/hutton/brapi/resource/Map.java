package hutton.brapi.resource;

import java.sql.*;

/**
 * Created by gs40939 on 28/04/2015.
 */
public class Map
{
	private int mapId;
	private String name;
	private String species;
	private String type;
	private String unit;
	private Date publishedDate;
	private int markerCount;
	private int linkageGroupCount;
	private String comments;

	public int getMapId()
	{
		return mapId;
	}

	public void setMapId(int mapId)
	{
		this.mapId = mapId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getSpecies()
	{
		return species;
	}

	public void setSpecies(String species)
	{
		this.species = species;
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

	public Date getPublishedDate()
	{
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate)
	{
		this.publishedDate = publishedDate;
	}

	public int getMarkerCount()
	{
		return markerCount;
	}

	public void setMarkerCount(int markerCount)
	{
		this.markerCount = markerCount;
	}

	public int getLinkageGroupCount()
	{
		return linkageGroupCount;
	}

	public void setLinkageGroupCount(int linkageGroupCount)
	{
		this.linkageGroupCount = linkageGroupCount;
	}

	public String getComments()
	{
		return comments;
	}

	public void setComments(String comments)
	{
		this.comments = comments;
	}

	@Override
	public String toString()
	{
		return "Map{" +
			"mapId=" + mapId +
			", name='" + name + '\'' +
			", species='" + species + '\'' +
			", type='" + type + '\'' +
			", unit='" + unit + '\'' +
			", publishedDate=" + publishedDate +
			", markerCount=" + markerCount +
			", linkageGroupCount=" + linkageGroupCount +
			", comments=" + comments +
			'}';
	}
}
