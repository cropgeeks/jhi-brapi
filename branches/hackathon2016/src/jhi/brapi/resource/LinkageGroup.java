package jhi.brapi.resource;

/**
 * Created by gs40939 on 17/06/2015.
 */
public class LinkageGroup
{
	private String linkageGroupId;
	private int numberMarkers;
	private long maxPosition;

	public String getLinkageGroupId()
	{
		return linkageGroupId;
	}

	public void setLinkageGroupId(String linkageGroupId)
	{
		this.linkageGroupId = linkageGroupId;
	}

	public int getNumberMarkers()
	{
		return numberMarkers;
	}

	public void setNumberMarkers(int numberMarkers)
	{
		this.numberMarkers = numberMarkers;
	}

	public long getMaxPosition()
	{
		return maxPosition;
	}

	public void setMaxPosition(long maxPosition)
	{
		this.maxPosition = maxPosition;
	}
}
