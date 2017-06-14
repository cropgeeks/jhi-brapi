package jhi.brapi.api.germplasm;

public class BrapiTaxon
{
	private String source;
	private String id;

	public String getSource()
	{
		return source;
	}

	public BrapiTaxon setSource(String source)
	{
		this.source = source;
		return this;
	}

	public String getId()
	{
		return id;
	}

	public BrapiTaxon setId(String id)
	{
		this.id = id;
		return this;
	}
}
