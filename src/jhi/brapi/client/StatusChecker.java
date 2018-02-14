package jhi.brapi.client;

import java.util.*;

import jhi.brapi.api.*;

public class StatusChecker
{
	public static final String HTTP_500 = "500";
	public static final String HTTP_404 = "404";
	public static final String NO_OBJECTS_FOUND = "40";

	public static boolean isServerError(List<Status> statuses)
	{
		return statuses.stream()
			.anyMatch(s -> s.getCode().equals(HTTP_500));
	}

	public static boolean isNoObjectsFound(List<Status> statuses)
	{
		return statuses.stream()
			.anyMatch(s -> s.getCode().equals(NO_OBJECTS_FOUND));
	}

	public static boolean isNotFound(List<Status> statuses)
	{
		return statuses.stream()
			.anyMatch(s -> s.getCode().equals(HTTP_404));
	}
}
