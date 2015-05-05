package uk.ac.hutton.brapi.server;

import org.restlet.representation.*;
import org.restlet.resource.*;

/**
 * Simple resource which returns a Restlet StringRepresentation of Hello World.
 */
public class HelloServerResource extends ServerResource
{
	@Get
	public StringRepresentation represent()
	{
		return new StringRepresentation("hello, world");
	}
}