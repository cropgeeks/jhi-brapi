package jhi.brapi.api.studies;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrapiDataLinks
{
	private String type;
	private String name;
	private String url;

	public String getType()
		{ return type; }

	public void setType(String type)
		{ this.type = type; }

	public String getName()
		{ return name; }

	public void setName(String name)
		{ this.name = name; }

	public String getUrl()
		{ return url; }

	public void setUrl(String url)
		{ this.url = url; }
}
