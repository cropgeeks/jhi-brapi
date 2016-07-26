package jhi.brapi.resource;

import java.util.*;

/**
 * Created by gs40939 on 03/11/2015.
 */
public class Metadata
{
	private Pagination pagination;

	private List<Status> status;

	private List<Datafile> datafiles;

	public Metadata()
	{
		this.pagination = new Pagination();
		this.status = new ArrayList<Status>();
		this.datafiles = new ArrayList<Datafile>();
	}

	public Pagination getPagination()
	{
		return pagination;
	}

	public void setPagination(Pagination pagination)
	{
		this.pagination = pagination;
	}

	public List<Status> getStatus()
	{
		return status;
	}

	public void setStatus(List<Status> status)
	{
		this.status = status;
	}

	public List<Datafile> getDatafiles()
	{
		return datafiles;
	}

	public void setDatafiles(List<Datafile> datafiles)
	{
		this.datafiles = datafiles;
	}
}
