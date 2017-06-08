package jhi.brapi.client;

import java.util.*;

import jhi.brapi.api.*;

public class AsyncChecker
{
	public static final String ASYNCID = "asynchid";
	public static final String ASYNCSTATUS = "asynchstatus";
	public static final String ASYNC_INPROCESS = "INPROCESS";
	public static final String ASYNC_FINISHED = "FINISHED";
	public static final String ASYNC_FAILED = "FAILED";

	public static Status hasAsyncId(List<Status> statuses)
	{
		Status status = null;

		Optional<Status> asyncStatus = statuses.stream()
			.filter(s -> s.getCode().equals(ASYNCID))
			.findFirst();

		if (asyncStatus.isPresent())
			status = asyncStatus.get();

		return status;
	}

	public static Status checkAsyncStatus(List<Status> statuses)
	{
		Status status = null;

		Optional<Status> asyncStatus = statuses.stream()
			.filter(s -> s.getCode().equals(ASYNCSTATUS))
			.findFirst();

		if (asyncStatus.isPresent())
			status = asyncStatus.get();

		return status;
	}

	public static boolean callInProcess(Status status)
	{
		return status != null && status.getMessage().equals(ASYNC_INPROCESS);
	}

	public static boolean callFinished(Status status)
	{
		return status != null && status.getMessage().equals(ASYNC_FINISHED);
	}
}
