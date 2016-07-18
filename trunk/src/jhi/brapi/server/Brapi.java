package jhi.brapi.server;

import java.util.*;

import org.restlet.*;
import org.restlet.engine.application.*;
import org.restlet.routing.*;
import org.restlet.service.*;

/**
 * Created by gs40939 on 24/04/2015.
 */
public class Brapi extends Application
{
	static TokenManager sessions = new TokenManager();

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
		corsFilter.setAllowedOrigins(new HashSet<>(Arrays.asList("*")));
		corsFilter.setAllowedCredentials(true);
		corsFilter.setSkippingResourceForCorsOptions(false);

		router.attach("/germplasm", Germplasm.class); // FJ / GM
		router.attach("/germplasm/", Germplasm.class);
		router.attach("/germplasm/{id}", GermplasmServerResource.class);
		router.attach("/germplasm/{id}/", GermplasmServerResource.class);
		router.attach("/germplasm/{id}/mcpd", GermplasmMcpd.class); // GM
		router.attach("/germplasm/{id}/mcpd/", GermplasmMcpd.class);
		router.attach("/germplasm/{id}/markerprofiles", GermplasmMarkerProfiles.class);
		router.attach("/germplasm/{id}/markerprofiles/", GermplasmMarkerProfiles.class);
		router.attach("/germplasm/{id}/pedigree", GermplasmPedigreeServerResource.class);
		router.attach("/germplasm/{id}/pedigree/", GermplasmPedigreeServerResource.class);
		router.attach("/maps", GenomeMaps.class); // FJ
		router.attach("/maps/", GenomeMaps.class);
		router.attach("/maps/{id}", GenomeMapMetaData.class);
		router.attach("/maps/{id}/", GenomeMapMetaData.class);
		router.attach("/maps/{id}/positions", GenomeMapMarkerData.class); // FJ
		router.attach("/maps/{id}/positions/", GenomeMapMarkerData.class);
		router.attach("/markerprofiles", MarkerProfiles.class); // FJ
		router.attach("/markerprofiles/", MarkerProfiles.class);
		router.attach("/markerprofiles/methods", MarkerProfileMethods.class); // FJ
		router.attach("/markerprofiles/methods/", MarkerProfileMethods.class);
//		router.attach("/markerprofiles/count", MarkerProfileCountServerResource.class);
//		router.attach("/markerprofiles/count/", MarkerProfileCountServerResource.class);
		router.attach("/markerprofiles/{id}", MarkerProfileServerResource.class);
		router.attach("/markerprofiles/{id}/", MarkerProfileServerResource.class);
//		router.attach("/markerprofiles/{id}/count", MarkerProfileCountServerResource.class);
//		router.attach("/markerprofiles/{id}/count/", MarkerProfileCountServerResource.class);
		router.attach("/traits", TraitListServerResource.class);
		router.attach("/traits/", TraitListServerResource.class);
		router.attach("/traits/{id}", TraitServerResource.class);
		router.attach("/traits/{id}/", TraitServerResource.class);
		router.attach("/allelematrix", AlleleMatrix.class); // FJ
		router.attach("/allelematrix/", AlleleMatrix.class);
		router.attach("/token", TokenAuthenticator.class);
		router.attach("/token/", TokenAuthenticator.class);
		router.attach("/locations", Locations.class);
		router.attach("/locations/", Locations.class);
		router.attach("/studies", Studies.class);
		router.attach("/studies/", Studies.class);
		router.attach("/studies/{id}", StudyDetails.class);
		router.attach("/studies/{id}/", StudyDetails.class);
		router.attach("/studies/{id}/table", StudiesAsTable.class);
		router.attach("/studies/{id}/table/", StudiesAsTable.class);

//		router.attach("/authorize", AuthorizationServerResource.class);
//		router.attach(HttpOAuthHelper.getAuthPage(getContext()),
//			AuthPageServerResource.class);

		return corsFilter;
	}

	static TokenManager getSessions()
	{
		return sessions;
	}
}
