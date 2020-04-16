package jhi.brapi.api;

public class BrapiMasterDetailResource<T extends BrapiDetailResource>
{
	private Metadata metadata = new Metadata();

	private T result;

	public BrapiMasterDetailResource()
	{
		metadata.setPagination(Pagination.empty());
	}

	// Should be our default choice for Brapi calls which "paginate" over a single result
	public BrapiMasterDetailResource(T detail, int currentPage, int pageSize, long totalCount)
	{
		this.result = detail;

		metadata.setPagination(new Pagination(pageSize, currentPage, totalCount, pageSize));
	}

	public Metadata getMetadata()
		{ return metadata; }

	public void setMetadata(Metadata metadata)
		{ this.metadata = metadata; }

	public T getResult()
		{ return result; }

	public void setResult(T result)
		{ this.result = result; }
}