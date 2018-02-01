package jhi.brapi.api;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrapiErrorResource
{
	private Metadata metadata = new Metadata();
	private Object result = new Object();

	public BrapiErrorResource()
	{
		metadata.setPagination(Pagination.empty());
	}

	public Metadata getMetadata()
	{
		return metadata;
	}

	public void setMetadata(Metadata metadata)
	{
		this.metadata = metadata;
	}

	public Object getResult()
	{
		return result;
	}

	public void setResult(Object result)
	{
		this.result = result;
	}
}
