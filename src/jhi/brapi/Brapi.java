package jhi.brapi;

import java.util.*;

import jhi.brapi.api.calls.*;
import jhi.brapi.api.files.*;
import jhi.brapi.api.genomemaps.*;
import jhi.brapi.api.germplasm.*;
import jhi.brapi.api.locations.*;
import jhi.brapi.api.markerprofiles.*;
import jhi.brapi.api.markers.*;
import jhi.brapi.api.authentication.*;
import jhi.brapi.api.studies.*;
import jhi.brapi.api.traits.*;
import jhi.brapi.util.*;

import org.restlet.*;
import org.restlet.engine.application.*;
import org.restlet.resource.*;
import org.restlet.routing.*;
import org.restlet.service.*;

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
		corsFilter.setAllowedOrigins(new HashSet<>(Collections.singletonList("*")));
		corsFilter.setAllowedCredentials(true);
		corsFilter.setSkippingResourceForCorsOptions(false);

		attachToRouter(router, "/germplasm", ServerGermplasmSearch.class); // GM
		attachToRouter(router, "/germplasm/mcpd", ServerGermplasmMcpdSearch.class);
		attachToRouter(router, "/germplasm/{id}", ServerGermplasm.class);
		attachToRouter(router, "/germplasm/{id}/mcpd", ServerGermplasmMcpd.class); // GM
		attachToRouter(router, "/germplasm/{id}/markerprofiles", ServerGermplasmMarkerProfiles.class);
		attachToRouter(router, "/germplasm/{id}/pedigree", ServerGermplasmPedigree.class);
		attachToRouter(router, "/maps", ServerGenomeMaps.class); // FJ
		attachToRouter(router, "/maps/{id}", ServerGenomeMapMetaData.class); // FJ
		attachToRouter(router, "/maps/{id}/positions", ServerGenomeMapMarkerData.class); // FJ
		attachToRouter(router, "/markerprofiles", ServerMarkerProfiles.class); // FJ
		attachToRouter(router, "/markerprofiles/{id}", ServerMarkerProfileData.class);
		attachToRouter(router, "/markers", ServerMarkers.class);
//		attachToRouter(router, "/traits", TraitListServerResource.class);
//		attachToRouter(router, "/traits/{id}", TraitServerResource.class);
		attachToRouter(router, "/allelematrix-search", ServerAlleleMatrix.class); // FJ
		attachToRouter(router, "/token", ServerTokenAuthenticator.class);
		attachToRouter(router, "/locations", ServerLocations.class);
		attachToRouter(router, "/studies-search", ServerStudies.class);  // FJ
		attachToRouter(router, "/studies/{id}", ServerStudyDetails.class);
		attachToRouter(router, "/studies/{id}/table", ServerStudiesAsTable.class);
		attachToRouter(router, "/files/{filename}", Files.class); // NON-BrAPI
		attachToRouter(router, "/calls", ServerCalls.class);  // FJ

//		attachToRouter(router, "/authorize", AuthorizationServerResource.class);
//		attachToRouter(router, HttpOAuthHelper.getAuthPage(getContext()), AuthPageServerResource.class);

		return corsFilter;
	}

	private static void attachToRouter(Router router, String url, Class<? extends ServerResource> clazz)
	{
		router.attach(url, clazz);
		router.attach(url + "/", clazz);
	}

	public static TokenManager getSessions()
	{
		return sessions;
	}
}