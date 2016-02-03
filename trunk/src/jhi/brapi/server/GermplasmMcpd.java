package jhi.brapi.server;

import org.restlet.resource.*;

import java.util.*;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

/**
 * Given an id, returns the markerprofile ids which relate to the germplasm indicated by id.
 */
public class GermplasmMcpd extends BaseBrapiServerResource
{
	private GermplasmDAO germplasmDAO = new GermplasmDAO();

	private String id;

	@Override
	public void doInit()
	{
		super.doInit();
		this.id = getRequestAttributes().get("id").toString();
	}

	@Get("json")
	public BasicResource<BrapiGermplasmMcpd> getJson()
	{
		List<BrapiGermplasmMcpd> list = germplasmDAO.getMcpdFor(id);

		return new BasicResource<>(list);
	}
}
