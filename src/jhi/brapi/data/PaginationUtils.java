package jhi.brapi.data;

import jhi.brapi.resource.Pagination;

/**
 * Created by gs40939 on 02/03/2016.
 */
public class PaginationUtils
{
	public static Pagination getEmptyPagination()
	{
		return new Pagination(0, 0, 0);
	}

	public static Pagination getPaginationForSingleResult()
	{
		return new Pagination(1, 0, 1);
	}

	public static int getLowLimit(int currentPage, int pageSize)
	{
		return currentPage * pageSize;
	}
}
