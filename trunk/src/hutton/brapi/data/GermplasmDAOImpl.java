package hutton.brapi.data;

import java.sql.*;
import java.util.*;

import hutton.brapi.resource.*;

/**
 * An implementation of the GermplasmDAO interface which provides access via a database.
 */
public class GermplasmDAOImpl implements GermplasmDAO
{
	// Simply selects all fields from germinatebase
	private final String getLines = "select * from germinatebase";

	// Simply selects all fields from germinatebase where the given id matches the id from the URI
	private final String getSpecificLine = "select * from germinatebase where id=?";

	// Query to extract the markerprofiles which relate to the germplasm indicated by id
	private final String markrerProfileIdQuery = "select DISTINCT(dataset_id), germinatebase_id from genotypes where germinatebase_id=?";

	@Override
	public GermplasmList getAll()
	{
		GermplasmList allGermplasm = new GermplasmList();

		try (Connection con = Database.INSTANCE.getDataSource().getConnection();
			 PreparedStatement statement = con.prepareStatement(getLines);
			 ResultSet resultSet = statement.executeQuery())
		{
			// To store the Germplasm instances created from the results of the db query before adding them to the
			// GermplasmList object
			List<Germplasm> germplasmList = new ArrayList<>();

			while (resultSet.next())
			{
				// Set the Germplasm bean using the data returned from the database
				Germplasm germplasm = new Germplasm();
				germplasm.setGermplasmId(resultSet.getInt("id"));
				germplasm.setGermplasmName(resultSet.getString("number"));
				germplasm.setTaxonId(resultSet.getInt("taxonomy_id"));

				germplasmList.add(germplasm);
			}
			allGermplasm.setGermplasm(germplasmList);
		}
		catch (SQLException e) { e.printStackTrace(); }

		return allGermplasm;
	}

	@Override
	public Germplasm getById(int id)
	{
		Germplasm germplasm = null;

		try (Connection con = Database.INSTANCE.getDataSource().getConnection();
			PreparedStatement statement = createByIdStatement(con, getSpecificLine, id);
			ResultSet resultSet = statement.executeQuery())
		{
			if (resultSet.first())
			{
				// Set the Germplasm bean using the data returned from the database
				germplasm = new Germplasm();
				germplasm.setGermplasmId(resultSet.getInt("id"));
				germplasm.setGermplasmName(resultSet.getString("number"));
				germplasm.setTaxonId(resultSet.getInt("taxonomy_id"));
			}
		}
		catch (SQLException e) { e.printStackTrace(); }

		return germplasm;
	}

	private PreparedStatement createByIdStatement(Connection con, String query, int id)
		throws SQLException
	{
		// Prepare statement with ID
		PreparedStatement statement = con.prepareStatement(query);
		statement.setInt(1, id);

		return statement;
	}

	@Override
	public GermplasmMarkerProfileList getMarkerProfilesFor(int id)
	{
		try (Connection con = Database.INSTANCE.getDataSource().getConnection();
			 PreparedStatement statement = createByIdStatement(con, markrerProfileIdQuery, id);
			 ResultSet resultSet = statement.executeQuery())
		{
			GermplasmMarkerProfileList profileList = new GermplasmMarkerProfileList();
			List<String> markerProfileIdList = new ArrayList<>();
			while (resultSet.next())
			{
				profileList.setGermplasmId(resultSet.getInt("germinatebase_id"));
				markerProfileIdList.add(resultSet.getInt("dataset_id") + "-" + resultSet.getInt("germinatebase_id"));
			}
			profileList.setMarkerProfiles(markerProfileIdList);

			return profileList;
		}
		catch (SQLException e) { e.printStackTrace(); }

		return null;
	}
}