package jhi.brapi;

import java.util.*;

import jhi.brapi.api.*;
import jhi.brapi.api.allelematrices.ServerAlleleMatrixDataset;
import jhi.brapi.api.calls.*;
import jhi.brapi.api.crops.*;
import jhi.brapi.api.files.*;
import jhi.brapi.api.genomemaps.*;
import jhi.brapi.api.germplasm.*;
import jhi.brapi.api.locations.*;
import jhi.brapi.api.markerprofiles.*;
import jhi.brapi.api.markers.*;
import jhi.brapi.api.authentication.*;
import jhi.brapi.api.studies.*;
import jhi.brapi.api.phenotypes.*;
import jhi.brapi.api.trials.*;
import jhi.brapi.util.*;

import org.restlet.*;
import org.restlet.data.*;
import org.restlet.engine.application.*;
import org.restlet.resource.*;
import org.restlet.routing.*;
import org.restlet.security.*;
import org.restlet.service.*;

import static org.restlet.data.ChallengeScheme.HTTP_OAUTH_BEARER;

public class Brapi extends Application
{
	static TokenManager sessions = new TokenManager();

	public Brapi()
	{
		setName("Germinate 3 API");
		setDescription("Test plant breeding API (BRAPI) implementation");
		setOwner("The James Hutton Institute");
		setAuthor("Information & Computational Sciences, JHI");

		CorsService corsService = new CorsService();
		corsService.setAllowedOrigins(new HashSet(Arrays.asList("*")));
		corsService.setAllowedCredentials(true);
		getServices().add(corsService);
	}

	@Override
	public Restlet createInboundRoot()
	{
		// Get restlet context
		Context context = getContext();

		// These routes are unaunthenticated as we need to be able to determine
		// the available calls and access the login URI without requiring
		// authentication
		Router unauthenticated = new Router(context);
		attachToRouter(unauthenticated, "/calls", ServerCalls.class);  // FJ
		attachToRouter(unauthenticated, "/token", ServerTokenAuthenticator.class);

		Router appRoutes = unauthenticated;
		boolean authenticate = Boolean.parseBoolean(context.getParameters().getFirstValue("auth-routes"));

		// If we are using authentication setup a new router which can has
		// authentication utilities added and is added as a sub-router of our
		// unauthenticated router
		if (authenticate)
		{
			appRoutes = new Router(context);
			// Sets up the token checking mechanism which automatically checks
			// authentication tokens for any request which is trying to access a URL
			// protected by the ChallengeAuthenticator
			ChallengeAuthenticator guard = new ChallengeAuthenticator(context, HTTP_OAUTH_BEARER, "JHI-BrAPI");
			TokenBasedVerifier verifier = new TokenBasedVerifier();
			guard.setVerifier(verifier);
			guard.setRechallenging(false);
			guard.setNext(appRoutes);

			unauthenticated.attachDefault(guard);
		}

		setupAppRoutes(appRoutes);
		setupNotImplementedRoutes(appRoutes);

		Filter encoder = new Encoder(context, false, true, new EncoderService(true));
		encoder.setNext(unauthenticated);

		CorsFilter corsFilter = new CorsFilter(context, encoder);
		corsFilter.setAllowedOrigins(new HashSet<>(Collections.singletonList("*")));
		corsFilter.setAllowedCredentials(true);
		corsFilter.setSkippingResourceForCorsOptions(true);

		return corsFilter;
	}

	private void setupAppRoutes(Router router)
	{
		// These routes require an authentication token to access (you can get
		// this by providing a username and password to the /token resource
		attachToRouter(router, "/allelematrices-search", ServerAlleleMatrix.class); // FJ
		attachToRouter(router, "/allelematrices-search/{id}", ServerStatus.class);
		attachToRouter(router, "/allelematrices-search/status/{id}", ServerStatus.class);
		attachToRouter(router, "/allelematrices", ServerAlleleMatrixDataset.class);
		attachToRouter(router, "/commoncropnames", ServerCrop.class);
		attachToRouter(router, "/files/{filename}", Files.class); // NON-BrAPI
		attachToRouter(router, "/germplasm/{id}", ServerGermplasm.class);
		attachToRouter(router, "/germplasm/{id}/markerprofiles", ServerGermplasmMarkerProfiles.class);
		attachToRouter(router, "/germplasm/{id}/pedigree", ServerGermplasmPedigree.class);
		attachToRouter(router, "/germplasm-search", ServerGermplasmSearch.class);
		attachToRouter(router, "/locations", ServerLocations.class);
		attachToRouter(router, "/maps", ServerGenomeMaps.class); // FJ
		attachToRouter(router, "/maps/{id}", ServerGenomeMapMetaData.class); // FJ
		attachToRouter(router, "/maps/{id}/positions", ServerGenomeMapMarkerData.class); // FJ
		attachToRouter(router, "/maps/{id}/positions/{linkageGroupId}", ServerGenomeMapLinkageGroupMarkers.class);
		attachToRouter(router, "/markerprofiles", ServerMarkerProfiles.class); // FJ
		attachToRouter(router, "/markerprofiles/{id}", ServerMarkerProfileData.class);
		attachToRouter(router, "/markers", ServerMarkersSearch.class);
		attachToRouter(router, "/markers/{id}", ServerMarkersData.class);
		attachToRouter(router, "/phenotypes-search", ServerPhenotypesSearch.class);
		attachToRouter(router, "/studies", ServerStudies.class);
		attachToRouter(router, "/studies/{id}", ServerStudyDetails.class);
//		attachToRouter(router, "/studies/{id}/table", ServerStudiesAsTable.class);
		attachToRouter(router, "/trials", ServerTrialList.class);
		attachToRouter(router, "/trials/{id}", ServerTrial.class);
	}

	private void setupNotImplementedRoutes(Router router)
	{
		List<String> notImplemented = new ArrayList<String>();
		notImplemented.add("/attributes/categories");
		notImplemented.add("/germplasm/{id}/attributes");
		notImplemented.add("/attributes?attributeCategoryDbId={attributeCategoryDbId}");
		notImplemented.add("/locations/{id}");
		notImplemented.add("/variables");
		notImplemented.add("/variables/{id}");
		notImplemented.add("/variables/datatypes");
		notImplemented.add("/variables-search");
		notImplemented.add("/ontologies");
		notImplemented.add("/phenotypes");
		notImplemented.add("/programs");
		notImplemented.add("/programs-search");
		notImplemented.add("/samples");
		notImplemented.add("/samples/{id}");
		notImplemented.add("/studies/{id}/observations");
		notImplemented.add("/studies/{id}/observationsunits");
		notImplemented.add("/studies/{id}/observationvariables");
		notImplemented.add("/studies/{id}/layout");
		notImplemented.add("/studies/{id}/germplasm");
		notImplemented.add("/studies/{id}/table");
		notImplemented.add("/observationsLevels");
		notImplemented.add("/seasons");
		notImplemented.add("/studyTypes");

		notImplemented.forEach(url ->
		{
			attachToRouter(router, url, ServerNotImplemented.class);
		});
	}

	private void attachToRouter(Router router, String url, Class<? extends ServerResource> clazz)
	{
		router.attach(url, clazz);
		router.attach(url + "/", clazz);
	}

	public static TokenManager getSessions()
	{
		return sessions;
	}
}