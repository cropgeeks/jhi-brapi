package hutton.brapi.resource;

/**
 * @author Sebastian Raubach
 */
public class Pedigree
{
	private String germplasmId;
	private String pedigreeString;
	private String parent1Id;
	private String parent2Id;

	public String getGermplasmId()
	{
		return germplasmId;
	}

	public void setGermplasmId(String germplasmId)
	{
		this.germplasmId = germplasmId;
	}

	public String getPedigreeString()
	{
		return pedigreeString;
	}

	public void setPedigreeString(String pedigreeString)
	{
		this.pedigreeString = pedigreeString;
	}

	public String getParent1Id()
	{
		return parent1Id;
	}

	public void setParent1Id(String parent1Id)
	{
		this.parent1Id = parent1Id;
	}

	public String getParent2Id()
	{
		return parent2Id;
	}

	public void setParent2Id(String parent2Id)
	{
		this.parent2Id = parent2Id;
	}

	@Override
	public String toString()
	{
		return "Pedigree{" +
				"germplasmId='" + germplasmId + '\'' +
				", pedigreeString='" + pedigreeString + '\'' +
				", parent1Id='" + parent1Id + '\'' +
				", parent2Id='" + parent2Id + '\'' +
				'}';
	}
}
