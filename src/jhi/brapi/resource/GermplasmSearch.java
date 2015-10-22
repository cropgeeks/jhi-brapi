package jhi.brapi.resource;

import java.util.*;

/**
 * @author Sebastian Raubach
 */
public class GermplasmSearch
{
	private String       accessionNumber;
	private List<String> synonyms;
	private String       germplasmPUI;
	private String       germplasmId;
	private String       germplasmName;
	private String       breederName;

	public String getAccessionNumber()
	{
		return accessionNumber;
	}

	public void setAccessionNumber(String accessionNumber)
	{
		this.accessionNumber = accessionNumber;
	}

	public List<String> getSynonyms()
	{
		return synonyms;
	}

	public void setSynonyms(List<String> synonyms)
	{
		this.synonyms = synonyms;
	}

	public String getGermplasmPUI()
	{
		return germplasmPUI;
	}

	public void setGermplasmPUI(String germplasmPUI)
	{
		this.germplasmPUI = germplasmPUI;
	}

	public String getGermplasmId()
	{
		return germplasmId;
	}

	public void setGermplasmId(String germplasmId)
	{
		this.germplasmId = germplasmId;
	}

	public String getGermplasmName()
	{
		return germplasmName;
	}

	public void setGermplasmName(String germplasmName)
	{
		this.germplasmName = germplasmName;
	}

	public String getBreederName()
	{
		return breederName;
	}

	public void setBreederName(String breederName)
	{
		this.breederName = breederName;
	}

	@Override
	public String toString()
	{
		return "GermplasmSearch{" +
				"accessionNumber='" + accessionNumber + '\'' +
				", synonyms=" + synonyms +
				", germplasmPUI='" + germplasmPUI + '\'' +
				", germplasmId='" + germplasmId + '\'' +
				", germplasmName='" + germplasmName + '\'' +
				", breederName='" + breederName + '\'' +
				'}';
	}
}