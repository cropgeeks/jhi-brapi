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

	private int id;

	@Override
	public void doInit()
	{
		super.doInit();
		this.id = Integer.parseInt(getRequestAttributes().get("id").toString());
	}

	@Get("json")
	public BasicResource<BrapiGermplasmMarkerProfiles> getJson()
	{
		List<BrapiGermplasmMarkerProfiles> list = germplasmDAO.getMarkerProfilesFor(id);

		return new BasicResource<BrapiGermplasmMarkerProfiles>(list);
	}
}
