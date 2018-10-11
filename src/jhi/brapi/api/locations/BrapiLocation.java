package jhi.brapi.api.locations;

public class BrapiLocation
{
	private String locationDbId;
	private String locationType;
	private String name;
	private String abbreviation;
	private String countryCode;
	private String countryName;
	private String latitude;
	private String longitude;
	private String altitude;
	private String instituteName;
	private String instituteAddress;
	private Object additionalInfo = null;

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

	public String getLatitude()
		{ return latitude; }

	public void setLatitude(String latitude)
		{ this.latitude = latitude; }

	public String getLongitude()
		{ return longitude; }

	public void setLongitude(String longitude)
		{ this.longitude = longitude; }

	public String getAltitude()
		{ return altitude; }

	public void setAltitude(String altitude)
		{ this.altitude = altitude; }

	public String getInstituteName()
		{ return instituteName; }

	public void setInstituteName(String instituteName)
		{ this.instituteName = instituteName; }

	public String getInstituteAddress()
		{ return instituteAddress; }

	public void setInstituteAddress(String instituteAddress)
		{ this.instituteAddress = instituteAddress; }

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