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
		CALLS.add(new BrapiCall("allelematrices")
			.withDatatypeJson()
			.withMethodGet());

		CALLS.add(new BrapiCall("allelematrix-search")
			.withDatatypeJson()
			.withDatatypeTsv()
			.withDatatypeFlapjack()
			.withMethodPost());

		CALLS.add(new BrapiCall("allelematrix-search/status/{id}")
			.withDatatypeJson()
			.withMethodGet());

		CALLS.add(new BrapiCall("calls")
			.withDatatypeJson()
			.withMethodGet());

		CALLS.add(new BrapiCall("germplasm-search")
			.withDatatypeJson()
			.withMethodGet()
			.withMethodPost());

		CALLS.add(new BrapiCall("germplasm/{id}")
			.withDatatypeJson()
			.withMethodGet());

		CALLS.add(new BrapiCall("germplasm/{id}/pedigree")
			.withDatatypeJson()
			.withMethodGet());

		CALLS.add(new BrapiCall("germplasm/{id}/markerprofiles")
			.withDatatypeJson()
			.withMethodGet());

		CALLS.add(new BrapiCall("markers")
			.withDatatypeJson()
			.withMethodGet());

		CALLS.add(new BrapiCall("markerprofiles")
			.withDatatypeJson()
			.withMethodGet());

		CALLS.add(new BrapiCall("markerprofiles/{id}")
			.withDatatypeJson()
			.withMethodGet());

		CALLS.add(new BrapiCall("studies-search")
			.withDatatypeJson()
			.withMethodGet()
			.withMethodPost());

		CALLS.add(new BrapiCall("studies/{id}")
			.withDatatypeJson()
			.withMethodGet());

		CALLS.add(new BrapiCall("maps")
			.withDatatypeJson()
			.withMethodGet());

		CALLS.add(new BrapiCall("maps/{id}")
			.withDatatypeJson()
			.withMethodGet());

		CALLS.add(new BrapiCall("maps/{id}/positions")
			.withDatatypeJson()
			.withMethodGet());

		CALLS.add(new BrapiCall("locations")
			.withDatatypeJson()
			.withMethodGet());

		CALLS.add(new BrapiCall("phenotypes-search")
			.withDatatypeJson()
			.withMethodGet()
			.withMethodPost());

		CALLS.add(new BrapiCall("token")
			.withDatatypeJson()
			.withMethodPost());

		CALLS.add(new BrapiCall("trials")
			.withDatatypeJson()
			.withMethodGet());

		CALLS.add(new BrapiCall("trials/{id}")
			.withDatatypeJson()
			.withMethodGet());

		CALLS.add(new BrapiCall("crops")
			.withDatatypeJson()
			.withMethodGet());

		CALLS.sort(Comparator.comparing(BrapiCall::getCall));
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