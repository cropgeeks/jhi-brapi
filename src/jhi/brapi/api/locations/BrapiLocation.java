package jhi.brapi.api.locations;

import java.util.*;

public class BrapiLocation
{
	private String locationDbId;
	private String locationType;
	private String name;
	private String abbreviation;
	private String countryCode;
	private String countryName;
	private double latitude;
	private double longitude;
	private int altitude;
	private String instituteName;
	private String instituteAddress;
	private Map<String, Object> additionalInfo = new HashMap<>();

	public String getLocationDbId()
		{ return locationDbId; }

	public void setLocationDbId(String locationDbId)
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

	public int getAltitude()
		{ return altitude; }

	public void setAltitude(int altitude)
		{ this.altitude = altitude; }

	public String getInstituteName()
		{ return instituteName; }

	public void setInstituteName(String instituteName)
		{ this.instituteName = instituteName; }

	public String getInstituteAddress()
		{ return instituteAddress; }

	public void setInstituteAddress(String instituteAddress)
		{ this.instituteAddress = instituteAddress; }

	public Map<String, Object> getAdditionalInfo()
		{ return additionalInfo; }

	public void setAdditionalInfo(Map<String, Object> additionalInfo)
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