package jhi.brapi.api;

public class BasicPost
{
	protected Integer pageSize;
	protected Integer page;

	public Integer getPageSize()
		{ return pageSize; }

	public void setPageSize(Integer pageSize)
		{ this.pageSize = pageSize; }

	public Integer getPage()
		{ return page; }

	public void setPage(Integer page)
		{ this.page = page; }
}