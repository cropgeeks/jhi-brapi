package jhi.brapi.api.markerprofiles;

public class MarkerProfilesSearchParams
{
	private String germplasm;
	private String study;
	private String sample;
	private String extract;
	private String method;

	public MarkerProfilesSearchParams()
	{
	}

	public MarkerProfilesSearchParams(String germplasm, String study, String sample, String extract, String method)
	{
		this.germplasm = germplasm;
		this.study = study;
		this.sample = sample;
		this.extract = extract;
		this.method = method;
	}

	public String getGermplasm()
		{ return germplasm; }

	public void setGermplasm(String germplasm)
		{ this.germplasm = germplasm; }

	public String getStudy()
		{ return study; }

	public void setStudy(String study)
		{ this.study = study; }

	public String getSample()
		{ return sample; }

	public void setSample(String sample)
		{ this.sample = sample; }

	public String getExtract()
		{ return extract; }

	public void setExtract(String extract)
		{ this.extract = extract; }

	public String getMethod()
		{ return method; }

	public void setMethod(String method)
		{ this.method = method; }
}