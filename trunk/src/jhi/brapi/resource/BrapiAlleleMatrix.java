package jhi.brapi.resource;

import java.util.*;

/**
 * Created by gs40939 on 16/06/2015.
 */
public class BrapiAlleleMatrix
{
	private List<String> markerprofileDbIds;

	private HashMap<String, List<String>> data;

	public List<String> getMarkerprofileDbIds()
	{
		return markerprofileDbIds;
	}

	public void setMarkerprofileDbIds(List<String> markerprofileDbIds)
	{
		this.markerprofileDbIds = markerprofileDbIds;
	}

	public HashMap<String, List<String>> getData()
	{
		return data;
	}

	public void setData(HashMap<String, List<String>> data)
	{
		this.data = data;
	}
}
