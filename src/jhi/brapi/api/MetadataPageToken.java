package jhi.brapi.api;

import java.util.*;

public class MetadataPageToken
{
	private List<DataFile> datafiles;

	private PageTokenPagination pagination;

	private List<Status> status;

	public MetadataPageToken()
	{
		this.pagination = new PageTokenPagination();
		this.status = new ArrayList<Status>();
		this.datafiles = new ArrayList<DataFile>();
	}

	public PageTokenPagination getPagination()
		{ return pagination; }

	public void setPagination(PageTokenPagination pagination)
		{ this.pagination = pagination; }

	public List<Status> getStatus()
		{ return status; }

	public void setStatus(List<Status> status)
		{ this.status = status; }

	public List<DataFile> getDatafiles()
		{ return datafiles; }

	public void setDatafiles(List<DataFile> datafiles)
		{ this.datafiles = datafiles; }
}