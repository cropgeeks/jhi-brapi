package jhi.brapi.api.traits;

import jhi.brapi.api.*;

public class TraitDAO
{
	private String getTraits = "select phenotypes.id, phenotypes.name, phenotypes.description, units.unit_name from phenotypes LEFT JOIN units ON phenotypes.unit_id = units.id";
	private String getTrait = "select phenotypes.id, phenotypes.name, phenotypes.description, units.unit_name from phenotypes LEFT JOIN units ON phenotypes.unit_id = units.id where phenotypes.id=?";

	/**
	 * Return all BrapiTrait objects. Uses the getTraits query defined above to query the database.
	 *
	 * @return	A BrapiTraitList object with is a wrapper around a List of BrapiTrait objects.
	 */
	public BrapiListResource<BrapiTrait> getAll()
	{
		BrapiListResource<BrapiTrait> allTraits = new BrapiListResource<BrapiTrait>();

		return allTraits;
	}

	/**
	 * Return the BrapiTrait object identified by the supplied id. Uses the getTrait query above to query the database.
	 *
	 * @param id	The id of the BrapiTrait object to be retrieved.
	 * @return		A BrapiTrait object representing the trait identified by the supplied id.
	 */
	public BrapiBaseResource<BrapiTrait> getById(int id)
	{
		return new BrapiBaseResource<BrapiTrait>();
	}
}