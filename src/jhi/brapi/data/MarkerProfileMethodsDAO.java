package jhi.brapi.data;

import jhi.brapi.resource.*;

public interface MarkerProfileMethodsDAO
{
	/**
	 * Returns all of the Method objects.
	 *
	 * @return	A MarkerProfileMethodList object, which is a wrapper around a List of Method objects.
	 */
	MarkerProfileMethodList getAll();
}
