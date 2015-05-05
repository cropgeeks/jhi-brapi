package uk.ac.hutton.brapi.resource;

import java.sql.Date;
import java.util.*;

/**
 * Created by gs40939 on 28/04/2015.
 */
public class Map
{
	private int id;
	private String name;
	private String species;
	private String type;
	private String unit;
	private Date date;
	private int markerCount;
	private int chromosomeCount;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
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

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public int getMarkerCount()
	{
		return markerCount;
	}

	public void setMarkerCount(int markerCount)
	{
		this.markerCount = markerCount;
	}

	public int getChromosomeCount()
	{
		return chromosomeCount;
	}

	public void setChromosomeCount(int chromosomeCount)
	{
		this.chromosomeCount = chromosomeCount;
	}

	public List<String> getComments()
	{
		return comments;
	}

	public void setComments(List<String> comments)
	{
		this.comments = comments;
	}

	private List<String> comments;

	@Override
	public String toString()
	{
		return "Map{" +
			"id=" + id +
			", name='" + name + '\'' +
			", species='" + species + '\'' +
			", type='" + type + '\'' +
			", unit='" + unit + '\'' +
			", date=" + date +
			", markerCount=" + markerCount +
			", chromosomeCount=" + chromosomeCount +
			", comments=" + comments +
			'}';
	}
}
