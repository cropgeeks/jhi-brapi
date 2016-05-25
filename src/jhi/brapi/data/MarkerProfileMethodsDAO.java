package jhi.brapi.data;

import jhi.brapi.resource.*;

import java.util.ArrayList;
import java.util.List;

public class MarkerProfileMethodsDAO
{
	public BasicResource<DataResult<BrapiMarkerProfileMethod>> getAll(int currentPage, int pageSize)
	{
		BasicResource<DataResult<BrapiMarkerProfileMethod>> result = new BasicResource<>();
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
			result = new BasicResource<DataResult<BrapiMarkerProfileMethod>>(new DataResult(methodsList), currentPage, pageSize, methodsList.size());

		}
		//		catch (SQLException e) { e.printStackTrace(); }

		return result;
	}
}