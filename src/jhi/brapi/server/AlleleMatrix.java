package jhi.brapi.server;

import org.restlet.data.*;
import org.restlet.engine.util.*;
import org.restlet.ext.jackson.*;
import org.restlet.ext.servlet.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

import java.util.*;

import javax.servlet.*;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

/**
 * Queries the database for the Germplasm with the given ID then returns a JSON (Jackson) representation of the Germplasm for API clients to consume.
 */
public class AlleleMatrix extends BaseBrapiServerResource
{
	private AlleleMatrixDAO alleleMatrixDAO = new AlleleMatrixDAO();

	// Temporarily included markerProfileDAO for dummy HTML get of BrapiAlleleMatrix
	private MarkerProfileDAO markerProfileDAO = new MarkerProfileDAO();
	private String format;
	private GenotypeEncodingParams params = new GenotypeEncodingParams();

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
	public JacksonRepresentation<BasicResource<BrapiAlleleMatrix>> post(Representation rep)
	{
		List<String> profileIds = new ArrayList<>();
		List<String> markerIds = new ArrayList<>();

		Form form = new Form(rep);
		for (Parameter parameter : form)
		{
			if (parameter.getName().equals("markerprofileDbId"))
				profileIds.add(parameter.getValue());
			else if (parameter.getName().equals("markerDbId"))
				markerIds.add(parameter.getValue());
			else if (parameter.getName().equals("format"))
				format = parameter.getValue();
			else if (parameter.getName().equals("unknownString"))
				params.setUnknownString(parameter.getValue());
			else if (parameter.getName().equals("sepPhased"))
				params.setSepPhased(parameter.getValue());
			else if (parameter.getName().equals("sepUnphased"))
				params.setSepUnphased(parameter.getValue());
		}

		// TODO: Get page and pageSize parameters and potentially all of these parameters from json post?

		if(format != null)
			return new JacksonRepresentation<BasicResource<BrapiAlleleMatrix>>(alleleMatrixDAO.get(getRequest(), profileIds, markerIds, format, params, currentPage, pageSize));
		else
			return new JacksonRepresentation<BasicResource<BrapiAlleleMatrix>>(alleleMatrixDAO.getFromHdf5(getContext(), profileIds, markerIds, params, currentPage, pageSize));
	}
}