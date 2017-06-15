package jhi.brapi.util;

import java.math.*;
import java.security.*;
import java.sql.*;

import jhi.brapi.*;
import jhi.brapi.api.authentication.*;

public class GatekeeperUtils
{
	public static BrapiSessionToken authenticate(String username, String password)
	{
		BrapiSessionToken result = null;

		try (Connection con = Database.INSTANCE.getDataSourceGatekeeper().getConnection();
			 PreparedStatement statement = DatabaseUtils.createByIdStatement(con, "SELECT * FROM users WHERE username=?", username); // TODO: Check if the user is actually allowed to access THIS instance of Germinate
			 ResultSet resultSet = statement.executeQuery())
		{
			if (resultSet.first())
				if (BCrypt.checkpw(password, resultSet.getString("password")))
				{
					// Generate a unique token for this session
					String token = new BigInteger(256, new SecureRandom()).toString(32);
					// And add it to the sessions stored server-side
					Brapi.getSessions().addSession(username, token);

					result = new BrapiSessionToken();
					result.setUserDisplayName("full_name");
					result.setAccess_token(token);
				}
		}
		catch (SQLException e) { e.printStackTrace(); }

		return result;
	}

	// TODO: Probably add other methods to do can-user-access-this...etc queries
}
