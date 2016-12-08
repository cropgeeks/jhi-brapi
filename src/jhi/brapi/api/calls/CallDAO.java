package jhi.brapi.api.calls;

import java.util.*;
import java.util.stream.*;

import jhi.brapi.api.*;
import jhi.brapi.util.*;

public class CallDAO
{
	private static final List<BrapiCall> CALLS = new ArrayList<>();

	static
	{
		CALLS.add(new BrapiCall("allelematrix-search")
			.addDatatype("json")
			.addDatatype("tsv")
			.addMethod("POST"));

		CALLS.add(new BrapiCall("calls")
			.addDatatype("json")
			.addMethod("GET"));

		CALLS.add(new BrapiCall("germplasm-search")
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
		CALLS.add(new BrapiCall("studies-search")
			.addDatatype("json")
			.addMethod("GET"));

		CALLS.add(new BrapiCall("studies/id")
			.addDatatype("json")
			.addMethod("GET"));

		CALLS.add(new BrapiCall("maps")
			.addDatatype("json")
			.addMethod("GET"));

		CALLS.add(new BrapiCall("maps/id")
			.addDatatype("json")
			.addMethod("GET"));

		CALLS.add(new BrapiCall("maps/id/positions")
			.addDatatype("json")
			.addMethod("GET"));

		CALLS.add(new BrapiCall("locations")
			.addDatatype("json")
			.addMethod("GET"));

		CALLS.add(new BrapiCall("token")
			.addDatatype("json")
			.addMethod("GET"));
	}

	public static BrapiListResource<BrapiCall> getAll(String dataType, int currentPage, int pageSize)
	{
		List<BrapiCall> calls = CALLS;

		if (dataType != null && !dataType.isEmpty())
		{
			calls = calls.stream()
						 .filter(c -> c.getDatatypes().contains(dataType)) // Get the calls that support the query data type
						 .collect(Collectors.toCollection(ArrayList::new));
		}

		int start = DatabaseUtils.getLimitStart(currentPage, pageSize);
		int end = Math.min(start + pageSize, calls.size());

		calls = calls.subList(start, end);

		return new BrapiListResource<BrapiCall>(calls, currentPage, pageSize, calls.size());
	}
}