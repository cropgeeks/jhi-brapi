package jhi.brapi.client;

import java.util.*;

import jhi.brapi.api.*;

public class AsyncChecker
{
	public static final String ASYNCID = "asyncid";
	public static final String ASYNCSTATUS = "asyncstatus";
	public static final String ASYNC_PENDING = "PENDING";
	public static final String ASYNC_INPROCESS = "INPROCESS";
	public static final String ASYNC_FINISHED = "FINISHED";
	public static final String ASYNC_FAILED = "FAILED";

	public static Status hasAsyncId(List<Status> statuses)
	{
		Status status = null;

		// TODO: remove temporary workarounds once other implemtentations are up to date
		Optional<Status> asyncStatus = statuses.stream()
			.filter(s -> s.getCode().equalsIgnoreCase(ASYNCID) ||  s.getCode().equalsIgnoreCase("asynchid"))
			.findFirst();

		if (asyncStatus.isPresent())
			status = asyncStatus.get();

		return status;
	}

	public static Status checkAsyncStatus(List<Status> statuses)
	{
		Status status = null;

		// TODO: remove temporary workarounds once other implemtentations are up to date
		Optional<Status> asyncStatus = statuses.stream()
			.filter(s -> s.getCode().equalsIgnoreCase(ASYNCSTATUS) || s.getCode().equalsIgnoreCase("asynchstatus"))
			.findFirst();

		if (asyncStatus.isPresent())
			status = asyncStatus.get();

		return status;
	}

	public static boolean callInProcess(Status status)
	{
		return status != null && status.getMessage().equalsIgnoreCase(ASYNC_INPROCESS) || status.getMessage().equalsIgnoreCase(ASYNC_PENDING);
	}

	public static boolean callFinished(Status status)
	{
		return status != null && status.getMessage().equalsIgnoreCase(ASYNC_FINISHED);
	}

	public static boolean callFailed(Status status)
	{
		return status != null && status.getMessage().equalsIgnoreCase(ASYNC_FAILED);
	}
}
