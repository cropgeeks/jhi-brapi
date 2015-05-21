package hutton.brapi.data;

import java.sql.*;
import java.util.*;

import hutton.brapi.resource.*;

/**
 * Created by gs40939 on 19/05/2015.
 */
public class TraitDAOImpl implements TraitDAO
{
	private String getTraits = "select phenotypes.id, phenotypes.name, phenotypes.description, units.unit_name from phenotypes LEFT JOIN units ON phenotypes.unit_id = units.id";
	private String getTrait = "select phenotypes.id, phenotypes.name, phenotypes.description, units.unit_name from phenotypes LEFT JOIN units ON phenotypes.unit_id = units.id where phenotypes.id=?";

	/**
	 * Return all Trait objects. Uses the getTraits query defined above to query the database.
	 *
	 * @return	A TraitList object with is a wrapper around a List of Trait objects.
	 */
	@Override
	public TraitList getAll()
	{
		TraitList allTraits = new TraitList();

		try (Connection con = Database.INSTANCE.getDataSource().getConnection())
		{
			// Prepare statement with ID from URI
			PreparedStatement statement = con.prepareStatement(getTraits);

			// Get the first result returned from the database
			ResultSet resultSet = statement.executeQuery();

			List<Trait> traitList = new ArrayList<>();

			while (resultSet.next())
			{
				// Set the Germplasm bean using the data returned from the database
				Trait trait = new Trait();
				trait.setId(resultSet.getString("id"));
				trait.setName(resultSet.getString("name"));
				trait.setMethod(resultSet.getString("description"));
				trait.setMethod(resultSet.getString("unit_name"));

				traitList.add(trait);
			}
			allTraits.setTraits(traitList);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return allTraits;
	}

	/**
	 * Return the Trait object identified by the supplied id. Uses the getTrait query above to query the database.
	 *
	 * @param id	The id of the Trait object to be retrieved.
	 * @return		A Trait object representing the trait identified by the supplied id.
	 */
	@Override
	public Trait getById(int id)
	{
		try (Connection con = Database.INSTANCE.getDataSource().getConnection())
		{
			// Prepare statement with ID from URI
			PreparedStatement statement = con.prepareStatement(getTrait);
			statement.setInt(1, id);

			// Get the first result returned from the database
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.first())
			{
				// Set the Germplasm bean using the data returned from the database
				Trait trait = new Trait();
				trait.setId(resultSet.getString("id"));
				trait.setName(resultSet.getString("name"));
				trait.setMethod(resultSet.getString("description"));

				return trait;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
