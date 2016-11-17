package jhi.brapi.api.locations;

public class BrapiLocation
{
	private int locationDbId;
	private String locationType;
	private String name;
	private String abbreviation;
	private String countryCode;
	private String countryName;
	private double latitude;
	private double longitude;
	private double altitude;
	private Object additionalInfo = null;

	public int getLocationDbId()
		{ return locationDbId; }

	public void setLocationDbId(int locationDbId)
		{ this.locationDbId = locationDbId; }

	public String getLocationType()
		{ return locationType; }

	public void setLocationType(String locationType)
		{ this.locationType = locationType; }

	public String getName()
		{ return name; }

	public void setName(String name)
		{ this.name = name; }

	public String getAbbreviation()
		{ return abbreviation; }

	public void setAbbreviation(String abbreviation)
		{ this.abbreviation = abbreviation; }

	public String getCountryCode()
		{ return countryCode; }

	public void setCountryCode(String countryCode)
		{ this.countryCode = countryCode; }

	public String getCountryName()
		{ return countryName; }

	public void setCountryName(String countryName)
		{ this.countryName = countryName; }

	public double getLatitude()
		{ return latitude; }

	public void setLatitude(double latitude)
		{ this.latitude = latitude; }

	public double getLongitude()
		{ return longitude; }

	public void setLongitude(double longitude)
		{ this.longitude = longitude; }

	public double getAltitude()
		{ return altitude; }

	public void setAltitude(double altitude)
		{ this.altitude = altitude; }

	public Object getAdditionalInfo()
		{ return additionalInfo; }

	public void setAdditionalInfo(Object additionalInfo)
		{ this.additionalInfo = additionalInfo; }

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
			", additionalInfo=" + additionalInfo +
			'}';
	}
}