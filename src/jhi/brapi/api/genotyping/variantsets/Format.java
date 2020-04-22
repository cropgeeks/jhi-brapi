package jhi.brapi.api.genotyping.variantsets;

public class Format
{
	private String dataFormat = "";
	private String fileFormat = "";
	private String fileURL = "";

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

	public String getFileURL()
	{
		return fileURL;
	}

	public void setFileURL(String fileURL)
	{
		this.fileURL = fileURL;
	}
}
