package hutton.brapi.server;

import java.util.*;

import hutton.brapi.data.*;

import com.google.inject.Guice;

import org.restlet.*;
import org.restlet.engine.application.*;
import org.restlet.ext.guice.*;
import org.restlet.ext.swagger.*;
import org.restlet.routing.*;
import org.restlet.service.*;

/**
 * Created by gs40939 on 24/04/2015.
 */
public class Brapi extends SwaggerApplication
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
		Router router = new Router(getContext());

		Filter encoder = new Encoder(getContext(), false, true, new EncoderService(true));
		encoder.setNext(router);
		CorsFilter corsFilter = new CorsFilter(getContext(), encoder);
		corsFilter.setAllowedOrigins(new HashSet<String>(Arrays.asList("*")));
		corsFilter.setAllowedCredentials(true);
		corsFilter.setSkippingResourceForCorsOptions(false);

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
		router.attach("/maps/{id}/positions", MapMarkersListServerResource.class);
		router.attach("/maps/{id}/positions/", MapMarkersListServerResource.class);
		router.attach("/markerprofiles", MarkerProfileListServerResource.class);
		router.attach("/markerprofiles/", MarkerProfileListServerResource.class);
		router.attach("/markerprofiles/methods", MarkerProfileMethodsListServerResource.class);
		router.attach("/markerprofiles/methods/", MarkerProfileMethodsListServerResource.class);
		router.attach("/markerprofiles/count", MarkerProfileCountServerResource.class);
		router.attach("/markerprofiles/count/", MarkerProfileCountServerResource.class);
		router.attach("/markerprofiles/{id}", MarkerProfileServerResource.class);
		router.attach("/markerprofiles/{id}/", MarkerProfileServerResource.class);
		router.attach("/markerprofiles/{id}/count", MarkerProfileCountServerResource.class);
		router.attach("/markerprofiles/{id}/count/", MarkerProfileCountServerResource.class);
		router.attach("/traits", TraitListServerResource.class);
		router.attach("/traits/", TraitListServerResource.class);
		router.attach("/traits/{id}", TraitServerResource.class);
		router.attach("/traits/{id}/", TraitServerResource.class);
		router.attach("/allelematrix", AlleleMatrixServerResource.class);
		router.attach("/allelematrix/", AlleleMatrixServerResource.class);

//		router.attach("/authorize", AuthorizationServerResource.class);
//		router.attach(HttpOAuthHelper.getAuthPage(getContext()),
//			AuthPageServerResource.class);

		CustomSwaggerSpecificationRestlet customSwaggerSpec = new CustomSwaggerSpecificationRestlet(getContext());
		customSwaggerSpec.setApiInboundRoot(this);
		attachSwaggerDocumentationRestlets(router, "/api-docs", customSwaggerSpec, "/api-docs/{resource}", customSwaggerSpec);

		return corsFilter;
	}
}
