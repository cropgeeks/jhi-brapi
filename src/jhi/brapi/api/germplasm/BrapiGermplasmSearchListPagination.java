package jhi.brapi.api.germplasm;

import java.util.*;

import jhi.brapi.api.*;

public class BrapiGermplasmSearchListPagination
{
	private Pagination pagination;
	private List<BrapiGermplasmSearch> germplasm;

	public Pagination getPagination()
		{ return pagination;
		}

	public void setPagination(Pagination pagination)
		{ this.pagination = pagination; }

	public List<BrapiGermplasmSearch> getGermplasm()
		{ return germplasm; }

	public void setGermplasm(List<BrapiGermplasmSearch> germplasm)
		{ this.germplasm = germplasm; }

	@Override
	public String toString()
	{
		return "BrapiGermplasmSearchListPagination{" +
				"pagination=" + pagination +
				", germplasm=" + germplasm +
				'}';
	}
}