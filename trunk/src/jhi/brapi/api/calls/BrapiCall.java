package jhi.brapi.api.calls;

import java.util.*;

public class BrapiCall
{
	public static final String DATATYPE_JSON = "json";
	public static final String DATATYPE_TSV = "tsv";
	public static final String DATATYPE_FLAPJACK = "flapjack";

	public static final String METHOD_GET = "GET";
	public static final String METHOD_POST = "POST";
	public static final String METHOD_PUT = "PUT";
	public static final String METHOD_DELETE = "DELETE";

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

	public BrapiCall withDatatypeJson()
	{
		return addDatatype(DATATYPE_JSON);
	}

	public BrapiCall withDatatypeTsv()
	{
		return addDatatype(DATATYPE_TSV);
	}

	public BrapiCall withDatatypeFlapjack()
	{
		return addDatatype(DATATYPE_FLAPJACK);
	}

	public BrapiCall withMethodGet()
	{
		return addMethod(METHOD_GET);
	}

	public BrapiCall withMethodPost()
	{
		return addMethod(METHOD_POST);
	}

	public BrapiCall withMethodPut()
	{
		return addMethod(METHOD_PUT);
	}

	public BrapiCall withMethodDelete()
	{
		return addMethod(METHOD_DELETE);
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