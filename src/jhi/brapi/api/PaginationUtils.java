package jhi.brapi.api;

public class PaginationUtils
{
	public static Pagination getEmptyPagination()
	{
		return new Pagination(0, 0, 0, 0);
	}

	public static Pagination getPaginationForSingleResult()
	{
		return new Pagination(1, 0, 1, 1);
	}

	public static int getLowLimit(int currentPage, int pageSize)
	{
		return currentPage * pageSize;
	}
}