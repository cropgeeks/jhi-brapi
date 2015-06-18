package hutton.brapi.resource;

/**
 * Created by gs40939 on 17/06/2015.
 */
public class LinkageGroup
{
	private String linkageGroupId;
	private int numberMarkers;
	private int maxPosition;

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

	public int getMaxPosition()
	{
		return maxPosition;
	}

	public void setMaxPosition(int maxPosition)
	{
		this.maxPosition = maxPosition;
	}
}
