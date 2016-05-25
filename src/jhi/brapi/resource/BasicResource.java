package jhi.brapi.resource;

import jhi.brapi.data.*;

/**
 * Created by gs40939 on 03/11/2015.
 */
public class BasicResource<T>
{
	private Metadata metadata = new Metadata();

	private T result;

	public BasicResource()
	{
		metadata.setPagination(PaginationUtils.getEmptyPagination());
	}

	// Should be our default choice for Brapi calls which "paginate" over a single result
	public BasicResource(T result)
	{
		this.result = result;
		metadata.setPagination(PaginationUtils.getPaginationForSingleResult());
	}

	// Should be our default choice for Brapi calls which paginate over a subset of a resource
	public BasicResource(T result, int currentPage, int pageSize, long totalCount)
	{
		this.result = result;
		metadata.setPagination(new Pagination(pageSize, currentPage, totalCount, pageSize));
	}

	public Metadata getMetadata()
	{
		return metadata;
	}

	public void setMetadata(Metadata metadata)
	{
		this.metadata = metadata;
	}

	public T getResult()
	{
		return result;
	}

	public void setResult(T result)
	{
		this.result = result;
	}
}
