package jhi.brapi.api;

public class PageNumberPagination extends Pagination
{
	private int currentPage;

	public PageNumberPagination()
	{
	}

	public PageNumberPagination(int pageSize, int currentPage, long totalCount, int desiredPageSize)
	{
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		this.totalPages = (int)Math.ceil(totalCount/(float) desiredPageSize);
	}

	public static PageNumberPagination empty()
	{
		return new PageNumberPagination(0, 0, 0, 0);
	}

	public static PageNumberPagination forSingleResult()
	{
		return new PageNumberPagination(1, 0, 1, 1);
	}

	public int getCurrentPage()
		{ return currentPage; }

	public void setCurrentPage(int currentPage)
		{ this.currentPage = currentPage; }

	@Override
	public String toString()
	{
		return "Pagination{" +
			"pageSize=" + pageSize +
			", currentPage=" + currentPage +
			", totalCount=" + totalCount +
			", totalPages=" + totalPages +
			'}';
	}
}