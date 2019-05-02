package jhi.brapi.api.germplasm;

import java.util.*;

public class BrapiGermplasmPedigree
{
	private String crossingPlan;
	private String crossingYear;
	private String defaultDisplayName;
	private String familyCode;
	private String germplasmDbId;
	private String parent1DbId;
	private String parent1Id;
	private String parent1Name;
	private String parent1Type;
	private String parent2DbId;
	private String parent2Id;
	private String parent2Name;
	private String parent2Type;
	private String pedigree;
	private List<String> siblings;

	public String getCrossingPlan()
		{ return crossingPlan; }

	public void setCrossingPlan(String crossingPlan)
		{ this.crossingPlan = crossingPlan; }

	public String getCrossingYear()
		{ return crossingYear; }

	public void setCrossingYear(String crossingYear)
		{ this.crossingYear = crossingYear; }

	public String getDefaultDisplayName()
		{ return defaultDisplayName; }

	public void setDefaultDisplayName(String defaultDisplayName)
		{ this.defaultDisplayName = defaultDisplayName; }

	public String getFamilyCode()
		{ return familyCode; }

	public void setFamilyCode(String familyCode)
		{ this.familyCode = familyCode; }

	public String getGermplasmDbId()
		{ return germplasmDbId; }

	public void setGermplasmDbId(String germplasmDbId)
		{ this.germplasmDbId = germplasmDbId; }

	public String getParent1DbId()
		{ return parent1DbId; }

	public void setParent1DbId(String parent1DbId)
		{ this.parent1DbId = parent1DbId; }

	public String getParent1Id()
		{ return parent1Id; }

	public void setParent1Id(String parent1Id)
		{ this.parent1Id = parent1Id; }

	public String getParent1Name()
		{ return parent1Name; }

	public void setParent1Name(String parent1Name)
		{ this.parent1Name = parent1Name; }

	public String getParent1Type()
		{ return parent1Type; }

	public void setParent1Type(String parent1Type)
		{ this.parent1Type = parent1Type; }

	public String getParent2DbId()
		{ return parent2DbId; }

	public void setParent2DbId(String parent2DbId)
		{ this.parent2DbId = parent2DbId; }

	public String getParent2Id()
		{ return parent2Id; }

	public void setParent2Id(String parent2Id)
		{ this.parent2Id = parent2Id; }

	public String getParent2Name()
		{ return parent2Name; }

	public void setParent2Name(String parent2Name)
		{ this.parent2Name = parent2Name; }

	public String getParent2Type()
		{ return parent2Type; }

	public void setParent2Type(String parent2Type)
		{ this.parent2Type = parent2Type; }

	public String getPedigree()
		{ return pedigree; }

	public void setPedigree(String pedigree)
		{ this.pedigree = pedigree; }

	public List<String> getSiblings()
		{ return siblings; }

	public void setSiblings(List<String> siblings)
		{ this.siblings = siblings; }
}