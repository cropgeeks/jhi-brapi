package jhi.brapi.api;

import com.fasterxml.jackson.annotation.*;

@JsonTypeInfo(
	use = JsonTypeInfo.Id.NAME,
	include = JsonTypeInfo.As.PROPERTY,
	property = "type")
@JsonSubTypes({
	@JsonSubTypes.Type(value = PageTokenPagination.class, name = "pagetokenpagination"),
	@JsonSubTypes.Type(value = PageNumberPagination.class, name = "pagenumberpagination")
})
public abstract class Pagination
{
	protected int pageSize;
	protected long totalCount;
	protected int totalPages;

	public int getPageSize()
		{ return pageSize; }

	public void setPageSize(int pageSize)
		{ this.pageSize = pageSize; }

	public long getTotalCount()
		{ return totalCount; }

	public void setTotalCount(long totalCount)
		{ this.totalCount = totalCount; }

	public int getTotalPages()
		{ return totalPages; }

	public void setTotalPages(int totalPages)
		{ this.totalPages = totalPages; }
}
