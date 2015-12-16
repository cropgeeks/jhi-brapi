package jhi.brapi.data;

import jhi.brapi.resource.*;

import java.util.ArrayList;
import java.util.List;

public class MarkerProfileMethodsDAO
{
	public List<BrapiMarkerProfileMethod> getAll()
	{
		// To store the Germplasm instances created from the results of the db query before adding them to the
		// GermplasmList object
		List<BrapiMarkerProfileMethod> methodsList = new ArrayList<>();

		//		try (Connection con = Database.INSTANCE.getDataSource().getConnection();
		//			 PreparedStatement statement = con.prepareStatement(getLines);
		//			 ResultSet resultSet = statement.executeQuery())
		{
//			while (resultSet.next())
			{
				BrapiMarkerProfileMethod method = new BrapiMarkerProfileMethod();
				method.setMethodId("0");
				method.setName("wibble");

				methodsList.add(method);
			}

		}
		//		catch (SQLException e) { e.printStackTrace(); }

		return methodsList;
	}
}