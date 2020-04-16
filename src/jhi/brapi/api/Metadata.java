package jhi.brapi.api;

import java.util.*;

public class Metadata
{
	private List<DataFile> datafiles;

	private Pagination pagination;

	private List<Status> status;

	public Metadata()
	{
		this.pagination = new Pagination();
		this.status = new ArrayList<Status>();
		this.datafiles = new ArrayList<DataFile>();
	}

	public Pagination getPagination()
		{ return pagination; }

	public void setPagination(Pagination pagination)
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