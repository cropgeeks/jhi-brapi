package jhi.brapi.client;

import jhi.brapi.api.*;

public class TokenPager
{
	private boolean isPaging = true;
	private int pageSize = 1000;

	private String pageToken;
	private String nextPageToken;
	private String prevPageToken;

	public TokenPager()
	{
	}

	/**
	 * Determine if another page of data is available, and the number of
	 * elements of data to request in that page.
	 *
	 * @param metadata	A {@link jhi.brapi.api.MetadataPageToken} object which will
	 *                  represent the Metadata block return from a BrAPI
	 *                  implementation
	 */
	public void paginate(MetadataPageToken metadata)
	{
		PageTokenPagination p = metadata.getPagination();

		if (p.getTotalPages() == 0 || p.getNextPageToken() == null)
			isPaging = false;

		else
		{
			pageSize = p.getPageSize();
			pageToken = p.getCurrentPageToken();
			nextPageToken = p.getNextPageToken();
			prevPageToken = p.getPrevPageToken();
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

	public String getPageToken()
		{ return pageToken; }

	public void setPageToken(String pageToken)
		{ this.pageToken = pageToken; }

	public String getNextPageToken()
		{ return nextPageToken; }

	public void setNextPageToken(String nextPageToken)
		{ this.nextPageToken = nextPageToken; }

	public String getPrevPageToken()
		{ return prevPageToken; }

	public void setPrevPageToken(String prevPageToken)
		{ this.prevPageToken = prevPageToken; }
}