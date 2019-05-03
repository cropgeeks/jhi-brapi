package jhi.brapi.api.germplasm;

public class BrapiProgeny
{
	private String defaultDisplayName;
	private String germplasmDbId;
	private String parentType;

	public String getDefaultDisplayName()
		{ return defaultDisplayName; }

	public void setDefaultDisplayName(String defaultDisplayName)
		{ this.defaultDisplayName = defaultDisplayName; }

	public String getGermplasmDbId()
		{ return germplasmDbId; }

	public void setGermplasmDbId(String germplasmDbId)
		{ this.germplasmDbId = germplasmDbId; }

	public String getParentType()
		{ return parentType; }

	public void setParentType(String parentType)
		{ this.parentType = parentType; }
}
