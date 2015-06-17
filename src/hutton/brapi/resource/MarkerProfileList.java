package hutton.brapi.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;

/**
 * Created by gs40939 on 07/05/2015.
 */
public class MarkerProfileList
{
	private List<MarkerProfile> markerprofiles;

	public MarkerProfileList()
	{
	}

	// JsonCreator annotation specifies the method used by Jackson to deserialize from JSON to Java.
	@JsonCreator
	public MarkerProfileList(List<MarkerProfile> markerprofiles)
	{
		this.markerprofiles = markerprofiles;
	}

	// JsonValue annotation specifies that the value of markerprofiles should be used (i.e. it won't be wrapped in a Germplasm JSON object)
	@JsonValue
	public List<MarkerProfile> getMarkerprofiles()
	{
		return markerprofiles;
	}

	public void setMarkerprofiles(List<MarkerProfile> markerprofiles)
	{
		this.markerprofiles = markerprofiles;
	}
}
