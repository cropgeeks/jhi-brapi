package jhi.brapi.api;

import java.util.*;

public class Result<T>
{
	private List<T> data;

	Result()
	{
	}

	public Result(List<T> data)
	{
		this.data = data;
	}

	public List<T> getData()
		{ return data; }

	public void setData(List<T> data)
		{ this.data = data; }
}
