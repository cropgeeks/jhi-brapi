package jhi.brapi.api;

public class Status
{
	private String messageType;

	private String message;

	public Status()
	{
	}

	public Status(String messageType, String message)
	{
		this.messageType = messageType;
		this.message = message;
	}

	public String getMessage()
		{ return message; }

	public void setMessage(String message)
		{ this.message = message; }

	public String getMessageType()
		{ return messageType; }

	public void setMessageType(String messageType)
		{ this.messageType = messageType; }

	@Override
	public String toString()
	{
		return messageType + " : " + message;
	}
}