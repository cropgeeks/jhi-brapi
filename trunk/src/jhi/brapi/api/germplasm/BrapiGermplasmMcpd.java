package jhi.brapi.api.germplasm;

import java.util.*;

public class BrapiGermplasmMcpd extends BrapiGermplasm
{
	private String commonCropName;
	private String instituteCode;
	private String instituteName;
	private String biologicalStatusOfAccessionCode;
	private String countryOfOriginCode;
	private List<String> typeOfGermplasmStorageCode;
	private String genus;
	private String species;
	private String speciesAuthority;
	private String subtaxa;
	private String subtaxaAuthority;
	private List<BrapiGermplasmDonor> donors;
	private String acquisitionDate;

	public String getCommonCropName()
		{ return commonCropName; }

	public void setCommonCropName(String commonCropName)
		{ this.commonCropName = commonCropName; }

	public String getInstituteCode()
		{ return instituteCode; }

	public void setInstituteCode(String instituteCode)
		{ this.instituteCode = instituteCode; }

	public String getInstituteName()
		{ return instituteName; }

	public void setInstituteName(String instituteName)
		{ this.instituteName = instituteName; }

	public String getBiologicalStatusOfAccessionCode()
		{ return biologicalStatusOfAccessionCode; }

	public void setBiologicalStatusOfAccessionCode(String biologicalStatusOfAccessionCode)
		{ this.biologicalStatusOfAccessionCode = biologicalStatusOfAccessionCode; }

	public String getCountryOfOriginCode()
		{ return countryOfOriginCode; }

	public void setCountryOfOriginCode(String countryOfOriginCode)
		{ this.countryOfOriginCode = countryOfOriginCode; }

	public List<String> getTypeOfGermplasmStorageCode()
		{ return typeOfGermplasmStorageCode; }

	public void setTypeOfGermplasmStorageCode(List<String> typeOfGermplasmStorageCode)
		{ this.typeOfGermplasmStorageCode = typeOfGermplasmStorageCode; }

	public String getGenus()
		{ return genus; }

	public void setGenus(String genus)
		{ this.genus = genus; }

	public String getSpecies()
		{ return species; }

	public void setSpecies(String species)
		{ this.species = species; }

	public String getSpeciesAuthority()
		{ return speciesAuthority; }

	public void setSpeciesAuthority(String speciesAuthority)
		{ this.speciesAuthority = speciesAuthority; }

	public String getSubtaxa()
		{ return subtaxa; }

	public void setSubtaxa(String subtaxa)
		{ this.subtaxa = subtaxa; }

	public String getSubtaxaAuthority()
		{ return subtaxaAuthority; }

	public void setSubtaxaAuthority(String subtaxaAuthority)
		{ this.subtaxaAuthority = subtaxaAuthority; }

	public List<BrapiGermplasmDonor> getDonors()
		{ return donors; }

	public void setDonors(List<BrapiGermplasmDonor> donors)
		{ this.donors = donors; }

	public String getAcquisitionDate()
		{ return acquisitionDate; }

	public void setAcquisitionDate(String acquisitionDate)
		{ this.acquisitionDate = acquisitionDate; }

	@Override
	public String toString()
	{
		return "BrapiGermplasmMcpd{" +
				"germplasmDbId='" + getGermplasmDbId() + '\'' +
				", defaultDisplayName='" + getDefaultDisplayName() + '\'' +
				", germplasmName='" + getGermplasmName() + '\'' +
				", accessionNumber='" + getAccessionNumber() + '\'' +
				", germplasmPUI='" + getGermplasmPUI() + '\'' +
				", pedigree='" + getPedigree() + '\'' +
				", seedSource='" + getSeedSource() + '\'' +
				", synonyms=" + getSynonyms() +
				", commonCropName='" + commonCropName + '\'' +
				", instituteCode='" + instituteCode + '\'' +
				", instituteName='" + instituteName + '\'' +
				", biologicalStatusOfAccessionCode='" + biologicalStatusOfAccessionCode + '\'' +
				", countryOfOriginCode='" + countryOfOriginCode + '\'' +
				", typeOfGermplasmStorageCode=" + typeOfGermplasmStorageCode +
				", genus='" + genus + '\'' +
				", species='" + species + '\'' +
				", speciesAuthority='" + speciesAuthority + '\'' +
				", subtaxa='" + subtaxa + '\'' +
				", subtaxaAuthority='" + subtaxaAuthority + '\'' +
				", donors=" + donors +
				", acquisitionDate='" + acquisitionDate + '\'' +
				'}';
	}
}