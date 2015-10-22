package jhi.brapi.resource;

import java.util.*;

import com.fasterxml.jackson.annotation.*;

public class MarkerProfileMethodList
{
	private List<MarkerProfileMethod> methods;

	public List<MarkerProfileMethod> getMethods()
	{
		return methods;
	}

	public void setMethods(List<MarkerProfileMethod> methods)
	{
		this.methods = methods;
	}
}