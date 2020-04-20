package jhi.brapi.api.core.serverinfo;

import java.util.*;
import java.util.stream.*;

import jhi.brapi.api.*;

public class ServerInfoDAO
{
	private static final List<BrapiCall> CALLS = new ArrayList<>();

	static
	{
		CALLS.add(new BrapiCall("callsets")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionTwoZero());

		CALLS.add(new BrapiCall("callsets/{id}")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionTwoZero());

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

		CALLS.add(new BrapiCall("maps/{id}")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionTwoZero());

		CALLS.add(new BrapiCall("maps/{id}/linkagegroups")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionTwoZero());

		CALLS.add(new BrapiCall("markerpositions")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionTwoZero());

		CALLS.add(new BrapiCall("studies")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionTwoZero());

		CALLS.add(new BrapiCall("variantsets")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionTwoZero());

		CALLS.add(new BrapiCall("variantsets/{id}")
			.withDatatypeJson()
			.withMethodGet()
			.withVersionTwoZero());

		CALLS.sort(Comparator.comparing(BrapiCall::getService));
	}

	public static BrapiBaseResource<BrapiServerInfo> getAll(String dataType)
	{
		List<BrapiCall> calls = CALLS;

		if (dataType != null && !dataType.isEmpty())
		{
			calls = calls.stream()
						 .filter(c -> c.getDataTypes().contains(dataType)) // Get the calls that support the query data type
						 .collect(Collectors.toCollection(ArrayList::new));
		}

		BrapiServerInfo serverInfo = new BrapiServerInfo();
		serverInfo.setCalls(calls);
		serverInfo.setOrganizationName("The James Hutton Institute");
		serverInfo.setOrganizationURL("https://www.hutton.ac.uk");

		return new BrapiBaseResource<BrapiServerInfo>(serverInfo, 0, calls.size(), calls.size());
	}
}