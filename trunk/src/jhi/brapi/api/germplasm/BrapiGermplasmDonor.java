package jhi.brapi.api.germplasm;

public class BrapiGermplasmDonor
{
	private String donorInstituteCode;
	private String donorAccessionNumber;

	public String getDonorInstituteCode()
		{ return donorInstituteCode; }

	public void setDonorInstituteCode(String donorInstituteCode)
		{ this.donorInstituteCode = donorInstituteCode; }

	public String getDonorAccessionNumber()
		{ return donorAccessionNumber; }

	public void setDonorAccessionNumber(String donorAccessionNumber)
		{ this.donorAccessionNumber = donorAccessionNumber; }

	@Override
	public String toString()
	{
		return "BrapiGermplasmDonor{" +
				"donorInstituteCode='" + donorInstituteCode + '\'' +
				", donorAccessionNumber='" + donorAccessionNumber + '\'' +
				'}';
	}
}