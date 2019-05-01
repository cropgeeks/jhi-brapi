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
		CALLS.add(new BrapiCall("calls")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneOne()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("crops")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneOne());

		CALLS.add(new BrapiCall("commoncropnames")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("locations")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneOne()
			.withVersionOneThree());

		// TODO: Add /locations/locationDbId

		CALLS.add(new BrapiCall("trials")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneOne()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("trials/{trialDbId}")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneOne()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("studies-search")
			.withDatatypeJson()
			.withMethodGet()
			.withMethodPost()
			.withVersionOneOne());

		// TODO: Add to codebase
		CALLS.add(new BrapiCall("studies")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("studies/{studyDbId}")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneOne()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("phenotypes-search")
			.withDatatypeJson()
			.withMethodGet()
			.withMethodPost()
			.withVersionOneOne());

		CALLS.add(new BrapiCall("germplasm-search")
			.withDatatypeJson()
			.withMethodGet()
			.withMethodPost()
			.withVersionOneOne());

		// TODO: Add to codebase
		CALLS.add(new BrapiCall("germplasm")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("germplasm/{germplasmDbId}")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneOne()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("germplasm/{germplasmDbId}/pedigree")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneOne()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("germplasm/{germplasmDbId}/markerprofiles")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneOne()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("markers")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneOne()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("allelematrices")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneOne()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("allelematrices-search")
			.withDatatypeJson()
			.withDatatypeTsv()
			.withDatatypeFlapjack()
			.withMethodPost()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("allelematrices-search/status/{id}")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("markerprofiles")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneOne()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("markerprofiles/{markerprofileDbId}")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneOne()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("maps")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneOne()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("maps/{mapDbId}")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneOne()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("maps/{mapDbId}/positions")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneOne()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("token")
			.withDatatypeJson()
			.withMethodPost()
			.withVersionOneOne());

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