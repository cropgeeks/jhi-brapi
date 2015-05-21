package hutton.brapi.data;

import hutton.brapi.resource.*;

/**
 * Specifies the public interface which any Germplasm data accessing classes must implement.
 */
public interface GermplasmDAO
{
	/**
	 * Returns all of the Germplasm objects.
	 *
	 * @return	A GermplasmList object, which is a wrapper around a List of Germplasm objects.
	 */
	GermplasmList getAll();

	// Return the Germplasm identified by id

	/**
	 * Return a specific Germplasm specified by the supplied id.
	 *
	 * @param id	The id of the Germplasm to getJson.
	 * @return		A Germplasm object identified by the supplied id (or null if none exists).
	 */
	Germplasm getById(int id);

	// Return the list of MarkerProfile ids that relate to this Germplasm

	/**
	 * Return the list of MarkerProfile ids that relate to this Germplasm in the form of a GermplasmMarkerProfileList.
	 *
	 * @param id	The id of the Germplasm for which MarkerProfile ids are being queried.
	 * @return		A GermplasmMarkerProfileList object identified by the supplied id (or null if none exists).
	 */
	GermplasmMarkerProfileList getMarkerProfilesFor(int id);
}
