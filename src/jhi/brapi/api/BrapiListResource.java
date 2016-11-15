package jhi.brapi.api;

import java.util.*;

public class BrapiListResource<T>
{
	private Metadata metadata = new Metadata();

	private Map<String, List<T>> result;

	public BrapiListResource()
	{
		metadata.setPagination(PaginationUtils.getEmptyPagination());
	}

	// Should be our default choice for Brapi calls which "paginate" over a single result
	public BrapiListResource(List<T> list, int currentPage, int pageSize, long totalCount)
	{
		this.result = wrapList("data", list);
		metadata.setPagination(new Pagination(pageSize, currentPage, totalCount, pageSize));
	}

	private Map<String, List<T>> wrapList(String label, List<T> wrappedObject)
	{
		HashMap<String, List<T>> map = new HashMap<>();
		map.put(label, wrappedObject);

		return map;
	}

	public Metadata getMetadata()
		{ return metadata; }

	public void setMetadata(Metadata metadata)
		{ this.metadata = metadata; }

	public Map<String, List<T>> getResult()
		{ return result; }

	public void setResult(Map<String, List<T>> result)
		{ this.result = result; }
}