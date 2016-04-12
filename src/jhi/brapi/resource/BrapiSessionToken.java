package jhi.brapi.resource;

/**
 * Created by gs40939 on 16/03/2016.
 */
public class BrapiSessionToken
{
	private String sessionToken;

	public String getSessionToken()
	{
		return sessionToken;
	}

	public void setSessionToken(String sessionToken)
	{
		this.sessionToken = sessionToken;
	}

	@Override
	public String toString()
	{
		return "BrapiSessionToken{" +
			"sessionToken='" + sessionToken + '\'' +
			'}';
	}
}
