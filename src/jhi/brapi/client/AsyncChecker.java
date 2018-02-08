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

	public enum AsyncStatus
	{
		PENDING,
		INPROCESS,
		FINISHED,
		FAILED,
		UNKNOWN
	}

	public static Status hasAsyncId(List<Status> statuses)
	{
		Status status = null;

		Optional<Status> asyncStatus = statuses.stream()
			.filter(s -> s.getCode().equalsIgnoreCase(ASYNCID) || s.getCode().equalsIgnoreCase("asynchid"))
			.findFirst();

		if (asyncStatus.isPresent())
			status = asyncStatus.get();

		return status;
	}

	public static AsyncStatus checkAsyncStatus(List<Status> statuses)
	{
		Status status = null;

		Optional<Status> asyncStatus = statuses.stream()
			.filter(s -> s.getCode().equalsIgnoreCase(ASYNCSTATUS) || s.getCode().equalsIgnoreCase("asynchstatus"))
			.findFirst();

		if (asyncStatus.isPresent())
			status = asyncStatus.get();

		AsyncStatus found = AsyncStatus.UNKNOWN;

		if (callPending(status))
			found = AsyncStatus.PENDING;
		else if (callInProcess(status))
			found = AsyncStatus.INPROCESS;
		else if (callFinished(status))
			found = AsyncStatus.FINISHED;
		else if (callFailed(status))
			found = AsyncStatus.FAILED;

		return found;
	}

	private static boolean callPending(Status status)
	{
		return status != null && status.getMessage().equalsIgnoreCase(ASYNC_PENDING);
	}

	private static boolean callInProcess(Status status)
	{
		return status != null && status.getMessage().equalsIgnoreCase(ASYNC_INPROCESS);
	}

	private static boolean callFinished(Status status)
	{
		return status != null && status.getMessage().equalsIgnoreCase(ASYNC_FINISHED);
	}

	private static boolean callFailed(Status status)
	{
		return status != null && status.getMessage().equalsIgnoreCase(ASYNC_FAILED);
	}
}
