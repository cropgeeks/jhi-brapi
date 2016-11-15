package jhi.brapi.util;

import java.sql.*;
import java.util.*;
import java.util.stream.*;

public class DatabaseUtils
{
	public static long getTotalCount(String sql)
	{
		// Query for count the total number of items defined by the resource
		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = con.prepareStatement(sql);
			 ResultSet resultSet = statement.executeQuery())
		{
			if (resultSet.first())
				return resultSet.getLong("total_count");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return -1;
	}

	public static long getValueTotalCount(String sql, Collection<String> values)
	{
		// Query for count the total number of items defined by the resource
		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = createValueStatement(con, sql, values);
			 ResultSet resultSet = statement.executeQuery())
		{
			if (resultSet.first())
				return resultSet.getLong("total_count");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return -1;
	}

	public static long getParameterizedTotalCount(String sql, LinkedHashMap<String, String> parameters)
	{
		// Query for count the total number of items defined by the resource
		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = createParameterizedStatement(con, sql, parameters);
			 ResultSet resultSet = statement.executeQuery())
		{
			if (resultSet.first())
				return resultSet.getLong("total_count");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return -1;
	}

	public static long getTotalCountById(String sql, String id)
	{
		// Query for count the total number of items defined by the resource
		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = createByIdStatement(con, sql, id);
			 ResultSet resultSet = statement.executeQuery())
		{
			if (resultSet.first())
				return resultSet.getLong("total_count");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return -1;
	}

	public static PreparedStatement createInLimitStatement(Connection con, String query, int currentPage, int pageSize, List<?>... lists)
			throws SQLException
	{
		String[] placeholders = Arrays.stream(lists)
									  .map(l -> createPlaceholders(l.size()))
									  .toArray(String[]::new);

		PreparedStatement stmt = con.prepareStatement(String.format(query, (Object[]) placeholders));

		int i = 1;
		for(List<?> list : lists)
		{
			for(Object o : list)
			{
				stmt.setString(i++, o.toString());
			}
		}

		stmt.setInt(i++, getLimitStart(currentPage, pageSize));
		stmt.setInt(i++, pageSize);

		return stmt;
	}

	public static PreparedStatement createInStatement(Connection con, String query, List<?>... lists)
			throws SQLException
	{
		String[] placeholders = Arrays.stream(lists)
			  .map(l -> createPlaceholders(l.size()))
			  .toArray(String[]::new);

		PreparedStatement stmt = con.prepareStatement(String.format(query, (Object[]) placeholders));

		int i = 1;
		for(List<?> list : lists)
		{
			for(Object o : list)
			{
				stmt.setString(i++, o.toString());
			}
		}

		return stmt;
	}

	public static String createPlaceholders(int size)
	{
		return IntStream.range(0, size)
						.mapToObj(i -> "?")
						.collect(Collectors.joining(","));
	}

	public static PreparedStatement createByIdStatement(Connection con, String query, String id)
			throws SQLException
	{
		// Prepare statement with ID
		PreparedStatement statement = con.prepareStatement(query);
		statement.setString(1, id);

		return statement;
	}

	public static PreparedStatement createLimitStatement(Connection con, String query, int currentPage, int pageSize)
			throws SQLException
	{
		// Prepare statement with low and high params for a limit query
		PreparedStatement statement = con.prepareStatement(query);
		statement.setInt(1, getLimitStart(currentPage, pageSize));
		statement.setInt(2, pageSize);

		return statement;
	}

	public static PreparedStatement createValueStatement(Connection con, String query, Collection<String> values)
			throws SQLException
	{
		// Prepare statement with low and high params for a limit query
		PreparedStatement statement = con.prepareStatement(query);

		int i = 1;
		for(String value : values)
			statement.setString(i++, value);

		return statement;
	}

	public static PreparedStatement createValueLimitStatement(Connection con, String query, Collection<String> values, int currentPage, int pageSize)
			throws SQLException
	{
		// Prepare statement with low and high params for a limit query
		PreparedStatement statement = con.prepareStatement(query);

		int i = 1;
		for(String value : values)
			statement.setString(i++, value);

		statement.setInt(i++, getLimitStart(currentPage, pageSize));
		statement.setInt(i++, pageSize);

		return statement;
	}

	public static PreparedStatement createParameterizedStatement(Connection con, String query, LinkedHashMap<String, String> parameters)
			throws SQLException
	{
		StringBuilder builder = new StringBuilder();

		for (Map.Entry<String, String> entry : parameters.entrySet())
		{
			if (builder.length() != 0)
				builder.append(" AND ");

			builder.append(entry.getKey())
				   .append(" = ?");
		}

		query = String.format(query, builder.toString());

		// Prepare statement with low and high params for a limit query
		PreparedStatement statement = con.prepareStatement(query);

		int i = 1;

		for (Map.Entry<String, String> entry : parameters.entrySet())
		{
			statement.setString(i++, entry.getValue());
		}

		return statement;
	}

	public static PreparedStatement createParameterizedLimitStatement(Connection con, String query, LinkedHashMap<String, String> parameters, int currentPage, int pageSize)
			throws SQLException
	{
		StringBuilder builder = new StringBuilder();

		for (Map.Entry<String, String> entry : parameters.entrySet())
		{
			if (builder.length() != 0)
				builder.append(" AND ");

			builder.append(entry.getKey())
				   .append(" = ?");
		}

		query = String.format(query, builder.toString());

		// Prepare statement with low and high params for a limit query
		PreparedStatement statement = con.prepareStatement(query);

		int i = 1;

		for (Map.Entry<String, String> entry : parameters.entrySet())
		{
			statement.setString(i++, entry.getValue());
		}

		statement.setInt(i++, getLimitStart(currentPage, pageSize));
		statement.setInt(i++, pageSize);

		return statement;
	}

	public static PreparedStatement createByIdLimitStatement(Connection con, String query, String id, int currentPage, int pageSize)
			throws SQLException
	{
		// Prepare statement with ID
		PreparedStatement statement = con.prepareStatement(query);
		statement.setString(1, id);
		statement.setInt(2, getLimitStart(currentPage, pageSize));
		statement.setInt(3, pageSize);

		return statement;
	}

	public static int getLimitStart(int currentPage, int pageSize)
	{
		return currentPage * pageSize;
	}
}
