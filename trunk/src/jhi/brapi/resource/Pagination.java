package jhi.brapi.resource;

/**
 * @author Sebastian Raubach
 */
public class Pagination
{
	private int pageSize;
	private int currentPage;
	private long totalCount;
	private int totalPages;

	public Pagination()
	{
	}

	public Pagination(int pageSize, int currentPage, long totalCount, int desiredPageSize)
	{
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		this.totalPages = (int)Math.ceil(totalCount/(float) desiredPageSize);

		System.out.println(this);
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public int getCurrentPage()
	{
		return currentPage;
	}

	public void setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;
	}

	public long getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(long totalCount)
	{
		this.totalCount = totalCount;
	}

	public int getTotalPages()
	{
		return totalPages;
	}

	public void setTotalPages(int totalPages)
	{
		this.totalPages = totalPages;
	}

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
