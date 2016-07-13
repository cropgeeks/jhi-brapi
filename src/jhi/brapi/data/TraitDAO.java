package jhi.brapi.data;

import jhi.brapi.resource.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gs40939 on 19/05/2015.
 */
public class TraitDAO
{
	private String getTraits = "select phenotypes.id, phenotypes.name, phenotypes.description, units.unit_name from phenotypes LEFT JOIN units ON phenotypes.unit_id = units.id";
	private String getTrait = "select phenotypes.id, phenotypes.name, phenotypes.description, units.unit_name from phenotypes LEFT JOIN units ON phenotypes.unit_id = units.id where phenotypes.id=?";

	/**
	 * Return all Trait objects. Uses the getTraits query defined above to query the database.
	 *
	 * @return	A TraitList object with is a wrapper around a List of Trait objects.
	 */
	public TraitList getAll()
	{
		TraitList allTraits = new TraitList();

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = con.prepareStatement(getTraits);
			 ResultSet resultSet = statement.executeQuery())
		{
			List<Trait> traitList = new ArrayList<>();

			while (resultSet.next())
			{
				// Set the Germplasm bean using the data returned from the database
				Trait trait = new Trait();
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
	 * Return the Trait object identified by the supplied id. Uses the getTrait query above to query the database.
	 *
	 * @param id	The id of the Trait object to be retrieved.
	 * @return		A Trait object representing the trait identified by the supplied id.
	 */
	public Trait getById(int id)
	{
		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = createByIdStatement(con, getTrait, id);
			 ResultSet resultSet = statement.executeQuery())
		{
			if (resultSet.first())
			{
				// Set the Germplasm bean using the data returned from the database
				Trait trait = new Trait();
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
