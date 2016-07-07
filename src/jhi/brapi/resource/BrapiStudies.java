package jhi.brapi.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author Sebastian Raubach
 */

// Exclude non-null fields from the output
//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BrapiStudies
{
	private String studyDbId;
	private String studyName;
	private List<String> years;
	private String locationDbId;
	private Object optionalInfo;

	public String getStudyDbId()
	{
		return studyDbId;
	}

	public void setStudyDbId(String studyDbId)
	{
		this.studyDbId = studyDbId;
	}

	public String getStudyName()
	{
		return studyName;
	}

	public void setStudyName(String studyName)
	{
		this.studyName = studyName;
	}

	public List<String> getYears()
	{
		return years;
	}

	public void setYears(List<String> years)
	{
		this.years = years;
	}

	public String getLocationDbId()
	{
		return locationDbId;
	}

	public void setLocationDbId(String locationDbId)
	{
		this.locationDbId = locationDbId;
	}

	public Object getOptionalInfo()
	{
		return optionalInfo;
	}

	public void setOptionalInfo(Object optionalInfo)
	{
		this.optionalInfo = optionalInfo;
	}

	@Override
	public String toString()
	{
		return "BrapiStudies{" +
			"studyDbId='" + studyDbId + '\'' +
			", studyName='" + studyName + '\'' +
			", years=" + years +
			", locationDbId='" + locationDbId + '\'' +
			", optionalInfo=" + optionalInfo +
			'}';
	}
}