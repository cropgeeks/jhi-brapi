package jhi.brapi.api.markerprofiles;

import java.util.*;

import jhi.brapi.api.*;
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

	public BrapiBaseResource<BrapiAlleleMatrix> getJson()
	{
		throw new ResourceException(404);
	}

	@Post
	public JacksonRepresentation<BrapiBaseResource<BrapiAlleleMatrix>> post(Representation rep)
	{
		List<String> profileIds = new ArrayList<>();
		List<String> markerIds = new ArrayList<>();

		Form form = new Form(rep);
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
		}

		return new JacksonRepresentation<BrapiBaseResource<BrapiAlleleMatrix>>(alleleMatrixDAO.getFromHdf5(getRequest(), getContext(), profileIds, markerIds, format, params, currentPage, pageSize));
	}
}