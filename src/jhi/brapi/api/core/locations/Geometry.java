package jhi.brapi.api.core.locations;

import java.util.*;

public class Geometry
{
	private List<Double> coordinates;
	private String type = "";

	public Geometry()
	{
	}

	public Geometry(List<Double> coordinates, String type)
	{
		this.coordinates = coordinates;
		this.type = type;
	}

	public List<Double> getCoordinates()
		{ return coordinates; }

	public void setCoordinates(List<Double> coordinates)
		{ this.coordinates = coordinates; }

	public String getType()
		{ return type; }

	public void setType(String type)
		{ this.type = type; }
}
