package hutton.brapi.server;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.Inject;
import hutton.brapi.data.MapDAO;
import hutton.brapi.resource.MapList;
import hutton.brapi.resource.MapMarkersList;
import org.restlet.ext.guice.SelfInjectingServerResource;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;

/**
 * Created by gs40939 on 17/06/2015.
 */
public class MapMarkersListServerResource extends SelfInjectingServerResource
{
	@Inject
	private MapDAO mapDAO;

	// The ID from the URI (need to get this in overridden doInit method)
	private String id;

	@Override
	public void doInit()
	{
		super.doInit();
		this.id = (String)getRequestAttributes().get("id");
	}

	@Get("json")
	public MapMarkersList getJson()
	{
		MapMarkersList mapList = mapDAO.getByIdMarkers(Integer.parseInt(id));

		return mapList;
	}

	@Get("html")
	public Representation getHtml()
	{
		MapMarkersList mapList = mapDAO.getByIdMarkers(Integer.parseInt(id));

		if (mapList != null)
		{
			JacksonRepresentation<MapMarkersList> rep = new JacksonRepresentation<>(mapList);
			rep.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
			return rep;
		}

		throw new ResourceException(404);
	}
}
