package jhi.brapi.data;

import jhi.brapi.resource.*;

/**
 * Created by gs40939 on 19/05/2015.
 */
public interface MarkerProfileDAO
{
	MarkerProfileList getAll();

	/**
	 * Return a specific Map specified by the supplied id.
	 *
	 * @param id	The id of the MarkerProfile to getJson
	 * @return		A MarkerProfile object identified by id (or null if none exists).
	 */
	MarkerProfileData getById(String id);

	/**
	 * Return a count of the Markers which are contained within the MarkerProfile in the form of a MarkerProfileCount.
	 *
	 * @param id	The id of the MarkerProfile for which a count is desired.
	 * @return		The MarkerProfileCount for the MarkerProfile with the given id.
	 */
	MarkerProfileCount getCountById(String id);
}
