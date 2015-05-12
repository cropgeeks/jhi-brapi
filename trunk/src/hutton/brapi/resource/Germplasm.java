package hutton.brapi.resource;

import java.util.*;

/**
 * Created by gs40939 on 24/04/2015.
 */
public class Germplasm
{
	private int id;
	private int taxonId;
	private String germplasmName;
	private List<String> synonyms;
	private int breedingProgramId;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
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
			"id=" + id +
			", taxonId=" + taxonId +
			", germplasmName='" + germplasmName + '\'' +
			", synonyms=" + synonyms +
			", breedingProgramId=" + breedingProgramId +
			'}';
	}
}