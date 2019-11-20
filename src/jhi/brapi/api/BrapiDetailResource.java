package jhi.brapi.api;

import java.util.*;

public interface BrapiDetailResource<T>
{
	public List<T> getData();

	public void setData(List<T> data);
}
