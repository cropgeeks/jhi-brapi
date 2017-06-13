package jhi.brapi.api.crops;

import java.sql.*;
import java.util.*;

import jhi.brapi.api.*;
import jhi.brapi.api.markers.*;
import jhi.brapi.api.markers.BrapiMarker.*;
import jhi.brapi.util.*;

/**
 * Specifies the public interface which any Crop data accessing classes must implement.
 */
public class CropDAO
{
	private final String getCrops = "SELECT DISTINCT cropname FROM taxonomies LIMIT ?, ?";

	public BrapiListResource<String> getAll(int currentPage, int pageSize)
	{
		// Create empty BrapiBaseResource of type BrapiMarker (if for whatever reason we can't get data from the database
		// this is what's returned
		BrapiListResource<String> result = new BrapiListResource<>();

		// Paginate over the data by passing the currentPage and pageSize values to the code which generates the
		// prepared statement (which includes a limit statement)
		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = DatabaseUtils.createLimitStatement(con, getCrops, currentPage, pageSize);
			 ResultSet resultSet = statement.executeQuery())
		{
			List<String> list = new ArrayList<>();

			while (resultSet.next())
				list.add(resultSet.getString("cropname"));

			long totalCount = DatabaseUtils.getTotalCount(statement);

			// Pass the currentPage and totalCount to the BrapiBaseResource constructor so we generate correct metadata
			result = new BrapiListResource<String>(list, currentPage, pageSize, totalCount);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return result;
	}
}