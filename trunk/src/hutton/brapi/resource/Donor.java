package hutton.brapi.resource;

/**
 * @author Sebastian Raubach
 */
public class Donor
{
	private String donorInstituteCode;
	private String donorAccessionNumber;

	public String getDonorInstituteCode()
	{
		return donorInstituteCode;
	}

	public void setDonorInstituteCode(String donorInstituteCode)
	{
		this.donorInstituteCode = donorInstituteCode;
	}

	public String getDonorAccessionNumber()
	{
		return donorAccessionNumber;
	}

	public void setDonorAccessionNumber(String donorAccessionNumber)
	{
		this.donorAccessionNumber = donorAccessionNumber;
	}

	@Override
	public String toString()
	{
		return "Donor{" +
				"donorInstituteCode='" + donorInstituteCode + '\'' +
				", donorAccessionNumber='" + donorAccessionNumber + '\'' +
				'}';
	}
}
