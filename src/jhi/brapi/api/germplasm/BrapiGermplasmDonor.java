package jhi.brapi.api.germplasm;

public class BrapiGermplasmDonor
{
	private String donorInstituteCode;
	private String donorAccessionNumber;
	private String donorGermplasmPUI;

	public String getDonorInstituteCode()
		{ return donorInstituteCode; }

	public void setDonorInstituteCode(String donorInstituteCode)
		{ this.donorInstituteCode = donorInstituteCode; }

	public String getDonorAccessionNumber()
		{ return donorAccessionNumber; }

	public void setDonorAccessionNumber(String donorAccessionNumber)
		{ this.donorAccessionNumber = donorAccessionNumber; }

	public String getDonorGermplasmPUI()
		{ return donorGermplasmPUI; }

	public void setDonorGermplasmPUI(String donorGermplasmPUI)
		{ this.donorGermplasmPUI = donorGermplasmPUI; }

	@Override
	public String toString()
	{
		return "BrapiGermplasmDonor{" +
			"donorInstituteCode='" + donorInstituteCode + '\'' +
			", donorAccessionNumber='" + donorAccessionNumber + '\'' +
			", donorGermplasmPUI='" + donorGermplasmPUI + '\'' +
			'}';
	}
}