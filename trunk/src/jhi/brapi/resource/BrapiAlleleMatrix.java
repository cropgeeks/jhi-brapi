package jhi.brapi.resource;

import java.util.*;

/**
 * Created by gs40939 on 16/06/2015.
 */
public class BrapiAlleleMatrix
{
	private List<String> markerprofileIds;

	private HashMap<String, List<String>> scores;

	public List<String> getMarkerprofileIds()
	{
		return markerprofileIds;
	}

	public void setMarkerprofileIds(List<String> markerprofileIds)
	{
		this.markerprofileIds = markerprofileIds;
	}

	public HashMap<String, List<String>> getScores()
	{
		return scores;
	}

	public void setScores(HashMap<String, List<String>> scores)
	{
		this.scores = scores;
	}
}
