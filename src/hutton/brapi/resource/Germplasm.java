package hutton.brapi.resource;

import java.util.*;

/**
 * Created by gs40939 on 24/04/2015.
 */
public class Germplasm
{
	private int germplasmId;
	private int taxonId;
	private String germplasmName;
	private List<String> synonyms;
	private int breedingProgramId;

	public int getGermplasmId()
	{
		return germplasmId;
	}

	public void setGermplasmId(int germplasmId)
	{
		this.germplasmId = germplasmId;
	}

	public int getTaxonId()
	{
		return taxonId;
	}

	public void setTaxonId(int taxonId)
	{
		this.taxonId = taxonId;
	}

	public String getGermplasmName()
	{
		return germplasmName;
	}

	public void setGermplasmName(String germplasmName)
	{
		this.germplasmName = germplasmName;
	}

	public List<String> getSynonyms()
	{
		return synonyms;
	}

	public void setSynonyms(List<String> synonyms)
	{
		this.synonyms = synonyms;
	}

	public int getBreedingProgramId()
	{
		return breedingProgramId;
	}

	public void setBreedingProgramId(int breedingProgramId)
	{
		this.breedingProgramId = breedingProgramId;
	}

	@Override
	public String toString()
	{
		return "Germplasm{" +
			"germplasmId=" + germplasmId +
			", taxonId=" + taxonId +
			", germplasmName='" + germplasmName + '\'' +
			", synonyms=" + synonyms +
			", breedingProgramId=" + breedingProgramId +
			'}';
	}
}