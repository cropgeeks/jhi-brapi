package jhi.brapi.api.calls;

import java.util.*;

public class BrapiCall
{
	private String call;
	private List<String> datatypes = new ArrayList<>();
	// HTTP methods (e.g. POST, GET, etc)
	private List<String> methods = new ArrayList<>();

	public BrapiCall() {}

	public BrapiCall(String call)
	{
		this.call = call;
	}

	public BrapiCall addMethod(String method)
	{
		methods.add(method);
		return this;
	}

	public BrapiCall addDatatype(String dataType)
	{
		datatypes.add(dataType);
		return this;
	}

	public boolean hasDataType(String dataType)
	{
		return datatypes.stream()
			.filter(d -> d.equalsIgnoreCase(dataType))
			.count() >= 1;
	}

	public boolean hasMethod(String method)
	{
		return methods.stream()
			.filter(d -> d.equalsIgnoreCase(method))
			.count() >= 1;
	}

	public String getCall()
		{ return call; }

	public void setCall(String call)
		{ this.call = call; }

	public List<String> getDatatypes()
		{ return datatypes; }

	public void setDatatypes(List<String> datatypes)
		{ this.datatypes = datatypes; }

	public List<String> getMethods()
	 { return methods; }

	public void setMethods(List<String> methods)
		{ this.methods = methods; }

	@Override
	public String toString()
	{
		return "BrapiCall{" +
				"call='" + call + '\'' +
				'}';
	}
}