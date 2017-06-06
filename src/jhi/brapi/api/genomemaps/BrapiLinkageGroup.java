package jhi.brapi.api.genomemaps;

public class BrapiLinkageGroup
{
	private String linkageGroupId;
	private int numberMarkers;
	private double maxPosition;

	public String getLinkageGroupId()
		{ return linkageGroupId; }

	public void setLinkageGroupId(String linkageGroupId)
		{ this.linkageGroupId = linkageGroupId; }

	public int getNumberMarkers()
		{ return numberMarkers; }

	public void setNumberMarkers(int numberMarkers)
		{ this.numberMarkers = numberMarkers; }

	public double getMaxPosition()
		{ return maxPosition; }

	public void setMaxPosition(double maxPosition)
		{ this.maxPosition = maxPosition; }
}