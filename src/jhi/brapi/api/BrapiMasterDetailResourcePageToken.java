package jhi.brapi.api;

public class BrapiMasterDetailResourcePageToken<T extends BrapiDetailResource>
{
	private MetadataPageToken metadata = new MetadataPageToken();

	private T result;

	public BrapiMasterDetailResourcePageToken()
	{
		metadata.setPagination(new PageTokenPagination());
	}

	// Should be our default choice for Brapi calls which "paginate" over a single result
	public BrapiMasterDetailResourcePageToken(T detail, int currentPage, int pageSize, long totalCount)
	{
		this.result = detail;

		metadata.setPagination(new PageTokenPagination(pageSize, String.valueOf(currentPage), totalCount, pageSize));
	}

	public MetadataPageToken getMetadata()
		{ return metadata; }

	public void setMetadata(MetadataPageToken metadata)
		{ this.metadata = metadata; }

	public T getResult()
		{ return result; }

	public void setResult(T result)
		{ this.result = result; }
}