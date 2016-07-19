package jhi.brapi.server;

import java.util.*;

import org.restlet.*;
import org.restlet.engine.application.*;
import org.restlet.resource.*;
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

		attachToRouter(router, "/germplasm", Germplasm.class); // FJ / GM
		attachToRouter(router, "/germplasm/{id}", GermplasmServerResource.class);
		attachToRouter(router, "/germplasm/{id}/mcpd", GermplasmMcpd.class); // GM
		attachToRouter(router, "/germplasm/{id}/markerprofiles", GermplasmMarkerProfiles.class);
		attachToRouter(router, "/germplasm/{id}/pedigree", GermplasmPedigreeServerResource.class);
		attachToRouter(router, "/germplasm/mcpd", GermplasmMcpdSearch.class); // TODO: Find out why this won't work!!! /germplasm/{id}/mcpd2 works fine
		attachToRouter(router, "/maps", GenomeMaps.class); // FJ
		attachToRouter(router, "/maps/{id}", GenomeMapMetaData.class);
		attachToRouter(router, "/maps/{id}/positions", GenomeMapMarkerData.class); // FJ
		attachToRouter(router, "/markerprofiles", MarkerProfiles.class); // FJ
		attachToRouter(router, "/markerprofiles/methods", MarkerProfileMethods.class); // FJ
//		attachToRouter(router, "/markerprofiles/count", MarkerProfileCountServerResource.class);
		attachToRouter(router, "/markerprofiles/{id}", MarkerProfileServerResource.class);
//		attachToRouter(router, "/markerprofiles/{id}/count", MarkerProfileCountServerResource.class);
		attachToRouter(router, "/markers", Markers.class);
		attachToRouter(router, "/traits", TraitListServerResource.class);
		attachToRouter(router, "/traits/{id}", TraitServerResource.class);
		attachToRouter(router, "/allelematrix", AlleleMatrix.class); // FJ
		attachToRouter(router, "/token", TokenAuthenticator.class);
		attachToRouter(router, "/locations", Locations.class);
		attachToRouter(router, "/studies", Studies.class);
		attachToRouter(router, "/studies/{id}", StudyDetails.class);
		attachToRouter(router, "/studies/{id}/table", StudiesAsTable.class);

//		attachToRouter(router, "/authorize", AuthorizationServerResource.class);
//		attachToRouter(router, HttpOAuthHelper.getAuthPage(getContext()), AuthPageServerResource.class);

		return corsFilter;
	}

	private static void attachToRouter(Router router, String url, Class<? extends ServerResource> clazz)
	{
		router.attach(url, clazz);
		router.attach(url + "/", clazz);
	}

	static TokenManager getSessions()
	{
		return sessions;
	}
}
