package hutton.brapi.data;

import hutton.brapi.resource.*;

/**
 * Created by gs40939 on 19/05/2015.
 */
public interface TraitDAO
{
	/**
	 * Return all Trait objects.
	 *
	 * @return	A TraitList object with is a wrapper around a List of Trait objects.
	 */
	TraitList getAll();

	/**
	 * Return the Trait object identified by the supplied id.
	 *
	 * @param id	The id of the Trait object to be retrieved.
	 * @return		A Trait object representing the trait identified by the supplied id.
	 */
	Trait getById(int id);
}
