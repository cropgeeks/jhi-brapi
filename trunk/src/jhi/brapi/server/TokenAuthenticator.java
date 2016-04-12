package jhi.brapi.server;

import jhi.brapi.resource.*;

import org.restlet.resource.*;

public class TokenAuthenticator extends ServerResource
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

	@Get
	public BrapiSessionToken getJson()
	{
		return login();
	}

	@Post
	public BrapiSessionToken login()
	{
		String token = "blah" + System.currentTimeMillis();

		Brapi.getSessions().addSession(username, token);
		token = "sessions.size(): " + Brapi.getSessions().sessions.size();

		BrapiSessionToken sessionToken = new BrapiSessionToken();
		sessionToken.setSessionToken(token);

		return sessionToken;
	}
}