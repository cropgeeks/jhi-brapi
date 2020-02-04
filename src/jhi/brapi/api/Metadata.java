package jhi.brapi.api;

import java.util.*;

public class Metadata
{
	private List<DataFile> datafiles;

	private PageNumberPagination pagination;

	private List<Status> status;

	public Metadata()
	{
		this.pagination = new PageNumberPagination();
		this.status = new ArrayList<Status>();
		this.datafiles = new ArrayList<DataFile>();
	}

	public PageNumberPagination getPagination()
		{ return pagination; }

	public void setPagination(PageNumberPagination pagination)
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