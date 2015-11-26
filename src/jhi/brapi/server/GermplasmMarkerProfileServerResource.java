package jhi.brapi.server;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

import org.restlet.resource.*;

/**
 * Given an id, returns the markerprofile ids which relate to the germplasm indicated by id.
 */
public class GermplasmMarkerProfileServerResource extends BaseBrapiServerResource<GermplasmMarkerProfileList>
{
	private GermplasmDAO germplasmDAO = new GermplasmDAO();

	private int id;

	@Override
	public void doInit()
	{
		super.doInit();
		this.id = Integer.parseInt(getRequestAttributes().get("id").toString());
	}

	@Get("json")
	public GermplasmMarkerProfileList getJson()
	{
		GermplasmMarkerProfileList profileList = germplasmDAO.getMarkerProfilesFor(id);

		return profileList;
	}
}
