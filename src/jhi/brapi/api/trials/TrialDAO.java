package jhi.brapi.api.trials;

import java.sql.*;
import java.time.*;
import java.util.*;

import jhi.brapi.api.*;
import jhi.brapi.util.*;

/**
 * Specifies the public interface which any ServerTrials data accessing classes must implement.
 */
public class TrialDAO
{
	private final String experimentType = "(experimenttypes.description = 'trials' OR experimenttypes.description = 'phenotype')";
	private final String tables = "datasets LEFT JOIN experiments ON experiments.id = datasets.experiment_id LEFT JOIN experimenttypes ON experimenttypes.id = experiments.experiment_type_id LEFT JOIN locations ON locations.id = datasets.location_id";
	private final String getTrials = "SELECT SQL_CALC_FOUND_ROWS datasets.*, experiments.*, locations.* FROM " + tables + " %s {WHERE_BIT} {ORDER_BIT} LIMIT ?, ?";
	private final String getById = "SELECT datasets.*, experiments.*, locations.* FROM " + tables + " WHERE datasets.id = ? AND " + experimentType;

	public BrapiListResource<BrapiTrial> getAll(Map<String, List<String>> parameters, String sortBy, int currentPage, int pageSize)
	{
		// Create empty BrapiBaseResource of type BrapiGermplasm (if for whatever reason we can't get data from the database
		// this is what's returned
		BrapiListResource<BrapiTrial> result = new BrapiListResource<>();

		// Paginate over the data by passing the currentPage and pageSize values to the code which generates the
		// prepared statement (which includes a limit statement)

		String query = getTrials.replace("{ORDER_BIT}", sortBy);

		if(parameters.size() > 0)
			query = query.replace("{WHERE_BIT}", " AND " + experimentType);
		else
			query = query.replace("{WHERE_BIT}", " WHERE " + experimentType);

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = DatabaseUtils.createParameterizedLimitStatement(con, query, parameters, currentPage, pageSize);
			 ResultSet resultSet = statement.executeQuery())
		{
			List<BrapiTrial> list = new ArrayList<>();

			while (resultSet.next())
				list.add(getBrapiTrial(resultSet));

			long totalCount = DatabaseUtils.getTotalCount(statement);

			// Pass the currentPage and totalCount to the BrapiBaseResource constructor so we generate correct metadata
			result = new BrapiListResource<BrapiTrial>(list, currentPage, pageSize, totalCount);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return result;
	}

	private BrapiTrial getBrapiTrial(ResultSet resultSet) throws SQLException
	{
		BrapiTrial trial = new BrapiTrial();

		trial.setTrialDbId(resultSet.getString("datasets.id"));
		trial.setTrialName(resultSet.getString("datasets.description"));
		trial.setActive("true");
		trial.setProgramDbId(resultSet.getString("experiments.id"));
		trial.setProgramName(resultSet.getString("experiments.description"));
		java.sql.Date startDate = resultSet.getDate("datasets.date_start");
		if (startDate != null)
			trial.setStartDate(Instant.ofEpochMilli(startDate.getTime()).toString());
		java.sql.Date endDate = resultSet.getDate("datasets.date_end");
		if (endDate != null)
			trial.setEndDate(Instant.ofEpochMilli(endDate.getTime()).toString());
		trial.setStudies(getBrapiTrialStudies(resultSet));

		return trial;
	}

	private List<BrapiTrialStudy> getBrapiTrialStudies(ResultSet resultSet) throws SQLException
	{
		List<BrapiTrialStudy> result = new ArrayList<>();

		BrapiTrialStudy study = new BrapiTrialStudy();
		study.setStudyDbId(resultSet.getString("datasets.id"));
		study.setStudyName(resultSet.getString("datasets.description"));
		study.setLocationName(resultSet.getString("locations.site_name"));

		result.add(study);

		return result;
	}

	public BrapiBaseResource<BrapiTrial> getById(String id)
	{
		BrapiBaseResource<BrapiTrial> result = new BrapiBaseResource<>();

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statemet = DatabaseUtils.createByIdStatement(con, getById, id);
			 ResultSet resultSet = statemet.executeQuery())
		{
			if (resultSet.next())
				result = new BrapiBaseResource<>(getBrapiTrial(resultSet));
		}
		catch (SQLException e) { e.printStackTrace(); }

		return result;
	}
}