package jhi.brapi.resource;

import java.util.*;

import jhi.brapi.data.*;

/**
 * Created by gs40939 on 03/11/2015.
 */
public class BasicResource<T>
{
	private Metadata metadata = new Metadata();

	private List<T> result = new ArrayList<T>();

	public BasicResource()
	{
		metadata.setPagination(PaginationUtils.getEmptyPagination());
	}

	// Should be our default choice for Brapi calls which paginate over a list of entries
	public BasicResource(List<T> result, int currentPage, int pageSize, long totalCount)
	{
		this.result = result;
		metadata.setPagination(new Pagination(result.size(), currentPage, totalCount, pageSize));
	}

	// Should be our default choice for Brapi calls which "paginate" over a single result
	public BasicResource(T result)
	{
		this.result = Collections.singletonList(result);
		metadata.setPagination(PaginationUtils.getPaginationForSingleResult());
	}

	// Should be our default choice for Brapi calls which paginate over a subset of a resource
	public BasicResource(T result, int currentPage, int pageSize, long totalCount)
	{
		this.result = Collections.singletonList(result);
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

	public List<T> getResult()
	{
		return result;
	}

	public void setResult(List<T> result)
	{
		this.result = result;
	}
}
