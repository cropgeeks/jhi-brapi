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

	public static Optional<Status> hasAsyncId(List<Status> statuses)
	{
		return statuses.stream()
			.filter(s -> s.getCode().equals(ASYNCID))
			.findFirst();
	}

	public static Optional<Status> checkAsyncStatus(List<Status> statuses)
	{
		return statuses.stream()
			.filter(s -> s.getCode().equals(ASYNCSTATUS))
			.findFirst();
	}

	public static boolean callInProcess(Status status)
	{
		return status.getMessage().equals(ASYNC_INPROCESS);
	}

	public static boolean callFinished(Status status)
	{
		return status.getMessage().equals(ASYNC_FINISHED);
	}
}
