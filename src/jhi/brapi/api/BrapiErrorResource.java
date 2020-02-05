package jhi.brapi.api;

import java.util.*;

public class BrapiErrorResource
{
	private Metadata metadata = new Metadata();
	private Map<Object, Object> result = new HashMap<>();

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

	public void setResult(Map<Object, Object> result)
	{
		this.result = result;
	}
}
