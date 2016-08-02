package jhi.brapi.resource;

import com.fasterxml.jackson.annotation.*;

/**
 * @author Sebastian Raubach
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BrapiGermplasmPost extends BasicPost
{
	private String germplasmPUI;
	private String germplasmDbId;
	private String germplasmSpecies;
	private String germplasmGenus;
	private String germplasmName;

	public String getGermplasmPUI()
	{
		return germplasmPUI;
	}

	public void setGermplasmPUI(String germplasmPUI)
	{
		this.germplasmPUI = germplasmPUI;
	}

	public String getGermplasmDbId()
	{
		return germplasmDbId;
	}

	public void setGermplasmDbId(String germplasmDbId)
	{
		this.germplasmDbId = germplasmDbId;
	}

	public String getGermplasmSpecies()
	{
		return germplasmSpecies;
	}

	public void setGermplasmSpecies(String germplasmSpecies)
	{
		this.germplasmSpecies = germplasmSpecies;
	}

	public String getGermplasmGenus()
	{
		return germplasmGenus;
	}

	public void setGermplasmGenus(String germplasmGenus)
	{
		this.germplasmGenus = germplasmGenus;
	}

	public String getGermplasmName()
	{
		return germplasmName;
	}

	public void setGermplasmName(String germplasmName)
	{
		this.germplasmName = germplasmName;
	}

	@Override
	public String toString()
	{
		return "BrapiGermplasmPost{" +
				"germplasmPUI='" + germplasmPUI + '\'' +
				", germplasmDbId='" + germplasmDbId + '\'' +
				", germplasmSpecies='" + germplasmSpecies + '\'' +
				", germplasmGenus='" + germplasmGenus + '\'' +
				", germplasmName='" + germplasmName + '\'' +
				", pageSize=" + pageSize +
				", page=" + page +
				'}';
	}
}
