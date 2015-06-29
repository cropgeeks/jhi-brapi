package hutton.brapi.server;

import hutton.brapi.data.*;
import hutton.brapi.resource.*;

import com.google.inject.*;

import org.restlet.resource.*;

/**
 * Given an id, returns the markerprofile ids which relate to the germplasm indicated by id.
 */
public class GermplasmMarkerProfileServerResource extends BaseBrapiServerResource<GermplasmMarkerProfileList>
{
	@Inject
	private GermplasmDAO germplasmDAO;

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
