package jhi.brapi.resource;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

/**
 * @author Sebastian Raubach
 */

// Exclude non-null fields from the output
//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BrapiStudies
{
	private String       studyDbId;
	private String       name;
	private List<String> seasons;
	private String       locationDbId;
	private Object       optionalInfo;

	public String getStudyDbId()
	{
		return studyDbId;
	}

	public void setStudyDbId(String studyDbId)
	{
		this.studyDbId = studyDbId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<String> getSeasons()
	{
		return seasons;
	}

	public void setSeasons(List<String> seasons)
	{
		this.seasons = seasons;
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
			", name='" + name + '\'' +
			", seasons=" + seasons +
			", locationDbId='" + locationDbId + '\'' +
			", optionalInfo=" + optionalInfo +
			'}';
	}
}