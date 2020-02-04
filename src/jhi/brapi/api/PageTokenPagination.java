package jhi.brapi.api;

public class PageTokenPagination extends Pagination
{
	private String currentPageToken;
	private String nextPageToken;
	private String prevPageToken;

	public PageTokenPagination()
	{
	}

	public PageTokenPagination(int pageSize, String currentPageToken, long totalCount, int desiredPageSize)
	{
		this.pageSize = pageSize;
		this.currentPageToken = currentPageToken;
		this.totalCount = totalCount;
		this.totalPages = (int)Math.ceil(totalCount/(float) desiredPageSize);

		// If we can, generate valeus for prevPageToken and nextPageToken
		int currentPage = Integer.parseInt(currentPageToken);
		if (currentPage >=1)
			prevPageToken = String.valueOf(currentPage-1);
		if (currentPage < totalPages)
			nextPageToken = String.valueOf(currentPage+1);
	}

	public String getCurrentPageToken()
	{
		return currentPageToken;
	}

	public void setCurrentPageToken(String currentPageToken)
	{
		this.currentPageToken = currentPageToken;
	}

	public String getNextPageToken()
	{
		return nextPageToken;
	}

	public void setNextPageToken(String nextPageToken)
	{
		this.nextPageToken = nextPageToken;
	}

	public String getPrevPageToken()
	{
		return prevPageToken;
	}

	public void setPrevPageToken(String prevPageToken)
	{
		this.prevPageToken = prevPageToken;
	}
}
