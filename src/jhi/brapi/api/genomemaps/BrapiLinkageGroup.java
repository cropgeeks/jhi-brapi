package jhi.brapi.api.genomemaps;

public class BrapiLinkageGroup
{
	private String linkageGroupName;
	private int markerCount;
	private double maxPosition;

	public String getLinkageGroupName()
		{ return linkageGroupName; }

	public void setLinkageGroupName(String linkageGroupName)
		{ this.linkageGroupName = linkageGroupName; }

	public int getMarkerCount()
		{ return markerCount; }

	public void setMarkerCount(int markerCount)
		{ this.markerCount = markerCount; }

	public double getMaxPosition()
		{ return maxPosition; }

	public void setMaxPosition(double maxPosition)
		{ this.maxPosition = maxPosition; }
}