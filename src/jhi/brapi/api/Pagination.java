package jhi.brapi.api;

public class Pagination
{
	private int currentPage;
	protected int pageSize;
	protected long totalCount;
	protected int totalPages;

	public Pagination()
	{
	}

	public Pagination(int pageSize, int currentPage, long totalCount, int desiredPageSize)
	{
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		this.totalPages = (int)Math.ceil(totalCount/(float) desiredPageSize);
	}

	public static Pagination empty()
	{
		return new Pagination(0, 0, 0, 0);
	}

	public static Pagination forSingleResult()
	{
		return new Pagination(1, 0, 1, 1);
	}

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

	public int getCurrentPage()
		{ return currentPage; }

	public void setCurrentPage(int currentPage)
		{ this.currentPage = currentPage; }
}
