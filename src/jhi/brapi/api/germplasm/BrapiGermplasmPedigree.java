package jhi.brapi.api.germplasm;

import java.util.*;

public class BrapiGermplasmPedigree
{
	private String germplasmDbId;
	private String defaultDisplayName;
	private String pedigree;
	private String crossingPlan;
	private String crossingYear;
	private String familyCode;
	private String parent1DbId;
	private String parent1Name;
	private String parent1Type;
	private String parent2DbId;
	private String parent2Name;
	private String parent2Type;
	private List<String> siblings;

	public String getGermplasmDbId()
		{ return germplasmDbId; }

	public void setGermplasmDbId(String germplasmDbId)
		{ this.germplasmDbId = germplasmDbId; }

	public String getDefaultDisplayName()
		{ return defaultDisplayName; }

	public void setDefaultDisplayName(String defaultDisplayName)
		{ this.defaultDisplayName = defaultDisplayName; }

	public String getPedigree()
		{ return pedigree; }

	public void setPedigree(String pedigree)
		{ this.pedigree = pedigree; }

	public String getParent1DbId()
		{ return parent1DbId; }

	public void setParent1DbId(String parent1DbId)
		{ this.parent1DbId = parent1DbId; }

	public String getParent2DbId()
		{ return parent2DbId; }

	public void setParent2DbId(String parent2DbId)
		{ this.parent2DbId = parent2DbId; }

	public String getCrossingPlan()
		{ return crossingPlan; }

	public void setCrossingPlan(String crossingPlan)
		{ this.crossingPlan = crossingPlan; }

	public String getCrossingYear()
		{ return crossingYear; }

	public void setCrossingYear(String crossingYear)
		{ this.crossingYear = crossingYear; }

	public String getFamilyCode()
		{ return familyCode; }

	public void setFamilyCode(String familyCode)
		{ this.familyCode = familyCode; }

	public String getParent1Name()
		{ return parent1Name; }

	public void setParent1Name(String parent1Name)
		{ this.parent1Name = parent1Name; }

	public String getParent1Type()
		{ return parent1Type; }

	public void setParent1Type(String parent1Type)
		{ this.parent1Type = parent1Type; }

	public String getParent2Name()
		{ return parent2Name; }

	public void setParent2Name(String parent2Name)
		{ this.parent2Name = parent2Name; }

	public String getParent2Type()
		{ return parent2Type; }

	public void setParent2Type(String parent2Type)
		{ this.parent2Type = parent2Type; }

	public List<String> getSiblings()
		{ return siblings; }

	public void setSiblings(List<String> siblings)
		{ this.siblings = siblings; }
}