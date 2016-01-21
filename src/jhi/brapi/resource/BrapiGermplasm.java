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
	private String       germplasmPUI;
	private String       germplasmName;
	private String       defaultDisplayName;
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
	private String       pedigree;
	private String       seedSource;

	public String getGermplasmDbId()
	{
		return germplasmDbId;
	}

	public void setGermplasmDbId(String germplasmDbId)
	{
		this.germplasmDbId = germplasmDbId;
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

	public String getDefaultDisplayName()
	{
		return defaultDisplayName;
	}

	public void setDefaultDisplayName(String defaultDisplayName)
	{
		this.defaultDisplayName = defaultDisplayName;
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

	@Override
	public String toString()
	{
		return "BrapiGermplasm{" +
				"germplasmDbId='" + germplasmDbId + '\'' +
				", germplasmPUI='" + germplasmPUI + '\'' +
				", germplasmName='" + germplasmName + '\'' +
				", defaultDisplayName='" + defaultDisplayName + '\'' +
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
				", pedigree='" + pedigree + '\'' +
				", seedSource='" + seedSource + '\'' +
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