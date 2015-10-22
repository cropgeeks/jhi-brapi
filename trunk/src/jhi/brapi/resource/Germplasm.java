package jhi.brapi.resource;

import java.util.*;

/**
 * @author Sebastian Raubach
 */

// Exclude non-null fields from the output
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Germplasm
{
	private String       germplasmId;
	private String       germplasmPUI;
	private String       germplasmName;
	private String       accessionNumber;
	private String       breederCode;
	private List<String> synonyms;
	private String       commonCropName;
	private String       instituteCode;
	private String       instituteName;
	private String       biologicalStatusOfGermplasmCode;
	private String       countryOfOriginCode;
	private List<String> typeOfGermplasmStorageCode;
	private String       genus;
	private String       species;
	private String       speciesAuthority;
	private String       subtaxa;
	private String       subtaxaAuthority;
	private List<Donor>  donors;
	private String       acquisitionDate;

	public String getGermplasmId()
	{
		return germplasmId;
	}

	public void setGermplasmId(String germplasmId)
	{
		this.germplasmId = germplasmId;
	}

	public String getGermplasmPUI()
	{
		return germplasmPUI;
	}

	public void setGermplasmPUI(String germplasmPUI)
	{
		this.germplasmPUI = germplasmPUI;
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

	public String getBreederCode()
	{
		return breederCode;
	}

	public void setBreederCode(String breederCode)
	{
		this.breederCode = breederCode;
	}

	public List<String> getSynonyms()
	{
		return synonyms;
	}

	public void setSynonyms(List<String> synonyms)
	{
		this.synonyms = synonyms;
	}

	public String getCommonCropName()
	{
		return commonCropName;
	}

	public void setCommonCropName(String commonCropName)
	{
		this.commonCropName = commonCropName;
	}

	public String getInstituteCode()
	{
		return instituteCode;
	}

	public void setInstituteCode(String instituteCode)
	{
		this.instituteCode = instituteCode;
	}

	public String getInstituteName()
	{
		return instituteName;
	}

	public void setInstituteName(String instituteName)
	{
		this.instituteName = instituteName;
	}

	public String getBiologicalStatusOfGermplasmCode()
	{
		return biologicalStatusOfGermplasmCode;
	}

	public void setBiologicalStatusOfGermplasmCode(String biologicalStatusOfGermplasmCode)
	{
		this.biologicalStatusOfGermplasmCode = biologicalStatusOfGermplasmCode;
	}

	public String getCountryOfOriginCode()
	{
		return countryOfOriginCode;
	}

	public void setCountryOfOriginCode(String countryOfOriginCode)
	{
		this.countryOfOriginCode = countryOfOriginCode;
	}

	public List<String> getTypeOfGermplasmStorageCode()
	{
		return typeOfGermplasmStorageCode;
	}

	public void setTypeOfGermplasmStorageCode(List<String> typeOfGermplasmStorageCode)
	{
		this.typeOfGermplasmStorageCode = typeOfGermplasmStorageCode;
	}

	public String getGenus()
	{
		return genus;
	}

	public void setGenus(String genus)
	{
		this.genus = genus;
	}

	public String getSpecies()
	{
		return species;
	}

	public void setSpecies(String species)
	{
		this.species = species;
	}

	public String getSpeciesAuthority()
	{
		return speciesAuthority;
	}

	public void setSpeciesAuthority(String speciesAuthority)
	{
		this.speciesAuthority = speciesAuthority;
	}

	public String getSubtaxa()
	{
		return subtaxa;
	}

	public void setSubtaxa(String subtaxa)
	{
		this.subtaxa = subtaxa;
	}

	public String getSubtaxaAuthority()
	{
		return subtaxaAuthority;
	}

	public void setSubtaxaAuthority(String subtaxaAuthority)
	{
		this.subtaxaAuthority = subtaxaAuthority;
	}

	public List<Donor> getDonors()
	{
		return donors;
	}

	public void setDonors(List<Donor> donors)
	{
		this.donors = donors;
	}

	public String getAcquisitionDate()
	{
		return acquisitionDate;
	}

	public void setAcquisitionDate(String acquisitionDate)
	{
		this.acquisitionDate = acquisitionDate;
	}

	@Override
	public String toString()
	{
		return "Germplasm{" +
				"germplasmId='" + germplasmId + '\'' +
				", germplasmPUI='" + germplasmPUI + '\'' +
				", germplasmName='" + germplasmName + '\'' +
				", accessionNumber='" + accessionNumber + '\'' +
				", breederCode='" + breederCode + '\'' +
				", synonyms=" + synonyms +
				", commonCropName='" + commonCropName + '\'' +
				", instituteCode='" + instituteCode + '\'' +
				", instituteName='" + instituteName + '\'' +
				", biologicalStatusOfGermplasmCode='" + biologicalStatusOfGermplasmCode + '\'' +
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