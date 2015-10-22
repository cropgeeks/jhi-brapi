package jhi.brapi.data;

import java.sql.*;
import java.util.*;

import jhi.brapi.resource.*;

/**
 * An implementation of the GermplasmDAO interface which provides access via a database.
 */
public class MarkerProfileMethodsDAOImpl implements MarkerProfileMethodsDAO
{
	@Override
	public MarkerProfileMethodList getAll()
	{
		MarkerProfileMethodList allMethods = new MarkerProfileMethodList();

//		try (Connection con = Database.INSTANCE.getDataSource().getConnection();
//			 PreparedStatement statement = con.prepareStatement(getLines);
//			 ResultSet resultSet = statement.executeQuery())
		{
			// To store the Germplasm instances created from the results of the db query before adding them to the
			// GermplasmList object
			List<MarkerProfileMethod> methodsList = new ArrayList<>();

//			while (resultSet.next())
			{
				MarkerProfileMethod method = new MarkerProfileMethod();
				method.setMethodId("0");
				method.setName("wibble");

				methodsList.add(method);
			}
			allMethods.setMethods(methodsList);
		}
//		catch (SQLException e) { e.printStackTrace(); }

		return allMethods;
	}
}