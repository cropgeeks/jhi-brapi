package jhi.brapi.resource;

import java.util.*;

/**
 * Created by gs40939 on 16/06/2015.
 */
public class BrapiAlleleMatrix
{
	private List<String> markerprofileDbIds;

	private List<LinkedHashMap<String, List<String>>> data;

	public List<String> getMarkerprofileDbIds()
	{
		return markerprofileDbIds;
	}

	public void setMarkerprofileDbIds(List<String> markerprofileDbIds)
	{
		this.markerprofileDbIds = markerprofileDbIds;
	}

	public List<LinkedHashMap<String, List<String>>> getData()
	{
		return data;
	}

	public void setData(List<LinkedHashMap<String, List<String>>> data)
	{
		this.data = data;
	}
}
