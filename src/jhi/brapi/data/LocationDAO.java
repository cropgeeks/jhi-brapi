package jhi.brapi.data;

import java.sql.*;
import java.util.*;

import jhi.brapi.resource.*;

public class LocationDAO
{
	private final String getCountLocations = "SELECT COUNT(*) AS total_count FROM locations WHERE locationtype_id = 3";

	private final String getLocations = "SELECT countries.country_code3, countries.country_name, locations.id, site_name, latitude, longitude, elevation FROM locations LEFT JOIN  countries ON country_id = countries.id WHERE locations.locationtype_id = 3 LIMIT ?, ?";

	public BasicResource<DataResult<BrapiLocation>> getAll(int currentPage, int pageSize)
	{
		// Create empty BasicResource of type BrapiLocation (if for whatever reason we can't get data from the database
		// this is what's returned
		BasicResource<DataResult<BrapiLocation>> result = new BasicResource<>();

		long totalCount = DatabaseUtils.getTotalCount(getCountLocations);

		if (totalCount != -1)
		{
			// Paginate over the data by passing the currentPage and pageSize values to the code which generates the
			// prepared statement (which includes a limit statement)
			try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
				 PreparedStatement statement = DatabaseUtils.createLimitStatement(con, getLocations, currentPage, pageSize);
				 ResultSet resultSet = statement.executeQuery())
			{
				List<BrapiLocation> list = new ArrayList<>();

				while (resultSet.next())
					list.add(getBrapiLocation(resultSet));

				// Pass the currentPage and totalCount to the BasicResource constructor so we generate correct metadata
				result = new BasicResource<DataResult<BrapiLocation>>(new DataResult(list), currentPage, pageSize, totalCount);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

		return result;
	}

	private BrapiLocation getBrapiLocation(ResultSet resultSet)
		throws SQLException
	{
		BrapiLocation location = new BrapiLocation();

		location.setName(resultSet.getString("locations.site_name"));
		location.setCountryCode(resultSet.getString("countries.country_code3"));
		location.setCountryName(resultSet.getString("countries.country_name"));
		location.setAltitude(resultSet.getDouble("locations.elevation"));
		location.setLatitude(resultSet.getDouble("locations.latitude"));
		location.setLongitude(resultSet.getDouble("locations.longitude"));
		location.setLocationDbId(resultSet.getInt("locations.id"));
		location.setAttributes(null);

		return location;
	}
}
