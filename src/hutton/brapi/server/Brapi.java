package hutton.brapi.server;

import hutton.brapi.data.*;

import com.google.inject.Guice;

import org.restlet.*;
import org.restlet.engine.application.CorsFilter;
import org.restlet.engine.application.Encoder;
import org.restlet.ext.guice.*;
import org.restlet.ext.json.*;
import org.restlet.routing.*;
import org.restlet.service.EncoderService;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by gs40939 on 24/04/2015.
 */
public class Brapi extends Application
{
	public Brapi()
	{
		setName("Germinate 3 API");
		setDescription("Test plant breeding API (BRAPI) implementation");
		setOwner("The James Hutton Institute");
		setAuthor("Information & Computational Sciences, JHI");
	}

	@Override
	public Restlet createInboundRoot()
	{
		Filter encoder = new Encoder(getContext(), false, true, new EncoderService(true));
		JsonpFilter filter = new JsonpFilter(getContext());
		CorsFilter corsFilter = new CorsFilter(getContext());
		corsFilter.setAllowedOrigins(new HashSet(Arrays.asList("*")));
		corsFilter.setAllowedCredentials(true);

		Router router = new Router(getContext());

		Guice.createInjector(new DAOModule(), new SelfInjectingServerResourceModule());

		router.attach("/germplasm", GermplasmListServerResource.class);
		router.attach("/germplasm/", GermplasmListServerResource.class);
		router.attach("/germplasm/{id}", GermplasmServerResource.class);
		router.attach("/germplasm/{id}/", GermplasmServerResource.class);
		router.attach("/germplasm/{id}/markerprofiles", GermplasmMarkerProfileServerResource.class);
		router.attach("/germplasm/{id}/markerprofiles/", GermplasmMarkerProfileServerResource.class);
		router.attach("/maps", MapListServerResource.class);
		router.attach("/maps/", MapListServerResource.class);
		router.attach("/maps/{id}", MapServerResource.class);
		router.attach("/maps/{id}/", MapServerResource.class);
		router.attach("/markerprofiles/{id}", MarkerProfileServerResource.class);
		router.attach("/markerprofiles/{id}/", MarkerProfileServerResource.class);
		router.attach("/markerprofiles/{id}/count", MarkerProfileCountServerResource.class);
		router.attach("/markerprofiles/{id}/count/", MarkerProfileCountServerResource.class);
		router.attach("/traits", TraitListServerResource.class);
		router.attach("/traits/", TraitListServerResource.class);
		router.attach("/traits/{id}", TraitServerResource.class);
		router.attach("/traits/{id}/", TraitServerResource.class);

		filter.setNext(router);
		encoder.setNext(filter);
		corsFilter.setNext(encoder);

		return corsFilter;
	}
}
