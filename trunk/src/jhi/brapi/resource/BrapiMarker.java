package jhi.brapi.resource;

/**
 * Created by gs40939 on 17/06/2015.
 */
public class BrapiMarker
{
	private String markerId;
	private String markerName;
	private String location;
	private String linkageGroup;

	public String getMarkerId()
	{
		return markerId;
	}

	public void setMarkerId(String markerId)
	{
		this.markerId = markerId;
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
