package jhi.brapi.api.markers;

import jhi.brapi.api.*;

import org.restlet.resource.*;

/**
 * Queries the database to get the data on a given marker (as specified by the
 * id which is part of the request). Returns data on a single marker.
 */
public class ServerMarkersData extends BaseBrapiServerResource
{
	private MarkerDAO markerDAO = new MarkerDAO();

	private String id;

	@Override
	public void doInit()
	{
		super.doInit();

		this.id = (String)getRequestAttributes().get("id");
	}

	@Get("json")
	public BrapiBaseResource<BrapiMarker> getJson()
	{
		return markerDAO.getById(id);
	}
}