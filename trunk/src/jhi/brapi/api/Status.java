package jhi.brapi.api;

public class Status
{
	private String code;

	private String message;

	public Status()
	{
	}

	public String getMessage()
		{ return message; }

	public void setMessage(String message)
		{ this.message = message; }

	public String getCode()
		{ return code; }

	public void setCode(String code)
		{ this.code = code; }
}