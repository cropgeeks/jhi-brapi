package jhi.brapi.resource;

public class BrapiBaseResource<T>
{
	private Metadata metadata = new Metadata();

	private T result;

	public BrapiBaseResource()
	{
		metadata.setPagination(PaginationUtils.getEmptyPagination());
	}

	// Should be our default choice for Brapi calls which "paginate" over a single result
	public BrapiBaseResource(T result)
	{
		this.result = result;
		metadata.setPagination(PaginationUtils.getPaginationForSingleResult());
	}

	// Should be our default choice for Brapi calls which paginate over a subset of a resource
	public BrapiBaseResource(T result, int currentPage, int pageSize, long totalCount)
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
