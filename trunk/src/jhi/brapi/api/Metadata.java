package jhi.brapi.api;

import java.util.*;

public class Metadata
{
	private Pagination pagination;

	private List<Status> status;

	private List<String> datafiles;

	public Metadata()
	{
		this.pagination = new Pagination();
		this.status = new ArrayList<Status>();
		this.datafiles = new ArrayList<String>();
	}

	public Pagination getPagination()
		{ return pagination; }

	public void setPagination(Pagination pagination)
		{ this.pagination = pagination; }

	public List<Status> getStatus()
		{ return status; }

	public void setStatus(List<Status> status)
		{ this.status = status; }

	public List<String> getDatafiles()
		{ return datafiles; }

	public void setDatafiles(List<String> datafiles)
		{ this.datafiles = datafiles; }
}