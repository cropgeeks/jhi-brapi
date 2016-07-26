package jhi.brapi.resource;

import java.util.*;

/**
 * Created by gs40939 on 12/04/2016.
 */
public class DataResult<T>
{
	private List<T> data = new ArrayList<>();

	public DataResult()
	{
	}

	public DataResult(List<T> data)
	{
		this.data = data;
	}

	public List<T> getData()
	{
		return data;
	}

	public void setData(List<T> data)
	{
		this.data = data;
	}
}
