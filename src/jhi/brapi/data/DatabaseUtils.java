package jhi.brapi.data;

import java.sql.*;

/**
 * Created by gs40939 on 03/03/2016.
 */
public class DatabaseUtils
{
	public static long getTotalCount(String sql)
	{
		// Query for count the total number of items defined by the resource
		try(Connection con = Database.INSTANCE.getDataSource().getConnection();
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

	public static long getTotalCountById(String sql, String id)
	{
		// Query for count the total number of items defined by the resource
		try(Connection con = Database.INSTANCE.getDataSource().getConnection();
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
		statement.setInt(1, PaginationUtils.getLowLimit(currentPage, pageSize));
		statement.setInt(2, pageSize);

		return statement;
	}

	public static PreparedStatement createByIdLimitStatement(Connection con, String query, String id, int currentPage, int pageSize)
		throws SQLException
	{
		// Prepare statement with ID
		PreparedStatement statement = con.prepareStatement(query);
		statement.setString(1, id);
		statement.setInt(2, PaginationUtils.getLowLimit(currentPage, pageSize));
		statement.setInt(3, pageSize);

		return statement;
	}
}
