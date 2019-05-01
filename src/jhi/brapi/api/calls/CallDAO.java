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
			.withMethodGet()
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

		CALLS.add(new BrapiCall("calls")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("commoncropnames")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("germplasm")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("germplasm/{germplasmDbId}")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("germplasm/{germplasmDbId}/pedigree")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("germplasm/{germplasmDbId}/markerprofiles")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("germplasm-search")
			.withDatatypeJson()
			.withMethodGet()
			.withMethodPost()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("locations")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("maps")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("maps/{mapDbId}")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("maps/{mapDbId}/positions")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("markers")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("markerprofiles")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("markerprofiles/{markerprofileDbId}")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("phenotypes-search")
			.withDatatypeJson()
			.withMethodGet()
			.withMethodPost()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("studies")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("studies/{studyDbId}")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("studies-search")
			.withDatatypeJson()
			.withMethodGet()
			.withMethodPost()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("trials")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneThree());

		CALLS.add(new BrapiCall("trials/{trialDbId}")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionOneThree());

		// NOTE: no longer officially a BrAPI call. We kept this in for comparatbility with Gobii.
		CALLS.add(new BrapiCall("token")
			.withDatatypeJson()
			.withMethodPost());

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