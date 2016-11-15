package jhi.brapi.util;

public class GenotypeEncodingParams
{
	private boolean collapse = true;
	private String unknownString = "N";
	private String sepPhased = "|";
	private String sepUnphased = "/";

	public GenotypeEncodingParams()
	{
	}

	public GenotypeEncodingParams(boolean collapse, String unknownString, String sepPhased, String sepUnphased)
	{
		this.collapse = collapse;
		this.unknownString = unknownString;
		this.sepPhased = sepPhased;
		this.sepUnphased = sepUnphased;
	}

	public boolean isCollapse()
		{ return collapse; }

	public void setCollapse(boolean collapse)
		{ this.collapse = collapse; }

	public String getUnknownString()
		{ return unknownString; }

	public void setUnknownString(String unknownString)
		{ this.unknownString = unknownString; }

	public String getSepPhased()
		{ return sepPhased; }

	public void setSepPhased(String sepPhased)
		{ this.sepPhased = sepPhased; }

	public String getSepUnphased()
		{ return sepUnphased; }

	public void setSepUnphased(String sepUnphased)
		{ this.sepUnphased = sepUnphased; }
}