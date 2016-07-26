package jhi.brapi.resource;

/**
 * Created by gs40939 on 16/03/2016.
 */
public class BrapiSessionToken
{
	private Metadata metadata;

	private String sessionToken;

	public BrapiSessionToken()
	{
	}

	public String getSessionToken()
	{
		return sessionToken;
	}

	public void setSessionToken(String sessionToken)
	{
		this.sessionToken = sessionToken;
	}

	public Metadata getMetadata()
	{
		return metadata;
	}

	public void setMetadata(Metadata metadata)
	{
		this.metadata = metadata;
	}
}
