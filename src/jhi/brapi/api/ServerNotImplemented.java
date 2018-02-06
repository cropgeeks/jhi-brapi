package jhi.brapi.api;

import org.restlet.resource.*;

import static org.restlet.data.Status.*;

public class ServerNotImplemented extends BaseBrapiServerResource<BrapiErrorResource>
{
	public void doInit()
	{
		super.doInit();
	}

	@Get("json")
	public BrapiErrorResource getJson()
	{
		BrapiErrorResource errorResource = new BrapiErrorResource();

		errorResource.getMetadata().getStatus().add(new Status("501", "Resource not implemented."));
		setStatus(SERVER_ERROR_NOT_IMPLEMENTED);

		return errorResource;
	}
}
