package jhi.brapi.data;

import java.sql.*;

public class GatekeeperUtils
{
	public static boolean authenticate(String username, String password)
	{
		try (Connection con = Database.INSTANCE.getDataSourceGatekeeper().getConnection();
			 PreparedStatement statement = DatabaseUtils.createByIdStatement(con, "SELECT password FROM users WHERE username=?", username);
			 ResultSet resultSet = statement.executeQuery())
		{
			if (resultSet.first())
				if (BCrypt.checkpw(password, resultSet.getString(1)))
					return true;
		}
		catch (SQLException e) { e.printStackTrace(); }

		return false;
	}

	// Probably add other methods to do can-user-access-this...etc queries
}
