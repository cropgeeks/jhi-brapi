package hutton.brapi.data;

import hutton.brapi.resource.*;

/**
 * Specifies the public interface which any Map data accessing classes must implement.
 */
public interface MapDAO
{
	/**
	 * Return all Maps.
	 *
	 * @return	A MapList object which is effectively a wrapper around a List of Map objects.
	 */
	MapList getAll();

	/**
	 * Return a specific Map specified by the supplied id.
	 *
	 * @param id	The id of the Map to getJson
	 * @return		A MapDetail object which represents the Map we wish to getJson (or null if none exists).
	 */
	MapDetail getById(int id);

	MapDetail getByIdAndChromosome(int id, String chromosome);
}
