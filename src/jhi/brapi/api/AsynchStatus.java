package jhi.brapi.api;

import java.sql.*;

public class AsynchStatus
{
	private String asynchId;
	private String status;
	private Date startTime;
	private Date endTime;
	private int percentComplete;

	public String getAsynchId()
		{ return asynchId; }

	public void setAsynchId(String asynchId)
		{ this.asynchId = asynchId; }

	public String getStatus()
		{ return status; }

	public void setStatus(String status)
		{ this.status = status; }

	public Date getStartTime()
		{ return startTime; }

	public void setStartTime(Date startTime)
		{ this.startTime = startTime; }

	public Date getEndTime()
		{ return endTime; }

	public void setEndTime(Date endTime)
		{ this.endTime = endTime; }

	public int getPercentComplete()
		{ return percentComplete; }

	public void setPercentComplete(int percentComplete)
		{ this.percentComplete = percentComplete; }
}
