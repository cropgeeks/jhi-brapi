package jhi.brapi.resource;

import java.util.List;

/**
 * Created by gs40939 on 03/11/2015.
 */
public class BasicResource<T>
{
	private Metadata metadata;

	private List<T> result;

	public BasicResource() {}

	public BasicResource(List<T> result)
	{
		this.result = result;
	}

	public Metadata getMetadata()
	{
		if (metadata == null)
			metadata = new Metadata();

		return metadata;
	}

	public void setMetadata(Metadata metadata)
	{
		this.metadata = metadata;
	}

	public List<T> getResult()
	{
		return result;
	}

	public void setResult(List<T> result)
	{
		this.result = result;
	}
}
