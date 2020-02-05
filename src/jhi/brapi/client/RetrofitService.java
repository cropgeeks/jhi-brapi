package jhi.brapi.client;

import jhi.brapi.api.*;
import jhi.brapi.api.authentication.*;
import jhi.brapi.api.core.serverinfo.*;
import jhi.brapi.api.genotyping.callsets.*;
import jhi.brapi.api.genotyping.genomemaps.*;
import jhi.brapi.api.core.studies.*;

import retrofit2.*;
import retrofit2.http.*;

/**
 * A Retrofit service definition of our BrAPI client. This defines the calls
 * that can be used with our client BrAPI client library. Each method defines
 * the URL (relative) to a base URL and the HTTP method that the call will
 * use. In accordance with the Retrofit specification pass null to query
 * parameters that are optional (and for which you don't want to pass values).
 *
 * @see <a href="https://square.github.io/retrofit/">Retrofit</a>
 */
public interface RetrofitService
{
	/**
	 * Queries the BrAPI provider to discover which calls are supported by the
	 * provider. Filterable by the dataType that a call supports returning data
	 * in. Supports paging.
	 *
	 * @param dataType	The datatype of the call return. e.g. JSON, TSV
	 * @return			A Retrofit Call object which contains a
	 * 					BrapiListResource which wraps a List of BrapiCall
	 * 					objects which contain details of the calls supported by
	 * 					this BrAPI provider
	 */
	@GET("serverinfo")
	Call<BrapiBaseResource<BrapiServerInfo>> getServerInfo(@Query("dataType") String dataType);

	/**
	 * Queries the BrAPI provider to attempt to retrieve an authentication token
	 * using the provided username and password.
	 *
	 * @param tokenPost An BrapiTokenLoginPost object with appropriate user data
	 * @return			A Retrofit Call object which wraps a BrapiSeesionToken
	 * 					object to authenticate the user with the BrAPI provider
	 * 					going forward
	 */
	@POST("token")
	Call<BrapiSessionToken> getAuthToken(@Body BrapiTokenLoginPost tokenPost);

	/**
	 * Get a study, optionally filtered by the query parameters outlined below
	 *
	 * @param commonCropName
	 * @param studyType
	 * @param programDbId
	 * @param locationDbId
	 * @param seasonDbId
	 * @param trialDbId
	 * @param studyDbId
	 * @param sutdyPUI
	 * @param germplasmDbId
	 * @param observationVariableId
	 * @param active
	 * @param sortBy
	 * @param sortOrder
	 * @param externalReferenceId
	 * @param externalReferenceSource
	 * @return
	 */
	@GET("studies")
	Call<BrapiListResource<Study>> getStudies(@Query("commonCropName") String commonCropName, @Query("studyType") String studyType, @Query("programDbId") String programDbId, @Query("locationDbId") String locationDbId, @Query("seasonDbId") String seasonDbId, @Query("trialDbId") String trialDbId, @Query("studyDbId") String studyDbId, @Query("studyPUI") String sutdyPUI, @Query("germplasmDbId") String germplasmDbId, @Query("observationVariableDbId") String observationVariableId, @Query("active") String active, @Query("sortBy") String sortBy, @Query("sortOrder") String sortOrder, @Query("externalReferenceId") String externalReferenceId, @Query("externalReferenceSource") String externalReferenceSource, @Query("pageSize") Integer pageSize, @Query("page") Integer page);

	/**
	 * Searches the BrAPI provider's list of studies, filtering by the
	 * parameters defined in the BrapiStudiesPost object via an HTTP POST.
	 *
	 * @param studiesPost	A BrapiStudiesPost object defining search parameters
	 *
	 * @return				A Retrofit Call object which contains a
	 * 						BrapiListResource which wraps a List of BrapiStudies
	 * 						objects which contain details of the studies which
	 * 						matched the search criteria
	 */
	@POST("search/studies")
	Call<BrapiListResource<Study>> getStudiesPost(@Body BrapiStudiesPost studiesPost);

	/**
	 * Searches the BrAPI provider's list of maps, filtering by the species and
	 * type parameters provided.
	 *
	 * @param commonCropName	A String representing the common name of the crop
	 * @param mapPUI			A string representing the DOI or other permanent identifier for this genomic map
	 * @param scientificName    A string representing the full scientific binomial format name.
	 * @param type				A String representing the type of the map (e.g. Genetic)
	 * @param programDbId		A String representing the unique id of a program
	 * @param trialDbId			A String representing the unique id of of a trial
	 * @param studyDbId			A string representing the uinque id of a study
	 * @param pageSize			The desired size of the returned page
	 * @param page				The desired page of data
	 *
	 * @return					A Retrofit Call object which contains a BrapiListResource which contains BrapiGenomeMap
	 * 							objects
	 */
	@GET("maps")
	Call<BrapiListResource<GenomeMap>> getMaps(@Query("commonCropName") String commonCropName, @Query("mapPUI") String mapPUI, @Query("scientificName") String scientificName, @Query("type") String type, @Query("programDbId") String programDbId, @Query("trialDbId") String trialDbId, @Query("studyDbId") String studyDbId, @Query("pageSize") Integer pageSize, @Query("page") Integer page);

//	@POST("maps")
//	Call<BrapiListResource<GenomeMap>> getMaps(@Body BrapiGenomeMapSearchPost mapSearchPost);

	/**
	 * Gets the information on an individual genome map
	 *
	 * @param mapDbId
	 * @param pageSize
	 * @param page
	 * @return
	 */
	@GET("maps/{mapDbId}")
	Call<BrapiBaseResource<GenomeMap>> getMapById(@Path("mapdDbId") String mapDbId, @Query("pageSize") Integer pageSize, @Query("page") Integer page);

	/**
	 * Gets a list of the MarkerPosition objects
	 *
	 * TODO: update comments here
	 * @param pageSize			The desired size of the returned page
	 * @param page				The desired page of data
	 *
	 * @return					A Retrofit Call object which contains a
	 * 							BrapiListResource which contains BrapiMarkerPositon
	 * 							objects
	 */
	@GET("markerpositions")
	Call<BrapiListResource<MarkerPosition>> getMarkerPositions(@Query("mapDbId") String mapDbId, @Query("linkageGroupName") String linkageGroupName, @Query("markerDbId") String markerDbId, @Query("minPosition") String minPositon, @Query("maxPosition") String maxPosition, @Query("pageSize") Integer pageSize, @Query("page") Integer page);

	/**
	 * Gets a list of CallSet objects
	 *
	 * @param callSetDbId
	 * @param callSetName
	 * @param variantSetDbId
	 * @param sampleDbId
	 * @param germplasmDbId
	 * @param pageSize
	 * @param page
	 * @return
	 */
	@GET("callsets")
	Call<BrapiListResource<CallSet>> getCallSets(@Query("callSetDbId") String callSetDbId, @Query("callSetName") String callSetName, @Query("variantSetDbId") String variantSetDbId, @Query("sampleDbId") String sampleDbId, @Query("germplasmDbId") String germplasmDbId, @Query("pageSize") Integer pageSize, @Query("page") Integer page);
}