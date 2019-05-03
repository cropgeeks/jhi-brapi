package jhi.brapi.api.studies;

public class BrapiDataLinks
{
	private String dataLinkeName;
	private String name;
	private String type;
	private String url;

	public String getDataLinkeName()
		{ return dataLinkeName; }

	public void setDataLinkeName(String dataLinkeName)
		{ this.dataLinkeName = dataLinkeName; }

	public String getName()
	{ return name; }

	public void setName(String name)
	{ this.name = name; }

	public String getType()
		{ return type; }

	public void setType(String type)
		{ this.type = type; }

	public String getUrl()
		{ return url; }

	public void setUrl(String url)
		{ this.url = url; }
}
