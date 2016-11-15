package jhi.brapi.api;

public class Datafile
{
	private String url;

	public Datafile()
	{
	}

	public Datafile(String url)
		{ this.url = url; }

	public String getUrl()
		{ return url; }

	public void setUrl(String url)
		{ this.url = url; }
}