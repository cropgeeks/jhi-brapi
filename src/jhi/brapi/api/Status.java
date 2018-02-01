package jhi.brapi.api;

public class Status
{
	private String code;

	private String message;

	public Status()
	{
	}

	public Status(String code, String message)
	{
		this.code = code;
		this.message = message;
	}

	public String getMessage()
		{ return message; }

	public void setMessage(String message)
		{ this.message = message; }

	public String getCode()
		{ return code; }

	public void setCode(String code)
		{ this.code = code; }

	@Override
	public String toString()
	{
		return code + " : " + message;
	}
}