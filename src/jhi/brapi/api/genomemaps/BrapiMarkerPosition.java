package jhi.brapi.api.genomemaps;

public class BrapiMarkerPosition
{
	private String markerDbId;
	private String markerName;
	private String location;
	private String linkageGroupName;

	public String getMarkerDbId()
		{ return markerDbId; }

	public void setMarkerDbId(String markerDbId)
		{ this.markerDbId = markerDbId; }

	public String getMarkerName()
		{ return markerName; }

	public void setMarkerName(String markerName)
		{ this.markerName = markerName; }

	public String getLocation()
		{ return location; }

	public void setLocation(String location)
		{ this.location = location; }

	public String getLinkageGroupName()
		{ return linkageGroupName; }

	public void setLinkageGroupName(String linkageGroupName)
		{ this.linkageGroupName = linkageGroupName; }
}