package jhi.brapi.resource;

/**
 * Created by gs40939 on 05/07/2016.
 */
public class BrapiLocation
{
	private int locationDbId;
	private String name;
	private String countryCode;
	private String countryName;
	private double latitude;
	private double longitude;
	private int altitude;
	private Object attributes = null;

	public int getLocationDbId()
	{
		return locationDbId;
	}

	public void setLocationDbId(int locationDbId)
	{
		this.locationDbId = locationDbId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCountryCode()
	{
		return countryCode;
	}

	public void setCountryCode(String countryCode)
	{
		this.countryCode = countryCode;
	}

	public String getCountryName()
	{
		return countryName;
	}

	public void setCountryName(String countryName)
	{
		this.countryName = countryName;
	}

	public double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}

	public double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}

	public int getAltitude()
	{
		return altitude;
	}

	public void setAltitude(int altitude)
	{
		this.altitude = altitude;
	}

	public Object getAttributes()
	{
		return attributes;
	}

	public void setAttributes(Object attributes)
	{
		this.attributes = attributes;
	}

	@Override
	public String toString()
	{
		return "BrapiLocation{" +
			"locationDbId=" + locationDbId +
			", name='" + name + '\'' +
			", countryCode='" + countryCode + '\'' +
			", countryName='" + countryName + '\'' +
			", latitude=" + latitude +
			", longitude=" + longitude +
			", altitude=" + altitude +
			", attributes=" + attributes +
			'}';
	}
}
