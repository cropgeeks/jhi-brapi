package jhi.brapi.resource;

/**
 * Created by gs40939 on 17/06/2015.
 */
public class BrapiMarkerPosition
{
	private String markerDbId;
	private String markerName;
	private String location;
	private String linkageGroup;

	public String getMarkerDbId()
	{
		return markerDbId;
	}

	public void setMarkerDbId(String markerDbId)
	{
		this.markerDbId = markerDbId;
	}

	public String getMarkerName()
	{
		return markerName;
	}

	public void setMarkerName(String markerName)
	{
		this.markerName = markerName;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getLinkageGroup()
	{
		return linkageGroup;
	}

	public void setLinkageGroup(String linkageGroup)
	{
		this.linkageGroup = linkageGroup;
	}
}
