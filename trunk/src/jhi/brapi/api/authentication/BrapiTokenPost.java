package jhi.brapi.api.authentication;

import com.fasterxml.jackson.annotation.*;
import jhi.brapi.api.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrapiTokenPost
{
	private String username;
	private String password;
	private String grant_type;
	private String client_id;

	public String getUsername()
		{ return username; }

	public void setUsername(String username)
		{ this.username = username; }

	public String getPassword()
		{ return password; }

	public void setPassword(String password)
		{ this.password = password; }

	public String getGrant_type()
		{ return grant_type; }

	public void setGrant_type(String grant_type)
		{ this.grant_type = grant_type; }

	public String getClient_id()
		{ return client_id; }

	public void setClient_id(String client_id)
		{ this.client_id = client_id; }
}