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

	@Get
	public Representation retrieve()
	{
		GermplasmMarkerProfileList profileList = germplasmDAO.getMarkerProfilesFor(id);

		JacksonRepresentation<GermplasmMarkerProfileList> rep = new JacksonRepresentation<>(profileList);
		rep.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

		return rep;
	}

	@Put
	public void store(Representation germplasm)
	{
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
