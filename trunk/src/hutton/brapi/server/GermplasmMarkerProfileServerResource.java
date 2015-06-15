package hutton.brapi.server;

import hutton.brapi.data.*;
import hutton.brapi.resource.*;

import com.fasterxml.jackson.databind.*;

import com.google.inject.*;

import org.restlet.ext.guice.*;
import org.restlet.ext.jackson.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

/**
 * Given an id, returns the markerprofile ids which relate to the germplasm indicated by id.
 */
public class GermplasmMarkerProfileServerResource extends SelfInjectingServerResource
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
	public GermplasmMarkerProfileList retrieve()
	{
		GermplasmMarkerProfileList profileList = germplasmDAO.getMarkerProfilesFor(id);

		return profileList;
	}
}
