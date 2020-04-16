package jhi.brapi.api.genotyping.genomemaps;

import java.util.*;

public class GenomeMap
{
	private Map<String, Object> additionalInfo;
	private String comments = "";
	private String documentationURL = "";
	private int linkageGroupCount;
	private String mapDbId = "";
	private String mapName = "";
	private int markerCount;
	private String publishedDate;
	private String scientificName = "";
	private String type = "";
	private String unit = "";

	public GenomeMap()
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

	public String getComments()
	{
		return comments;
	}

	public void setComments(String comments)
	{
		this.comments = comments;
	}

	public String getDocumentationURL()
	{
		return documentationURL;
	}

	public void setDocumentationURL(String documentationURL)
	{
		this.documentationURL = documentationURL;
	}

	public int getLinkageGroupCount()
	{
		return linkageGroupCount;
	}

	public void setLinkageGroupCount(int linkageGroupCount)
	{
		this.linkageGroupCount = linkageGroupCount;
	}

	public String getMapDbId()
	{
		return mapDbId;
	}

	public void setMapDbId(String mapDbId)
	{
		this.mapDbId = mapDbId;
	}

	public String getMapName()
	{
		return mapName;
	}

	public void setMapName(String mapName)
	{
		this.mapName = mapName;
	}

	public int getMarkerCount()
	{
		return markerCount;
	}

	public void setMarkerCount(int markerCount)
	{
		this.markerCount = markerCount;
	}

	public String getPublishedDate()
	{
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate)
	{
		this.publishedDate = publishedDate;
	}

	public String getScientificName()
	{
		return scientificName;
	}

	public void setScientificName(String scientificName)
	{
		this.scientificName = scientificName;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getUnit()
	{
		return unit;
	}

	public void setUnit(String unit)
	{
		this.unit = unit;
	}
}