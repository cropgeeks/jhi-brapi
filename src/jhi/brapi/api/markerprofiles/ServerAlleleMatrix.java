package jhi.brapi.api.markerprofiles;

import java.util.*;

import jhi.brapi.api.*;

import org.restlet.data.*;
import org.restlet.ext.jackson.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

/**
 * Queries the database for the ServerGermplasmSearch with the given ID then returns a JSON (Jackson) representation of the ServerGermplasmSearch for API clients to consume.
 */
public class ServerAlleleMatrix extends BaseBrapiServerResource
{
	private AlleleMatrixDAO alleleMatrixDAO = new AlleleMatrixDAO();

	// Temporarily included markerProfileDAO for dummy HTML get of ServerAlleleMatrix
	private MarkerProfileDAO markerProfileDAO = new MarkerProfileDAO();
	private String format;
	private jhi.brapi.util.GenotypeEncodingParams params = new jhi.brapi.util.GenotypeEncodingParams();

	// Temporary, TODO remove this after testing with large DB to find limits of call
//	@Get("json")
	public BrapiBaseResource<BrapiAlleleMatrix> getJson()
	{
////		List<String> profileIds = new ArrayList<>();
////
////		List<BrapiMarkerProfile> list = markerProfileDAO.getAll();
////		for (BrapiMarkerProfile profile : list)
////			profileIds.add(profile.getMarkerprofileDbId());
////
////		List<ServerAlleleMatrix> list = alleleMatrixDAO.get(profileIds);
////
////		return new BrapiBaseResource<ServerAlleleMatrix>(list);
//
//		// Not implemented because we're not parsing the markerprofileDbId params yet
//
		throw new ResourceException(404);
	}

//	@Get("json")
//	public Representation retrieve()
//	{
//		List<ServerAlleleMatrix> list = new ArrayList<>();
////		if (markerIds.isEmpty())
////		list = alleleMatrixDAO.get(profileIds);
////		else
////			matrix = alleleMatrixDAO.get(profileIds, markerIds);
//
//		BrapiBaseResource<ServerAlleleMatrix> br = new BrapiBaseResource<ServerAlleleMatrix>(list);
//
//		return new JacksonRepresentation<>(br);
//	}

	@Post
	public JacksonRepresentation<BrapiBaseResource<BrapiAlleleMatrix>> post(Representation rep)
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

//		if(format != null) // TODO: Why is this using the DB and not HDF5?
//			return new JacksonRepresentation<BrapiBaseResource<ServerAlleleMatrix>>(alleleMatrixDAO.get(getRequest(), profileIds, markerIds, format, params, currentPage, pageSize));
//		else
			return new JacksonRepresentation<BrapiBaseResource<BrapiAlleleMatrix>>(alleleMatrixDAO.getFromHdf5(getRequest(), getContext(), profileIds, markerIds, format, params, currentPage, pageSize));
	}
}