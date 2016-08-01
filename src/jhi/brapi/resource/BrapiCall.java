package jhi.brapi.resource;

import java.util.*;

/**
 * @author Sebastian Raubach
 */
public class BrapiCall
{
	private String       call;
	private List<String> datatypes = new ArrayList<>();
	// HTTP methods (e.g. POST, GET, etc)
	private List<String> methods = new ArrayList<>();

	public BrapiCall()
	{
	}

	public BrapiCall(String call)
	{
		this.call = call;
	}

	public String getCall()
	{
		return call;
	}

	public void setCall(String call)
	{
		this.call = call;
	}

	public List<String> getDatatypes()
	{
		return datatypes;
	}

	public void setDatatypes(List<String> datatypes)
	{
		this.datatypes = datatypes;
	}

	public BrapiCall addDatatype(String dataType)
	{
		datatypes.add(dataType);
		return this;
	}

	public List<String> getMethods()
	{
		return methods;
	}

	public void setMethods(List<String> methods)
	{
		this.methods = methods;
	}

	public BrapiCall addMethod(String method)
	{
		methods.add(method);
		return this;
	}

	@Override
	public String toString()
	{
		return "BrapiCall{" +
				"call='" + call + '\'' +
				'}';
	}
}
