package hutton.brapi.resource;

import java.util.*;

/**
 * @author Sebastian Raubach
 */
public class GermplasmSearchListPagination
{
	private Pagination            pagination;
	private List<GermplasmSearch> germplasm;

	public Pagination getPagination()
	{
		return pagination;
	}

	public void setPagination(Pagination pagination)
	{
		this.pagination = pagination;
	}

	public List<GermplasmSearch> getGermplasm()
	{
		return germplasm;
	}

	public void setGermplasm(List<GermplasmSearch> germplasm)
	{
		this.germplasm = germplasm;
	}

	@Override
	public String toString()
	{
		return "GermplasmSearchListPagination{" +
				"pagination=" + pagination +
				", germplasm=" + germplasm +
				'}';
	}
}
