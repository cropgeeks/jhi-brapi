package jhi.brapi.api.genotyping.genomemaps;

import jhi.brapi.api.*;

import org.restlet.resource.*;

import java.util.*;

public class ServerMarkerpositions extends BaseBrapiServerResource
{
	private MapDAO mapDAO = new MapDAO();

	private String mapDbId;
	private String linkageGroupName;
	private String markerDbId;
	private String minPosition;
	private String maxPosition;

	@Override
	public void doInit()
	{
		super.doInit();

		mapDbId = getQueryValue("mapDbId");
		linkageGroupName = getQueryValue("linkageGroupName");
		markerDbId = getQueryValue("markerDbId");
		minPosition = getQueryValue("minPositions");
		maxPosition = getQueryValue("mapDbId");
	}

	@Get("json")
	public BrapiListResource<MarkerPosition> getJson()
	{
		LinkedHashMap<String, List<String>> parameters = new LinkedHashMap<>();
		addParameter(parameters, "map_id", mapDbId);
		addParameter(parameters, "chromosome", linkageGroupName);
		addParameter(parameters, "marker_id", markerDbId);
//		addParameter(parameters, "definition_star", minPosition);
//		addParameter(parameters, null, maxPosition);

		return mapDAO.getMarkers(parameters, currentPage, pageSize);
	}
}