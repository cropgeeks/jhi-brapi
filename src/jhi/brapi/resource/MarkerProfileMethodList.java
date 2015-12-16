package jhi.brapi.resource;

import java.util.*;

import com.fasterxml.jackson.annotation.*;

public class MarkerProfileMethodList
{
	private List<BrapiMarkerProfileMethod> methods;

	public List<BrapiMarkerProfileMethod> getMethods()
	{
		return methods;
	}

	public void setMethods(List<BrapiMarkerProfileMethod> methods)
	{
		this.methods = methods;
	}
}