package jhi.brapi.api.markerprofiles;

import java.util.*;

import jhi.brapi.api.*;
import jhi.brapi.api.Status;

import org.restlet.resource.*;

public class ServerStatus extends BaseBrapiServerResource
{
	private String id;

	@Override
	public void doInit()
	{
		super.doInit();
		this.id = getRequestAttributes().get("id").toString();
	}

	@Get("json")
	public BrapiListResource<Object> getJson()
	{
		BrapiListResource<Object> test = new BrapiListResource<>(new ArrayList<>(), 0, 0, 0);

		String url = getRequest().getRootRef().toString();
		String datafile = url + "/files/" + id;

		AsynchStatus asynchStatus = new AsynchStatus();
		asynchStatus.setAsynchId(id);
		asynchStatus.setStatus("FINISHED");

		test.getMetadata().getStatus().add(new Status("asynchstatus", "FINISHED"));
		test.getMetadata().setDatafiles(Collections.singletonList(datafile));
		test.getMetadata().setAsynchStatus(asynchStatus);

		return test;
	}
}