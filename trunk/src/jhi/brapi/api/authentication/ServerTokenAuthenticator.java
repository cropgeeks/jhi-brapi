package jhi.brapi.api.authentication;

import java.math.*;
import java.security.*;

import jhi.brapi.*;
import jhi.brapi.api.*;
import jhi.brapi.util.*;

import org.restlet.resource.*;

public class ServerTokenAuthenticator extends BaseBrapiServerResource
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
		return login(new BrapiTokenPost());
	}

	@Post
	public BrapiSessionToken login(BrapiTokenPost post)
	{
		grantType = post.getGrant_type();
		username = post.getUsername();
		password = post.getPassword();
		clientId = post.getClient_id();

		// Authenticate with Germinate Gatekeeper
		if (GatekeeperUtils.authenticate(username, password) == false)
			throw new ResourceException(400);

		// Generate a unique token for this session
		String token = new BigInteger(256, new SecureRandom()).toString(32);
		// And add it to the sessions stored server-side
		Brapi.getSessions().addSession(username, token);

		// Create a BrapiSessionToken object to return the result to the client
		BrapiSessionToken sessionToken = new BrapiSessionToken();
		sessionToken.setSessionToken(token);

		// BrapiSessionToken is a special case and doesn't get metadata by
		// default, so we need to include it here (even though it isn't used)
		Metadata md = new Metadata();
		md.setPagination(Pagination.empty());
		sessionToken.setMetadata(md);

		return sessionToken;
	}
}