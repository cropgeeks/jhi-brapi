package jhi.brapi.util;

import java.sql.*;
import java.util.*;
import java.util.stream.*;

public class DatabaseUtils
{
	public static String createPlaceholders(int size)
	{
		return IntStream.range(0, size)
						.mapToObj(i -> "?")
						.collect(Collectors.joining(","));
	}

	public static String createInPlaceholders(int size)
	{
		return IntStream.range(0, size)
						.mapToObj(i -> "?")
						.collect(Collectors.joining(",", " IN(", ") "));
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

	public static PreparedStatement createParameterizedLimitStatement(Connection con, String query, Map<String, List<String>> parameters, int currentPage, int pageSize)
		throws SQLException
	{
		query = buildParameterizedQuery(query, parameters);

		// Prepare statement with low and high params for a limit query
		PreparedStatement statement = con.prepareStatement(query);

		int i = 1;

		for (Map.Entry<String, List<String>> entry : parameters.entrySet())
		{
			for (String value : entry.getValue())
				statement.setString(i++, value);
		}

		statement.setInt(i++, getLimitStart(currentPage, pageSize));
		statement.setInt(i++, pageSize);

		return statement;
	}

	public static PreparedStatement createParameterizedStatement(Connection con, String query, Map<String, List<String>> parameters)
			throws SQLException
	{
		query = buildParameterizedQuery(query, parameters);

		// Prepare statement with low and high params for a limit query
		PreparedStatement statement = con.prepareStatement(query);

		int i = 1;

		for (Map.Entry<String, List<String>> entry : parameters.entrySet())
		{
			for (String value : entry.getValue())
				statement.setString(i++, value);
		}

		return statement;
	}

	private static String buildParameterizedQuery(String query, Map<String, List<String>> parameters)
	{
		StringBuilder builder = new StringBuilder();
		for (Map.Entry<String, List<String>> entry : parameters.entrySet())
		{
			if (builder.length() != 0)
				builder.append(" AND ");

			if (builder.length() == 0 && entry.getValue().size() > 0)
				builder.append("WHERE ");

			builder.append(entry.getKey())
				   .append(DatabaseUtils.createInPlaceholders(entry.getValue().size()));
		}

		return String.format(query, builder.toString());
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

	/**
	 * Create a statement that will search for items with the given "IN" call. This will also order the items based on these values
	 *
	 * @param con   The database {@link Connection}
	 * @param sql   The SQL query containing two "%s" placeholders, one in the "IN" block and one for the order of the items
	 * @param items The items to put into the two placeholders
	 * @return The {@link PreparedStatement} representing the query
	 * @throws SQLException Thrown if the query fails on the database
	 */
	public static PreparedStatement createOrderedInStatement(Connection con, String sql, List<?> items) throws SQLException
	{
		String formatted = String.format(sql, DatabaseUtils.createPlaceholders(items.size()), DatabaseUtils.createPlaceholders(items.size()));

		PreparedStatement stmt = con.prepareStatement(formatted);

		int i = 1;
		for(Object item : items)
			stmt.setString(i++, item.toString());

		for(Object item : items)
			stmt.setString(i++, item.toString());

		return stmt;
	}

	public static int getLimitStart(int currentPage, int pageSize)
	{
		return currentPage * pageSize;
	}

	// Should be used for SELECT statements which include the SQL_CALC_FOUND_ROWS
	// function
	public static long getTotalCount(Statement stmt)
		throws SQLException
	{
		long totalCount = 0;

		ResultSet rs = stmt.executeQuery("SELECT FOUND_ROWS()");
		if (rs.next())
			totalCount = rs.getLong(1);

		return totalCount;
	}
}