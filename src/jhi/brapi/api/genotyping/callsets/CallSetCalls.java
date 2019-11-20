package jhi.brapi.api.genotyping.callsets;

import jhi.brapi.api.*;

import java.util.*;

public class CallSetCalls implements BrapiDetailResource<CallSetCallsDetail>
{
	private List<CallSetCallsDetail> data;

	private boolean expandHomozygotes = false;
	private String sepPhased = "|";
	private String sepUnphased = "/";
	private String unknownString = "";

	@Override
	public List<CallSetCallsDetail> getData()
	{
		return data;
	}

	@Override
	public void setData(List<CallSetCallsDetail> data)
	{
		this.data = data;
	}

	public boolean isExpandHomozygotes()
	{
		return expandHomozygotes;
	}

	public void setExpandHomozygotes(boolean expandHomozygotes)
	{
		this.expandHomozygotes = expandHomozygotes;
	}

	public String getSepPhased()
	{
		return sepPhased;
	}

	public void setSepPhased(String sepPhased)
	{
		this.sepPhased = sepPhased;
	}

	public String getSepUnphased()
	{
		return sepUnphased;
	}

	public void setSepUnphased(String sepUnphased)
	{
		this.sepUnphased = sepUnphased;
	}

	public String getUnknownString()
	{
		return unknownString;
	}

	public void setUnknownString(String unknownString)
	{
		this.unknownString = unknownString;
	}
}
