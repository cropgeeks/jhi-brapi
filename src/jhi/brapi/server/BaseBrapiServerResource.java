package jhi.brapi.server;

import com.fasterxml.jackson.databind.*;

import org.restlet.ext.jackson.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

/**
 * Created by gs40939 on 26/06/2015.
 *
 * Base ServerResource type for our BRAPI API implementation. All ServerResource objects should extend from this.
 * The format of our base call T getJson() is set out using an abstract method. In addition to this a concrete
 * implementation of the getHtml() method is provided which will respond to request for the ServerResource in html
 * format by returning pretty printed JSON in html text.
 */
public abstract class BaseBrapiServerResource<T> extends ServerResource
{
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