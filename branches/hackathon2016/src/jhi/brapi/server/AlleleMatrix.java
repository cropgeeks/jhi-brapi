package jhi.brapi.server;

import java.util.*;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

import com.fasterxml.jackson.databind.SerializationFeature;

import org.restlet.data.*;
import org.restlet.ext.jackson.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

/**
 * Queries the database for the Germplasm with the given ID then returns a JSON (Jackson) representation of the
 * Germplasm for API clients to consume.
 */
public class AlleleMatrix extends BaseBrapiServerResource
{
	private AlleleMatrixDAO alleleMatrixDAO = new AlleleMatrixDAO();

	// Temporarily included markerProfileDAO for dummy HTML get of BrapiAlleleMatrix
	private MarkerProfileDAO markerProfileDAO = new MarkerProfileDAO();

	// Temporary, TODO remove this after testing with large DB to find limits of call
//	@Get("json")
	public BasicResource<BrapiAlleleMatrix> getJson()
	{
////		List<String> profileIds = new ArrayList<>();
////
////		List<BrapiMarkerProfile> list = markerProfileDAO.getAll();
////		for (BrapiMarkerProfile profile : list)
////			profileIds.add(profile.getMarkerprofileDbId());
////
////		List<BrapiAlleleMatrix> list = alleleMatrixDAO.get(profileIds);
////
////		return new BasicResource<BrapiAlleleMatrix>(list);
//
//		// Not implemented because we're not parsing the markerprofileDbId params yet
//
		throw new ResourceException(404);
	}

//	@Get("json")
//	public Representation retrieve()
//	{
//		List<BrapiAlleleMatrix> list = new ArrayList<>();
////		if (markerIds.isEmpty())
////		list = alleleMatrixDAO.get(profileIds);
////		else
////			matrix = alleleMatrixDAO.get(profileIds, markerIds);
//
//		BasicResource<BrapiAlleleMatrix> br = new BasicResource<BrapiAlleleMatrix>(list);
//
//		return new JacksonRepresentation<>(br);
//	}

	@Post
	public JacksonRepresentation post(Representation rep)
	{
		List<String> profileIds = new ArrayList<>();
		List<String> markerIds = new ArrayList<>();

		Form form = new Form(rep);
		for (Parameter parameter : form)
		{
			if (parameter.getName().equals("markerprofileDbId"))
				profileIds.add(parameter.getValue());
//
//			if (parameter.getName().equals("markerId"))
//				markerIds.add(parameter.getValue());
		}

		return new JacksonRepresentation(alleleMatrixDAO.get(profileIds, currentPage, pageSize));
	}
}