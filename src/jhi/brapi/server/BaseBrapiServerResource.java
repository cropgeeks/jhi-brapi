package jhi.brapi.server;

import com.fasterxml.jackson.databind.*;

import org.restlet.ext.jackson.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

import jhi.brapi.resource.*;

/**
 * Base ServerResource type for our BRAPI API implementation. All ServerResource objects should extend from this.
 * The format of our base call T getJson() is set out using an abstract method. In addition to this a concrete
 * implementation of the getHtml() method is provided which will respond to request for the ServerResource in html
 * format by returning pretty printed JSON in html text.
 */
public abstract class BaseBrapiServerResource<T> extends ServerResource
{
	protected static final String PARAM_PAGE_SIZE = "pageSize";
	protected static final String PARAM_CURRENT_PAGE = "page";

	// TODO: do we want to configure this value in the environment somehow (e.g. properties file etc...)
	// TODO: once finished testing set back to Integer.MAX_VALUE, or something more suitable
	protected int pageSize = 2000;//Integer.MAX_VALUE;
	protected int currentPage = 0;

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

	/**
	 * Returns an HTML formatted pretty printed version of the JSON response for this server resource. This will be
	 * preseneted by default to anything which requests text/html, most likely a web browser.
	 *
	 * @return	JacksonRespresentation<T> of the resource.
	 * @throws	ResourceException - a restlet 404 if the object isn't found.
	 */
	@Get("html")
	public Representation getHtml()
	{
		T result = getJson();

		if (result != null)
		{
			JacksonRepresentation<T> rep = new JacksonRepresentation<>(result);
			rep.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
			return rep;
		}

		throw new ResourceException(404);
	}
}