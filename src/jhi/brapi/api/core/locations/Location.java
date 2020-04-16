package jhi.brapi.api.core.locations;

import java.util.*;

public class Location
{
	private String locationDbId = "";
	private String abbreviation = "";
	private Map<String, Object> additionalInfo = new HashMap<>();
	private int altitude;
	private String coordinateDescription = "";
	private Coordinates coordinates;
	private String countryCode = "";
	private String countryName = "";
	private String documentationURL = "";
	private String environmentType = "";
	private String exposure = "";
	private String instituteAddress = "";
	private String instituteName = "";
	private String locationName = "";
	private String locationType = "";
	private String siteStatus = "";
	private String slope = "";
	private String topography = "";

	public Location()
	{
	}

	public Location(int altitude, Coordinates coordinates, String countryCode, String countryName, String locationName, String locationType)
	{
		this.altitude = altitude;
		this.coordinates = coordinates;
		this.countryCode = countryCode;
		this.countryName = countryName;
		this.locationName = locationName;
		this.locationType = locationType;
	}

	public String getLocationDbId()
		{ return locationDbId; }

	public void setLocationDbId(String locationDbId)
		{ this.locationDbId = locationDbId; }

	public String getAbbreviation()
		{ return abbreviation; }

	public void setAbbreviation(String abbreviation)
		{ this.abbreviation = abbreviation; }

	public Map<String, Object> getAdditionalInfo()
		{ return additionalInfo; }

	public void setAdditionalInfo(Map<String, Object> additionalInfo)
		{ this.additionalInfo = additionalInfo; }

	public int getAltitude()
		{ return altitude; }

	public void setAltitude(int altitude)
		{ this.altitude = altitude; }

	public String getCoordinateDescription()
		{ return coordinateDescription; }

	public void setCoordinateDescription(String coordinateDescription)
		{ this.coordinateDescription = coordinateDescription; }

	public Coordinates getCoordinates()
		{ return coordinates; }

	public void setCoordinates(Coordinates coordinates)
		{ this.coordinates = coordinates; }

	public String getCountryCode()
		{ return countryCode; }

	public void setCountryCode(String countryCode)
		{ this.countryCode = countryCode; }

	public String getCountryName()
		{ return countryName; }

	public void setCountryName(String countryName)
		{ this.countryName = countryName; }

	public String getDocumentationURL()
		{ return documentationURL; }

	public void setDocumentationURL(String documentationURL)
		{ this.documentationURL = documentationURL; }

	public String getEnvironmentType()
		{ return environmentType; }

	public void setEnvironmentType(String environmentType)
		{ this.environmentType = environmentType; }

	public String getExposure()
		{ return exposure; }

	public void setExposure(String exposure)
		{ this.exposure = exposure;
	}

	public String getInstituteAddress()
		{ return instituteAddress; }

	public void setInstituteAddress(String instituteAddress)
		{ this.instituteAddress = instituteAddress; }

	public String getInstituteName()
		{ return instituteName; }

	public void setInstituteName(String instituteName)
		{ this.instituteName = instituteName; }

	public String getLocationName()
		{ return locationName; }

	public void setLocationName(String locationName)
		{ this.locationName = locationName; }

	public String getLocationType()
		{ return locationType; }

	public void setLocationType(String locationType)
		{ this.locationType = locationType; }

	public String getSiteStatus()
		{ return siteStatus; }

	public void setSiteStatus(String siteStatus)
		{ this.siteStatus = siteStatus; }

	public String getSlope()
		{ return slope; }

	public void setSlope(String slope)
		{ this.slope = slope; }

	public String getTopography()
		{ return topography; }

	public void setTopography(String topography)
		{ this.topography = topography; }
}