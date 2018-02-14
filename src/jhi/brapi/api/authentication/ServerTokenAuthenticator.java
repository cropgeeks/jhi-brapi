package jhi.brapi.api.authentication;

import jhi.brapi.api.*;
import jhi.brapi.util.*;

import org.restlet.data.Status;
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

	@Delete
	public BrapiBaseResource<Void> logout(BrapiTokenLogoutPost post)
	{
		BrapiBaseResource<Void> logoutResponse = new BrapiBaseResource<>();

		setStatus(Status.SERVER_ERROR_NOT_IMPLEMENTED);

		return logoutResponse;
	}

	@Get("json")
	public BrapiSessionToken getJson()
	{
		BrapiSessionToken sessionToken = new BrapiSessionToken();

		// BrapiSessionToken is a special case and doesn't get metadata by
		// default, so we need to include it here (even though it isn't used)
		Metadata md = new Metadata();
		md.setPagination(Pagination.empty());
		md.getStatus().add(new jhi.brapi.api.Status("405", "Method not allowed"));
		sessionToken.setMetadata(md);

		setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
		// GET not supported
		throw new ResourceException(405);
	}

	@Post
	public BrapiSessionToken login(BrapiTokenLoginPost post)
	{
		grantType = post.getGrant_type();
		username = post.getUsername();
		password = post.getPassword();
		clientId = post.getClient_id();

		BrapiSessionToken sessionToken = GatekeeperUtils.authenticate(username, password);

		if (sessionToken != null)
		{
			// BrapiSessionToken is a special case and doesn't get metadata by
			// default, so we need to include it here (even though it isn't used)
			Metadata md = new Metadata();
			md.setPagination(Pagination.empty());
			sessionToken.setMetadata(md);

			setStatus(Status.SUCCESS_CREATED);
		}
		else
		{
			sessionToken = new BrapiSessionToken();

			// BrapiSessionToken is a special case and doesn't get metadata by
			// default, so we need to include it here (even though it isn't used)
			Metadata md = new Metadata();
			md.setPagination(Pagination.empty());
			md.getStatus().add(new jhi.brapi.api.Status("40", "No objects found for given parameters"));
			sessionToken.setMetadata(md);

			setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
		}

		return sessionToken;
	}
}