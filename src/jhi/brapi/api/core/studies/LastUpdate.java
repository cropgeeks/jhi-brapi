package jhi.brapi.api.core.studies;

public class LastUpdate
{
	private String timestamp = "";
	private String version = "";

	public LastUpdate()
	{
	}

	public LastUpdate(String timestamp, String version)
	{
		this.timestamp = timestamp;
		this.version = version;
	}

	public String getTimestamp()
		{ return timestamp; }

	public void setTimestamp(String timestamp)
		{ this.timestamp = timestamp; }

	public String getVersion()
		{ return version; }

	public void setVersion(String version)
		{ this.version = version; }
}
