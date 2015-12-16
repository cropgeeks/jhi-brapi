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
public class AlleleMatrixServerResource extends ServerResource
{
	private AlleleMatrixDAO alleleMatrixDAO = new AlleleMatrixDAO();

	// Temporarily included markerProfileDAO for dummy HTML get of AlleleMatrix
	private MarkerProfileDAO markerProfileDAO = new MarkerProfileDAO();

	// The ID from the URI (need to get this in overridden doInit method)
	private String id;

	@Override
	public void doInit()
	{
		super.doInit();
		this.id = (String)getRequestAttributes().get("id");
	}

	// Temporary, TODO remove this after testing with large DB to find limits of call
	@Get("html")
	public Representation retrieve()
	{
		List<String> profileIds = new ArrayList<>();

		List<BrapiMarkerProfile> list = markerProfileDAO.getAll();
		for (BrapiMarkerProfile profile : list)
			profileIds.add(profile.getMarkerprofileId());

		AlleleMatrix matrix = alleleMatrixDAO.get(profileIds);

		if (matrix != null)
		{
			JacksonRepresentation<AlleleMatrix> rep = new JacksonRepresentation<>(matrix);
			rep.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
			return rep;
		}

		throw new ResourceException(404);
	}

	@Post("form:html")
	public Representation post(Representation entity)
	{
		List<String> profileIds = new ArrayList<>();
		List<String> markerIds = new ArrayList<>();

		Form form = new Form(entity);
		for (Parameter parameter : form)
		{
			if (parameter.getName().equals("markerprofileId"))
				profileIds.add(parameter.getValue());

			if (parameter.getName().equals("markerId"))
				markerIds.add(parameter.getValue());
		}

		AlleleMatrix matrix;
		if (markerIds.isEmpty())
			matrix = alleleMatrixDAO.get(profileIds);
		else
			matrix = alleleMatrixDAO.get(profileIds, markerIds);

		if (matrix != null)
			return new JacksonRepresentation<>(matrix);

		throw new ResourceException(404);
	}
}