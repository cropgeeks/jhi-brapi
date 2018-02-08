package jhi.brapi.api.markerprofiles;

import java.util.*;

import jhi.brapi.api.*;
import jhi.brapi.api.Status;
import jhi.brapi.util.*;

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

	private String format;
	private GenotypeEncodingParams params = new GenotypeEncodingParams();

	private String matrixDbId;

	public BrapiBaseResource<BrapiAlleleMatrix> getJson()
	{
		throw new ResourceException(404);
	}

	@Post("application/x-www-form-urlencoded")
	public BrapiBaseResource<BrapiAlleleMatrix> post(Form form)
	{
		List<String> profileIds = new ArrayList<>();
		List<String> markerIds = new ArrayList<>();

		for (Parameter parameter : form)
		{
			if (parameter.getName().equalsIgnoreCase("markerprofileDbId"))
				profileIds.add(parameter.getValue());

			else if (parameter.getName().equalsIgnoreCase("markerDbId"))
				markerIds.add(parameter.getValue());

			else if (parameter.getName().equalsIgnoreCase("format"))
				format = parameter.getValue();

			else if (parameter.getName().equalsIgnoreCase("unknownString"))
				params.setUnknownString(parameter.getValue());

			else if (parameter.getName().equalsIgnoreCase("sepPhased"))
				params.setSepPhased(parameter.getValue());

			else if (parameter.getName().equalsIgnoreCase("sepUnphased"))
				params.setSepUnphased(parameter.getValue());

			else if (parameter.getName().equalsIgnoreCase("matrixDbId"))
				matrixDbId = parameter.getValue();
		}


		if (matrixDbId != null)
			return alleleMatrixDAO.getFromHdf5ByMatrixId(getRequest(), getContext(), matrixDbId, params, currentPage, pageSize);

		return alleleMatrixDAO.getFromHdf5(getRequest(), getContext(), profileIds, markerIds, format, params, currentPage, pageSize);
	}

	@Post("json")
	public BrapiBaseResource<BrapiAlleleMatrix> post(BrapiAlleleMatrixSearchPost alleleMatrixSearchPost)
	{
		System.out.println(alleleMatrixSearchPost);

		if (alleleMatrixSearchPost.getUnknownString() != null)
			params.setUnknownString(alleleMatrixSearchPost.getUnknownString());
		if (alleleMatrixSearchPost.getSepPhased() != null)
			params.setSepPhased(alleleMatrixSearchPost.getSepPhased());
		if(alleleMatrixSearchPost.getSepUnphased() != null)
			params.setSepUnphased(alleleMatrixSearchPost.getSepUnphased());

		if (alleleMatrixSearchPost.getMatrixDbId() != null)
			return alleleMatrixDAO.getFromHdf5ByMatrixId(getRequest(), getContext(), alleleMatrixSearchPost.getMatrixDbId().get(0), params, currentPage, pageSize);

		return alleleMatrixDAO.getFromHdf5(getRequest(), getContext(), alleleMatrixSearchPost.getMarkerprofileDbId(), new ArrayList<String>(), alleleMatrixSearchPost.getFormat(), params, currentPage, pageSize);
	}
}