package jhi.brapi.api;

import java.util.*;
import java.util.stream.*;

import jhi.brapi.client.*;

import org.restlet.resource.*;

/**
 * Base ServerResource type for our BRAPI API implementation. All ServerResource objects should extend from this.
 * The format of our base call T getJson() is set out using an abstract method.
 */
public abstract class BaseBrapiServerResource<T> extends ServerResource
{
	protected static final String PARAM_PAGE_SIZE = "pageSize";
	protected static final String PARAM_CURRENT_PAGE = "page";
	protected static final String PARAM_PAGE_TOKEN = "pageToken";

	// TODO: do we want to configure this value in the environment somehow (e.g. properties file etc...)
	// TODO: once finished testing set back to Integer.MAX_VALUE, or something more suitable
	protected int pageSize = 1000;//Integer.MAX_VALUE;
	protected int currentPage = 0;
	protected String pageToken;

	@Override
	public void doInit()
	{
		super.doInit();

		String pageSize = getQueryValue(PARAM_PAGE_SIZE);
		if (pageSize != null)
		{
			try
			{
				this.pageSize = Integer.parseInt(getQueryValue(PARAM_PAGE_SIZE));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		String page = getQueryValue(PARAM_CURRENT_PAGE);
		if (page != null)
		{
			try
			{
				this.currentPage = Integer.parseInt(getQueryValue(PARAM_CURRENT_PAGE));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		pageToken = getQueryValue(PARAM_PAGE_TOKEN);
	}

	protected List<String> parseGetParameterList(String input)
	{
		input = getQueryValue(input);

		if (input != null)
			return Arrays.stream(input.split(","))
						 .map(String::trim)
						 .collect(Collectors.toList());
		else
			return null;
	}

	protected void setPaginationParameters(BasicPost parameters)
	{
		if(parameters != null)
		{
			if(parameters.getPage() != null)
				this.currentPage = parameters.getPage();
			if(parameters.getPageSize() != null)
				this.pageSize = parameters.getPageSize();
		}
	}

	@Get("json")
	public abstract T getJson();


	protected void setHttpResponseCode(List<Status> statuses)
	{
		if (StatusChecker.isServerError(statuses))
			setStatus(org.restlet.data.Status.SERVER_ERROR_INTERNAL);
		else if (StatusChecker.isNoObjectsFound(statuses))
			setStatus(org.restlet.data.Status.SUCCESS_NO_CONTENT);
		else if (StatusChecker.isNotFound(statuses))
			setStatus(org.restlet.data.Status.CLIENT_ERROR_NOT_FOUND);
	}

	protected void addParameter(Map<String, List<String>> map, String key, String value)
	{
		if (key != null && value != null && value.length() != 0)
			map.put(key, Collections.singletonList(value));
	}
}