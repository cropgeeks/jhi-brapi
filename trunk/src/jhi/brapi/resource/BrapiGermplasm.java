package jhi.brapi.resource;

import java.util.*;

/**
 * @author Sebastian Raubach
 */

// Exclude non-null fields from the output
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrapiGermplasm
{
	private String       germplasmDbId;
	private String       defaultDisplayName;
	private String       germplasmName;
	private String       accessionNumber;
	private String       germplasmPUI;
	private String       pedigree;
	private String       seedSource;
	private List<String> synonyms;

	public String getGermplasmDbId()
	{
		return germplasmDbId;
	}

	public void setGermplasmDbId(String germplasmDbId)
	{
		this.germplasmDbId = germplasmDbId;
	}

	public String getDefaultDisplayName()
	{
		return defaultDisplayName;
	}

	public void setDefaultDisplayName(String defaultDisplayName)
	{
		this.defaultDisplayName = defaultDisplayName;
	}

	public String getGermplasmName()
	{
		return germplasmName;
	}

	public void setGermplasmName(String germplasmName)
	{
		this.germplasmName = germplasmName;
	}

	public String getAccessionNumber()
	{
		return accessionNumber;
	}

	public void setAccessionNumber(String accessionNumber)
	{
		this.accessionNumber = accessionNumber;
	}

	public String getGermplasmPUI()
	{
		return germplasmPUI;
	}

	public void setGermplasmPUI(String germplasmPUI)
	{
		this.germplasmPUI = germplasmPUI;
	}

	public String getPedigree()
	{
		return pedigree;
	}

	public void setPedigree(String pedigree)
	{
		this.pedigree = pedigree;
	}

	public String getSeedSource()
	{
		return seedSource;
	}

	public void setSeedSource(String seedSource)
	{
		this.seedSource = seedSource;
	}

	public List<String> getSynonyms()
	{
		return synonyms;
	}

	public void setSynonyms(List<String> synonyms)
	{
		this.synonyms = synonyms;
	}

	@Override
	public String toString()
	{
		return "BrapiGermplasm{" +
				"germplasmDbId='" + germplasmDbId + '\'' +
				", defaultDisplayName='" + defaultDisplayName + '\'' +
				", germplasmName='" + germplasmName + '\'' +
				", accessionNumber='" + accessionNumber + '\'' +
				", germplasmPUI='" + germplasmPUI + '\'' +
				", pedigree='" + pedigree + '\'' +
				", seedSource='" + seedSource + '\'' +
				", synonyms=" + synonyms +
				'}';
	}

	public enum MatchingMethod
	{
		EXACT,
		WILDCARD;

		public static MatchingMethod getValue(String input)
		{
			if (input == null || input.equals(""))
			{
				return EXACT;
			}
			else
			{
				try
				{
					return MatchingMethod.valueOf(input.toUpperCase());
				}
				catch (Exception e)
				{
					// TODO: Return a 501 HTTP error code
					return EXACT;
				}
			}
		}
	}
}