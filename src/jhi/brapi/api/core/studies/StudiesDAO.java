package jhi.brapi.api.core.studies;

import java.sql.*;
import java.time.*;
import java.util.*;

import jhi.brapi.api.*;
import jhi.brapi.api.core.locations.*;
import jhi.brapi.util.*;

/**
 * Specifies the public interface which any ServerGermplasm data accessing classes must implement.
 */
public class StudiesDAO
{
	// Simply selects all fields from germinatebase
	private final String getStudyDetails = "SELECT datasets.*, experiments.description AS trial_name, experimenttypes.description AS trial_type, experimenttypes.id, locations.*, countries.country_code3, countries.country_name, locationtypes.name, (SELECT GROUP_CONCAT(DISTINCT YEAR (recording_date) ORDER BY recording_date ) FROM phenotypedata WHERE phenotypedata.dataset_id = datasets.id) AS years FROM datasets LEFT JOIN experiments ON experiments.id = datasets.experiment_id LEFT JOIN experimenttypes ON experimenttypes.id = experiments.experiment_type_id LEFT JOIN locations ON datasets.location_id = locations.id LEFT JOIN countries on countries.id = locations.country_id LEFT JOIN locationtypes ON locations.locationtype_id = locationtypes.id WHERE datasets.id = ?";

	private final String getStudies = "SELECT SQL_CALC_FOUND_ROWS datasets.*, experiments.description AS trial_name, experimenttypes.description AS trial_type, experimenttypes.id, locations.*, countries.country_code3, countries.country_name, locationtypes.name, (SELECT GROUP_CONCAT(DISTINCT YEAR (recording_date) ORDER BY recording_date ) FROM phenotypedata WHERE phenotypedata.dataset_id = datasets.id) AS years FROM datasets LEFT JOIN experiments ON experiments.id = datasets.experiment_id LEFT JOIN experimenttypes ON experimenttypes.id = experiments.experiment_type_id LEFT JOIN locations ON datasets.location_id = locations.id LEFT JOIN countries on countries.id = locations.country_id LEFT JOIN locationtypes ON locations.locationtype_id = locationtypes.id %s GROUP BY datasets.id LIMIT ?, ?";

	private LocationDAO locationDAO = new LocationDAO();

	public BrapiBaseResource<Study> getById(String id)
	{
		BrapiBaseResource<Study> result = new BrapiBaseResource<>();

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement mapStatement = DatabaseUtils.createByIdStatement(con, getStudyDetails, id);
			 ResultSet resultSet = mapStatement.executeQuery())
		{
			if(resultSet.next())
				result = new BrapiBaseResource<>(getStudyFrom(resultSet));
		}
		catch (SQLException e) { e.printStackTrace(); }

		return result;
	}

	public BrapiListResource<Study> getAll(Map<String, List<String>> parameters, int currentPage, int pageSize)
	{
		// Create empty BrapiBaseResource of type BrapiGermplasm (if for whatever reason we can't get data from the database
		// this is what's returned
		BrapiListResource<Study> result = new BrapiListResource<>();

		// Paginate over the data by passing the currentPage and pageSize values to the code which generates the
		// prepared statement (which includes a limit statement)
		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = DatabaseUtils.createParameterizedLimitStatement(con, getStudies, parameters, currentPage, pageSize);
			 ResultSet resultSet = statement.executeQuery())
		{
			List<Study> list = new ArrayList<>();

			while (resultSet.next())
				list.add(getStudyFrom(resultSet));

			long totalCount = DatabaseUtils.getTotalCount(statement);

			// Pass the currentPage and totalCount to the BrapiBaseResource constructor so we generate correct metadata
			result = new BrapiListResource<Study>(list, currentPage, pageSize, totalCount);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return result;
	}

	private Study getStudyFrom(ResultSet resultSet)
		throws SQLException
	{
		Study study = new Study();
		study.setStudyDbId(resultSet.getString("experiment_id"));
		study.setStudyDescription(resultSet.getString("description"));
		study.setStudyType(resultSet.getString("trial_type"));
		study.setTrialName(resultSet.getString("trial_name"));
		study.setStudyName(resultSet.getString("trial_name"));
		java.sql.Date startDate = resultSet.getDate("datasets.date_start");
		if (startDate != null)
			study.setStartDate(Instant.ofEpochMilli(startDate.getTime()).toString());
		java.sql.Date endDate = resultSet.getDate("datasets.date_end");
		if (endDate != null)
			study.setEndDate(Instant.ofEpochMilli(endDate.getTime()).toString());
		study.setActive(false);

		Location location = locationDAO.getBrapiLocation(resultSet);
		study.setLocationDbId(location.getLocationDbId());
		study.setLocationName(location.getLocationName());

		List<String> seasons = new ArrayList<>();
		// Parse out the years
		String seasonString = resultSet.getString("years");
		study.setSeasons(parseSeasonString(seasonString));
		study.setAdditionalInfo(new HashMap<String, Object>());

		LastUpdate lastUpdate = new LastUpdate();
		lastUpdate.setVersion(resultSet.getString("datasets.version"));
		lastUpdate.setTimestamp(resultSet.getTimestamp("datasets.updated_on").toString());
		study.setLastUpdate(lastUpdate);

		return study;
	}

	private List<String> parseSeasonString(String seasonString)
	{
		List<String> seasons = new ArrayList<>();

		if(seasonString != null)
		{
			String[] yearArray = seasonString.split(",");
			seasons = Arrays.asList(yearArray);
		}

		return seasons;
	}
}