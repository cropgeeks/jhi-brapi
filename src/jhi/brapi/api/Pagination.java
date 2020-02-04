package jhi.brapi.api;

public class Pagination
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
