package jhi.brapi.api;

public class DataFile
{
	// A human readable description of the file contents
	private String fileDescription;
	// The MD5 Hash of the file contents to be used as a check sum
	private String fileMD5Hash;
	private String fileName;
	// The size of the file in bytes
	private int fileSize;
	// The type or format of the file. Preferably MIME Type.
	private String fileType;
	//The absolute URL where the file is located
	private String fileURL;

	public DataFile()
	{
	}

	public DataFile(String fileDescription, String fileMD5Hash, String fileName, int fileSize, String fileType, String fileURL)
	{
		this.fileDescription = fileDescription;
		this.fileMD5Hash = fileMD5Hash;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.fileType = fileType;
		this.fileURL = fileURL;
	}

	public String getFileDescription()
		{ return fileDescription; }

	public void setFileDescription(String fileDescription)
		{ this.fileDescription = fileDescription; }

	public String getFileMD5Hash()
		{ return fileMD5Hash; }

	public void setFileMD5Hash(String fileMD5Hash)
		{ this.fileMD5Hash = fileMD5Hash; }

	public String getFileName()
		{ return fileName; }

	public void setFileName(String fileName)
		{ this.fileName = fileName; }

	public int getFileSize()
		{ return fileSize; }

	public void setFileSize(int fileSize)
		{ this.fileSize = fileSize; }

	public String getFileType()
		{ return fileType; }

	public void setFileType(String fileType)
		{ this.fileType = fileType; }

	public String getFileURL()
		{ return fileURL; }

	public void setFileURL(String fileURL)
		{ this.fileURL = fileURL; }
}
