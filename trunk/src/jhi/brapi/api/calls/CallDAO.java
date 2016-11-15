package jhi.brapi.api.calls;

import java.util.*;
import java.util.stream.*;

import jhi.brapi.api.*;

public class CallDAO
{
	private static final List<BrapiCall> CALLS = new ArrayList<>();

	static
	{
		CALLS.add(new BrapiCall("allelematrix")
				.addDatatype("json")
				.addDatatype("tsv")
				.addMethod("POST")
				.addMethod("GET"));

		CALLS.add(new BrapiCall("calls")
				.addDatatype("json")
				.addMethod("GET"));

		CALLS.add(new BrapiCall("germplasm")
				.addDatatype("json")
				.addMethod("GET"));

		CALLS.add(new BrapiCall("germplasm/id")
				.addDatatype("json")
				.addMethod("GET"));

		CALLS.add(new BrapiCall("germplasm/id/mcpd")
				.addDatatype("json")
				.addMethod("GET"));

		CALLS.add(new BrapiCall("germplasm/id/pedigree")
				.addDatatype("json")
				.addMethod("GET"));

		CALLS.add(new BrapiCall("germplasm/id/markerprofiles")
				.addDatatype("json")
				.addMethod("GET"));

		CALLS.add(new BrapiCall("markers")
				.addDatatype("json")
				.addMethod("GET"));

		CALLS.add(new BrapiCall("markerprofiles")
				.addDatatype("json")
				.addMethod("GET"));

		CALLS.add(new BrapiCall("markerprofiles/id")
				.addDatatype("json")
				.addMethod("GET"));

		// TODO: Check if correct
		CALLS.add(new BrapiCall("studies")
				.addDatatype("json")
				.addMethod("GET"));

		CALLS.add(new BrapiCall("studies/studyDbId")
				.addDatatype("json")
				.addMethod("GET"));

		CALLS.add(new BrapiCall("maps")
				.addDatatype("json")
				.addMethod("GET"));

		CALLS.add(new BrapiCall("maps/mapId")
				.addDatatype("json")
				.addMethod("GET"));

		CALLS.add(new BrapiCall("maps/mapId/positions")
				.addDatatype("json")
				.addMethod("GET"));

		CALLS.add(new BrapiCall("locations")
				.addDatatype("json")
				.addMethod("GET"));
	}

	public static BrapiListResource<BrapiCall> getAll(String dataType, int currentPage, int pageSize)
	{
		List<BrapiCall> calls = CALLS;

		if (dataType != null && !Objects.equals(dataType, ""))
		{
			calls = calls.stream()
						 .filter(c -> c.getDatatypes().contains(dataType)) // Get the calls that support the query data type
						 .collect(Collectors.toCollection(ArrayList::new));
		}

		int start = PaginationUtils.getLowLimit(currentPage, pageSize);
		int end = Math.min(start + pageSize, calls.size());

		calls = calls.subList(start, end);

		return new BrapiListResource<BrapiCall>(calls, currentPage, pageSize, calls.size());
	}
}
