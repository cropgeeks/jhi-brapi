package jhi.brapi.api.genotyping.genomemaps;

import java.util.*;

public class LinkageGroup
{
	private Map<String, Object> additionalInfo = new HashMap<>();
	private String linkageGroupName = "";
	private int markerCount;
	private double maxPosition;

	public LinkageGroup()
	{
	}

	public Map<String, Object> getAdditionalInfo()
	{
		return additionalInfo;
	}

	public void setAdditionalInfo(Map<String, Object> additionalInfo)
	{
		this.additionalInfo = additionalInfo;
	}

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