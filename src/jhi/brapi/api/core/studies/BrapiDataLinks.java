package jhi.brapi.api.core.studies;

public class BrapiDataLinks
{
	private String dataLinkeName = "";
	private String type = "";
	private String url = "";
	private String version = "";

	public String getDataLinkeName()
		{ return dataLinkeName; }

	public void setDataLinkeName(String dataLinkeName)
		{ this.dataLinkeName = dataLinkeName; }

	public String getType()
		{ return type; }

	public void setType(String type)
		{ this.type = type; }

	public String getUrl()
		{ return url; }

	public void setUrl(String url)
		{ this.url = url; }

	public String getVersion()
		{ return version; }

	public void setVersion(String version)
		{ this.version = version; }
}
