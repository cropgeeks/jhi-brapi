package jhi.brapi.api.calls;

import java.util.*;

public class BrapiCall
{
	public static final String DATATYPE_JSON = "application/json";
	public static final String DATATYPE_TSV = "text/tsv";
	public static final String DATATYPE_FLAPJACK = "application/flapjack";

	public static final String METHOD_GET = "GET";
	public static final String METHOD_POST = "POST";
	public static final String METHOD_PUT = "PUT";
	public static final String METHOD_DELETE = "DELETE";

	public static final String VERSION_ONE_ZERO = "1.0";
	public static final String VERSION_ONE_ONE = "1.1";
	public static final String VERSION_ONE_TWO = "1.2";
	public static final String VERSION_ONE_THREE = "1.3";

	private String call;
	private List<String> dataTypes = new ArrayList<>();
	// HTTP methods (e.g. POST, GET, etc)
	private List<String> methods = new ArrayList<>();
	private List<String> versions = new ArrayList<>();

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

	public BrapiCall addDataType(String dataType)
	{
		dataTypes.add(dataType);
		return this;
	}

	public BrapiCall addVersion(String version)
	{
		versions.add(version);
		return this;
	}

	public BrapiCall withDatatypeJson()
	{
		return addDataType(DATATYPE_JSON);
	}

	public BrapiCall withDatatypeTsv()
	{
		return addDataType(DATATYPE_TSV);
	}

	public BrapiCall withDatatypeFlapjack()
	{
		return addDataType(DATATYPE_FLAPJACK);
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

	public BrapiCall withVersionOneZero()
		{ return addVersion(VERSION_ONE_ZERO); }

	public BrapiCall withVersionOneOne()
		{ return addVersion(VERSION_ONE_ONE); }

	public BrapiCall withVersionOneTwo()
		{ return addVersion(VERSION_ONE_TWO); }

	public BrapiCall withVersionOneThree()
		{ return addVersion(VERSION_ONE_THREE); }

	public boolean hasDataType(String dataType)
	{
		return dataTypes.stream()
			.filter(d -> d.equalsIgnoreCase(dataType))
			.count() >= 1;
	}

	public boolean hasMethod(String method)
	{
		return methods.stream()
			.filter(m -> m.equalsIgnoreCase(method))
			.count() >= 1;
	}

	public boolean hasVersion(String version)
	{
		return versions.stream()
			.filter(v -> v.equalsIgnoreCase(version))
			.count() >= 1;
	}

	public String getCall()
		{ return call; }

	public void setCall(String call)
		{ this.call = call; }

	public List<String> getDataTypes()
		{ return dataTypes; }

	public void setDataTypes(List<String> dataTypes)
		{ this.dataTypes = dataTypes; }

	public List<String> getDatatypes()
		{ return dataTypes; }

	public void setDatatypes(List<String> datatypes)
		{ this.dataTypes = dataTypes; }

	public List<String> getMethods()
	 { return methods; }

	public void setMethods(List<String> methods)
		{ this.methods = methods; }

	public List<String> getVersions()
		{ return versions; }

	public void setVersions(List<String> versions)
		{ this.versions = versions; }

	@Override
	public String toString()
	{
		return "BrapiCall{" +
				"call='" + call + '\'' +
				'}';
	}
}