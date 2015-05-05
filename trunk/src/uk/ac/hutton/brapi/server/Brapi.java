package uk.ac.hutton.brapi.server;

import org.restlet.*;
import org.restlet.routing.*;

/**
 * Created by gs40939 on 24/04/2015.
 */
public class Brapi extends Application
{
	public static void main(String[] args) throws Exception
	{
//		Component server = new Component();
//		server.getServers().add(Protocol.HTTP, 8111);
//		server.getDefaultHost().attach(new Brapi());
//		server.start();
	}

	public Brapi()
	{
		setName("Germinate 3 API");
		setDescription("Test plant breeding API implementation");
		setOwner("The James Hutton Institute");
		setAuthor("Information & Computational Sciences, JHI");
	}

	@Override
	public Restlet createInboundRoot()
	{
		Router router = new Router(getContext());

		router.attach("/", HelloServerResource.class);
		router.attach("/germplasm/{id}", uk.ac.hutton.brapi.server.GermplasmServerResource.class);
		router.attach("/germplasm/{id}/", uk.ac.hutton.brapi.server.GermplasmServerResource.class);
		router.attach("/germplasm/{id}/markerprofiles", GermplasmMarkerProfileServerResource.class);
		router.attach("/germplasm/{id}/markerprofiles/", GermplasmMarkerProfileServerResource.class);
		router.attach("/maps/", MapsServerResource.class);
		router.attach("/maps/{id}", MapServerResource.class);
		router.attach("/maps/{id}/", MapServerResource.class);

		return router;
	}
}
