package jhi.brapi.api.genotyping.variantsets;

public class Format
{
	private String dataFormat = "";
	private String fileFormat = "";
	private String fileUrl = "";

	public Format()
	{
	}

	public String getDataFormat()
	{
		return dataFormat;
	}

	public void setDataFormat(String dataFormat)
	{
		this.dataFormat = dataFormat;
	}

	public String getFileFormat()
	{
		return fileFormat;
	}

	public void setFileFormat(String fileFormat)
	{
		this.fileFormat = fileFormat;
	}

	public String getFileUrl()
	{
		return fileUrl;
	}

	public void setFileUrl(String fileUrl)
	{
		this.fileUrl = fileUrl;
	}
}
