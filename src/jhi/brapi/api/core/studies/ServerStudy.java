package jhi.brapi.api.core.studies;

import jhi.brapi.api.*;
import org.restlet.resource.*;

import java.util.*;

public class ServerStudy extends BaseBrapiServerResource
{
	private StudiesDAO studiesDAO = new StudiesDAO();

	private String id;

	@Override
	public void doInit()
	{
		super.doInit();
		this.id = getRequestAttributes().get("id").toString();
	}

	@Get("json")
	public BrapiBaseResource<Study> getJson()
	{
		return studiesDAO.getById(id);
	}
}