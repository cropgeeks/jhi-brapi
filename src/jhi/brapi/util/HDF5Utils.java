package jhi.brapi.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

public class HDF5Utils
{
	public static String getHdf5File(String datasetId)
	{
		String file = null;

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = DatabaseUtils.createByIdStatement(con, "SELECT * FROM datasets WHERE id = ?", datasetId);
			 ResultSet resultSet = statement.executeQuery())
		{
			if(resultSet.next())
				file = resultSet.getString("source_file");
		}
		catch (SQLException e) { e.printStackTrace(); }

		return file;
	}

	public static LinkedHashMap<String, String> getGermplasmMappingForIds(List<Integer> germinatebaseIds)
	{
		String query = "SELECT id, name FROM germinatebase WHERE id IN (%s) ORDER BY FIELD (id, %s)";

		LinkedHashMap<String, String> map = new LinkedHashMap<>();

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = DatabaseUtils.createOrderedInStatement(con, query, germinatebaseIds);
			 ResultSet resultSet = statement.executeQuery())
		{
			while(resultSet.next())
				map.put(resultSet.getString("id"), resultSet.getString("name"));
		}
		catch (SQLException e) { e.printStackTrace(); }

		return map;
	}

	public static LinkedHashMap<String, String> getGermplasmMappingForNames(List<String> names)
	{
		String query = "SELECT id, name FROM germinatebase WHERE name IN (%s) ORDER BY FIELD (name, %s)";

		LinkedHashMap<String, String> map = new LinkedHashMap<>();

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = DatabaseUtils.createOrderedInStatement(con, query, names);
			 ResultSet resultSet = statement.executeQuery())
		{
			while(resultSet.next())
				map.put(resultSet.getString("id"), resultSet.getString("name"));
		}
		catch (SQLException e) { e.printStackTrace(); }

		return map;
	}
}
