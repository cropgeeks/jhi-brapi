package jhi.brapi.server;

import java.util.List;
import jhi.brapi.data.*;
import jhi.brapi.resource.*;

import org.restlet.resource.*;

/**
 * Given an id, returns the markerprofile ids which relate to the germplasm indicated by id.
 */
public class GermplasmMarkerProfiles extends BaseBrapiServerResource
{
	private GermplasmDAO germplasmDAO = new GermplasmDAO();

	private String id;

	@Override
	public void doInit()
	{
		super.doInit();
		this.id = getRequestAttributes().get("id").toString();
	}

	@Get("json")
	public BasicResource<BrapiGermplasmMarkerProfiles> getJson()
	{
		return germplasmDAO.getMarkerProfilesFor(id, currentPage, pageSize);
	}
}
