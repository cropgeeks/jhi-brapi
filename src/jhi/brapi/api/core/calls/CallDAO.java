package jhi.brapi.api.core.calls;

import java.util.*;
import java.util.stream.*;

import jhi.brapi.api.*;
import jhi.brapi.util.*;

public class CallDAO
{
	private static final List<BrapiCall> CALLS = new ArrayList<>();

	static
	{
		CALLS.add(new BrapiCall("commoncropnames")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionTwoZero());

		CALLS.add(new BrapiCall("locations")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionTwoZero());

		CALLS.add(new BrapiCall("maps")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionTwoZero());

		CALLS.add(new BrapiCall("studies")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionTwoZero());

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