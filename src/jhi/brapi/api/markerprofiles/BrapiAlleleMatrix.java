package jhi.brapi.api.markerprofiles;

import java.util.*;

public class BrapiAlleleMatrix
{
	private List<List<String>> data;

	public List<List<String>> getData()
		{ return data; }

	public void setData(List<List<String>> data)
		{ this.data = data; }

	public String markerId(int line)
	{
		return data.get(line).get(0);
	}

	public String markerProfileId(int line)
	{
		return data.get(line).get(1);
	}

	public String allele(int line)
	{
		return data.get(line).get(2);
	}
}