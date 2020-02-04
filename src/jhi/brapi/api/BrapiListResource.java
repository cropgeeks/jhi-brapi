package jhi.brapi.api;

import java.util.*;

public class BrapiListResource<T>
{
	private Metadata metadata = new Metadata();

	private Result<T> result;

	public BrapiListResource()
	{
		metadata.setPagination(PageNumberPagination.empty());
	}

	// Should be our default choice for Brapi calls which "paginate" over a single result
	public BrapiListResource(List<T> list, int currentPage, int pageSize, long totalCount)
	{
		this.result = new Result<T>(list);
		metadata.setPagination(new PageNumberPagination(Math.min(pageSize, list.size()), currentPage, totalCount, pageSize));
	}

	public Metadata getMetadata()
		{ return metadata; }

	public void setMetadata(Metadata metadata)
		{ this.metadata = metadata; }

	public Result getResult()
		{ return result; }

	public void setResult(Result<T> result)
		{ this.result = result; }

	public List<T> data()
	{
		return result.getData();
	}
}