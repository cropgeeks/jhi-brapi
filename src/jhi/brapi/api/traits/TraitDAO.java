package jhi.brapi.api.traits;

import java.sql.*;
import java.util.*;

import jhi.brapi.util.*;

public class TraitDAO
{
	private String getTraits = "select phenotypes.id, phenotypes.name, phenotypes.description, units.unit_name from phenotypes LEFT JOIN units ON phenotypes.unit_id = units.id";
	private String getTrait = "select phenotypes.id, phenotypes.name, phenotypes.description, units.unit_name from phenotypes LEFT JOIN units ON phenotypes.unit_id = units.id where phenotypes.id=?";

	/**
	 * Return all BrapiTrait objects. Uses the getTraits query defined above to query the database.
	 *
	 * @return	A BrapiTraitList object with is a wrapper around a List of BrapiTrait objects.
	 */
	public BrapiTraitList getAll()
	{
		BrapiTraitList allTraits = new BrapiTraitList();

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = con.prepareStatement(getTraits);
			 ResultSet resultSet = statement.executeQuery())
		{
			List<BrapiTrait> traitList = new ArrayList<>();

			while (resultSet.next())
			{
				// Set the ServerGermplasmSearch bean using the data returned from the database
				BrapiTrait trait = new BrapiTrait();
				trait.setTraitId(resultSet.getString("id"));
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
	 * Return the BrapiTrait object identified by the supplied id. Uses the getTrait query above to query the database.
	 *
	 * @param id	The id of the BrapiTrait object to be retrieved.
	 * @return		A BrapiTrait object representing the trait identified by the supplied id.
	 */
	public BrapiTrait getById(int id)
	{
		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = createByIdStatement(con, getTrait, id);
			 ResultSet resultSet = statement.executeQuery())
		{
			if (resultSet.first())
			{
				// Set the ServerGermplasmSearch bean using the data returned from the database
				BrapiTrait trait = new BrapiTrait();
				trait.setTraitId(resultSet.getString("id"));
				trait.setName(resultSet.getString("name"));
				trait.setMethod(resultSet.getString("description"));

				return trait;
			}
		}
		catch (SQLException e) { e.printStackTrace(); }

		return null;
	}

	private PreparedStatement createByIdStatement(Connection con, String query, int id)
		throws SQLException
	{
		// Prepare statement with ID from URI
		PreparedStatement statement = con.prepareStatement(query);
		statement.setInt(1, id);

		return statement;
	}
}