package hutton.brapi.resource;

import java.util.*;

import com.fasterxml.jackson.annotation.*;

public class MarkerProfileMethodList
{
	private List<MarkerProfileMethod> methods;

	public MarkerProfileMethodList()
	{
	}

	// JsonCreator annotation specifies the method used by Jackson to deserialize from JSON to Java.
	@JsonCreator
	public MarkerProfileMethodList(List<MarkerProfileMethod> methods)
	{
		this.methods = methods;
	}

	// JsonValue annotation specifies that the value of germplasm should be used (i.e. it won't be wrapped in a Germplasm JSON object)
	@JsonValue
	public List<MarkerProfileMethod> getMethods()
	{
		return methods;
	}

	public void setMethods(List<MarkerProfileMethod> methods)
	{
		this.methods = methods;
	}
}