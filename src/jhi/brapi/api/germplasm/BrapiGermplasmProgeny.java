package jhi.brapi.api.germplasm;

import java.util.*;

public class BrapiGermplasmProgeny
{
	private String defaultDisplayname;
	private String germplasmDbId;
	private List<BrapiProgeny> progeny;

	public String getDefaultDisplayname()
		{ return defaultDisplayname; }

	public void setDefaultDisplayname(String defaultDisplayname)
		{ this.defaultDisplayname = defaultDisplayname; }

	public String getGermplasmDbId()
		{ return germplasmDbId; }

	public void setGermplasmDbId(String germplasmDbId)
		{ this.germplasmDbId = germplasmDbId; }

	public List<BrapiProgeny> getProgeny()
		{ return progeny; }

	public void setProgeny(List<BrapiProgeny> progeny)
		{ this.progeny = progeny; }
}
