package jhi.brapi.api.locations;

import java.sql.*;
import java.util.*;

import jhi.brapi.api.*;
import jhi.brapi.util.*;

public class LocationDAO
{
	private final String getLocations = "SELECT SQL_CALC_FOUND_ROWS countries.country_code3, countries.country_name, locations.id, site_name, site_name_short, latitude, longitude, elevation, locationtypes.name FROM locations LEFT JOIN  countries ON country_id = countries.id LEFT JOIN locationtypes ON locationtype_id = locationtypes.id LIMIT ?, ?";

	private final String getLocationsWhere = "SELECT SQL_CALC_FOUND_ROWS countries.country_code3, countries.country_name, locations.id, site_name, site_name_short, latitude, longitude, elevation, locationtypes.name FROM locations LEFT JOIN  countries ON country_id = countries.id LEFT JOIN locationtypes ON locationtype_id = locationtypes.id WHERE name = ? LIMIT ?, ?";

	private final String getLocation = "SELECT countries.country_code3, countries.country_name, locations.id, site_name, site_name_short, latitude, longitude, elevation, locationtypes.name FROM locations LEFT JOIN  countries ON country_id = countries.id LEFT JOIN locationtypes ON locationtype_id = locationtypes.id WHERE locations.id = ?";

	public BrapiListResource<BrapiLocation> getAll(int currentPage, int pageSize)
	{
		// Create empty BrapiBaseResource of type BrapiLocation (if for whatever reason we can't get data from the database
		// this is what's returned
		BrapiListResource<BrapiLocation> result = new BrapiListResource<>();

		// Paginate over the data by passing the currentPage and pageSize values to the code which generates the
		// prepared statement (which includes a limit statement)
		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = DatabaseUtils.createLimitStatement(con, getLocations, currentPage, pageSize);
			 ResultSet resultSet = statement.executeQuery())
		{
			List<BrapiLocation> list = new ArrayList<>();

			while (resultSet.next())
				list.add(getBrapiLocation(resultSet));

			long totalCount = DatabaseUtils.getTotalCount(statement);

			// Pass the currentPage and totalCount to the BrapiBaseResource constructor so we generate correct metadata
			result = new BrapiListResource<BrapiLocation>(list, currentPage, pageSize, totalCount);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return result;
	}

	public BrapiListResource<BrapiLocation> getByLocationType(String locationType, int currentPage, int pageSize)
	{
		// Create empty BrapiBaseResource of type BrapiLocation (if for whatever reason we can't get data from the database
		// this is what's returned
		BrapiListResource<BrapiLocation> result = new BrapiListResource<>();

		// Paginate over the data by passing the currentPage and pageSize values to the code which generates the
		// prepared statement (which includes a limit statement)
		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = DatabaseUtils.createByIdLimitStatement(con, getLocationsWhere, locationType, currentPage, pageSize);
			 ResultSet resultSet = statement.executeQuery())
		{
			List<BrapiLocation> list = new ArrayList<>();

			while (resultSet.next())
				list.add(getBrapiLocation(resultSet));

			long totalCount = DatabaseUtils.getTotalCount(statement);

			// Pass the currentPage and totalCount to the BrapiBaseResource constructor so we generate correct metadata
			result = new BrapiListResource<BrapiLocation>(list, currentPage, pageSize, totalCount);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return result;
	}

	public BrapiBaseResource<BrapiLocation> getById(String id)
	{
		// Create empty BrapiBaseResource of type BrapiLocation (if for whatever reason we can't get data from the database
		// this is what's returned
		BrapiBaseResource<BrapiLocation> result = new BrapiBaseResource<>();

		// Paginate over the data by passing the currentPage and pageSize values to the code which generates the
		// prepared statement (which includes a limit statement)
		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = DatabaseUtils.createByIdStatement(con, getLocation, id);
			 ResultSet resultSet = statement.executeQuery())
		{
			if (resultSet.next())
				result = new BrapiBaseResource<BrapiLocation>(getBrapiLocation(resultSet));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
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
		location.setLocationDbId(resultSet.getString("locations.id"));
		location.setAdditionalInfo(null);
		location.setLocationType(resultSet.getString("locationtypes.name"));
		location.setAbbreviation(resultSet.getString("locations.site_name_short"));

		return location;
	}
}
