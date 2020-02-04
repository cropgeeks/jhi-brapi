package jhi.brapi.client;

import jhi.brapi.api.*;

/**
 * Designed for use with the paginated calls defined within the BrAPI
 * specification. Call paginate with a BrAPI {@link jhi.brapi.api.Metadata}
 * object as the parameter and check the status of {@link #isPaging()} to
 * determine whether or not to continue paging through the data. This pager will
 * respect the page sizes specified by BrAPI implementations.
 */
public class Pager
{
	private boolean isPaging = true;
	private int pageSize = 1000;
	private int page = 0;

	public Pager()
	{
	}

	/**
	 * Additional constructor which enables specifying the number of elements
	 * the Pager will ask for in its first request to a BrAPI implementation.
	 * For further calls Pager will respect the page sizes being specified by
	 * the BrAPI implementation it is communicating with.
	 *
	 * @param pageSize	The number of elements asked for in the first request
	 */
	public Pager(int pageSize)
	{
		this.pageSize = pageSize;
	}

	/**
	 * Determine if another page of data is available, and the number of
	 * elements of data to request in that page.
	 *
	 * @param metadata	A {@link jhi.brapi.api.Metadata} object which will
	 *                  represnet the Metadata block return from a BrAPI
	 *                  implementation
	 */
	public void paginate(Metadata metadata)
	{
		Pagination p = metadata.getPagination();

		if (p instanceof PageNumberPagination)
		{
			PageNumberPagination pageData = (PageNumberPagination)p;
			if (p.getTotalPages() == 0 || pageData.getCurrentPage() == p.getTotalPages() - 1)
				isPaging = false;

				// Update the pageSize and page variables as we haven't yet reached the
				// end of the data
			else
			{
				pageSize = p.getPageSize();
				page = (pageData.getCurrentPage() + 1);
			}
		}

		// TODO: this will work for our own implementation, but needs to be update to handle situations where page tokens
		// are genuinely strings
		else if (p instanceof PageTokenPagination)
		{
			PageTokenPagination pageData = (PageTokenPagination)p;
			if (pageData.getNextPageToken() == null || pageData.getNextPageToken().isEmpty())
				isPaging = false;

			pageSize = pageData.getPageSize();
			page = Integer.parseInt(pageData.getNextPageToken());
		}
	}

	public boolean isPaging()
		{ return isPaging; }

	public void setPaging(boolean paging)
		{ isPaging = paging; }

	public int getPageSize()
		{ return pageSize; }

	public void setPageSize(int pageSize)
		{ this.pageSize = pageSize; }

	public int getPage()
		{ return page; }

	public void setPage(int page)
		{ this.page = page; }
}