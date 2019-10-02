package jhi.brapi.api.core.locations;

import java.util.*;

public class Coordinates
{
	private Geometry geometry;
	private String type = "";

	public Coordinates()
	{
	}

	public Coordinates(Geometry geometry, String type)
	{
		this.geometry = geometry;
		this.type = type;
	}

	public static Coordinates fromLatLong(double latitude, double longitude)
	{
		List<Double> latLong = new ArrayList<>();
		latLong.add(latitude);
		latLong.add(longitude);

		Geometry geometry = new Geometry(latLong, "Point");

		return new Coordinates(geometry, "Feature");
	}

	public Geometry getGeometry()
		{ return geometry; }

	public void setGeometry(Geometry geometry)
		{ this.geometry = geometry; }

	public String getType()
		{ return type; }

	public void setType(String type)
		{ this.type = type; }
}
