package jhi.brapi.server;

import jhi.brapi.resource.*;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

import org.restlet.resource.*;

public class TokenAuthenticator extends BaseBrapiServerResource
{
	private String grantType;
	private String username;
	private String password;
	private String clientId;

	@Override
	public void doInit()
	{
		super.doInit();

		try
		{
			this.username = getQueryValue("username");
			this.password = getQueryValue("password");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		try
		{
			this.grantType = getQueryValue("grant_type");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		try
		{
			this.clientId = getQueryValue("client_id");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Get("json")
	public BrapiSessionToken getJson()
	{
		throw new ResourceException(404);
	}

	@Post
	public BrapiSessionToken login(Representation rep)
	{
		String token = "blah" + System.currentTimeMillis();

		Brapi.getSessions().addSession(username, token);
		token = "sessions.size(): " + Brapi.getSessions().sessions.size();

		BrapiSessionToken sessionToken = new BrapiSessionToken();
		sessionToken.setSessionToken(token);

		// BrapiSessionToken is a special case and doesn't get metadata by
		// default, so we need to include it here (even though it isn't used)
		Metadata md = new Metadata();
		md.setPagination(PaginationUtils.getEmptyPagination());
		sessionToken.setMetadata(md);

		return sessionToken;
	}
}