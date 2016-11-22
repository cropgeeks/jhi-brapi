package jhi.brapi.api.studies;

import java.sql.*;
import java.util.*;

import jhi.brapi.api.*;
import jhi.brapi.api.locations.*;
import jhi.brapi.util.*;

/**
 * Specifies the public interface which any ServerGermplasmSearch data accessing classes must implement.
 */
public class StudiesDAO
{
	// Simply selects all fields from germinatebase
	private final String getStudyDetails = "SELECT datasets.*, experiments.description AS trial_name, experimenttypes.description AS trial_type, (SELECT GROUP_CONCAT(DISTINCT YEAR (recording_date) ORDER BY recording_date ) FROM phenotypedata WHERE phenotypedata.dataset_id = datasets.id) AS years FROM phenotypedata LEFT JOIN datasets ON datasets.id = phenotypedata.dataset_id LEFT JOIN experiments ON experiments.id = datasets.experiment_id LEFT JOIN experimenttypes ON experimenttypes.id = experiments.experiment_type_id WHERE datasets.id = ?";

	private final String getStudies = "SELECT SQL_CALC_FOUND_ROWS datasets.*, experiments.description AS trial_name, experimenttypes.description AS trial_type, (SELECT GROUP_CONCAT(DISTINCT YEAR (recording_date) ORDER BY recording_date ) FROM phenotypedata WHERE phenotypedata.dataset_id = datasets.id) AS years FROM datasets LEFT JOIN experiments ON experiments.id = datasets.experiment_id LEFT JOIN experimenttypes ON experimenttypes.id = experiments.experiment_type_id WHERE 1=1 %s GROUP BY datasets.id LIMIT ?, ?";

	private final String studyDetailsTable = "call phenotype_data_complete (?)";

	private final String studyDetailsTablePhenotypes = "select DISTINCT(phenotypes.id) from phenotypes LEFT JOIN phenotypedata ON phenotypes.id = phenotypedata.phenotype_id WHERE dataset_id = ?";

	public BrapiBaseResource<BrapiStudies> getById(String id)
	{
		BrapiBaseResource<BrapiStudies> result = new BrapiBaseResource<>();

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement mapStatement = DatabaseUtils.createByIdStatement(con, getStudyDetails, id);
			 ResultSet resultSet = mapStatement.executeQuery())
		{
			if(resultSet.next())
				result = new BrapiBaseResource<>(getBrapiStudies(resultSet));
		}
		catch (SQLException e) { e.printStackTrace(); }

		return result;
	}

	public BrapiListResource<BrapiStudies> getAll(int currentPage, int pageSize, String studyType, String programId, String locationId, String seasonId)
	{
		// Create empty BrapiBaseResource of type BrapiGermplasm (if for whatever reason we can't get data from the database
		// this is what's returned
		BrapiListResource<BrapiStudies> result = new BrapiListResource<>();

		// TODO: Can we do this kind of concept in a generic way somewhere?
		StringBuilder builder = new StringBuilder();
		if(studyType != null)
		{
			switch(studyType.toLowerCase())
			{
				case "genotype":
					builder.append(" AND experimenttypes.description = 'genotype' ");
					break;
				case "trial":
					builder.append(" AND experimenttypes.description = 'trials' ");
					break;
				default:
					return result;
			}
		}

		List<String> values = new ArrayList<>();
		if (programId != null && !programId.isEmpty())
		{
			builder.append(" AND experiments.id = ?");
			values.add(programId);
		}
		if (locationId != null && !locationId.isEmpty())
		{
			builder.append(" AND datasets.location_id = ?");
			values.add(locationId);
		}
		if (seasonId != null && !seasonId.isEmpty())
		{
			builder.append(" AND YEAR(phenotypedata.recording_date) = ?");
			values.add(seasonId);
		}

		String finalGetStudies = String.format(getStudies, builder.toString());

		// Paginate over the data by passing the currentPage and pageSize values to the code which generates the
		// prepared statement (which includes a limit statement)
		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = DatabaseUtils.createValueLimitStatement(con, finalGetStudies, values, currentPage, pageSize);
			 ResultSet resultSet = statement.executeQuery())
		{
			List<BrapiStudies> list = new ArrayList<>();

			while (resultSet.next())
				list.add(getBrapiStudies(resultSet));

			long totalCount = DatabaseUtils.getTotalCount(statement);

			// Pass the currentPage and totalCount to the BrapiBaseResource constructor so we generate correct metadata
			result = new BrapiListResource<BrapiStudies>(list, currentPage, pageSize, totalCount);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return result;
	}

	private BrapiStudies getBrapiStudies(ResultSet resultSet)
		throws SQLException
	{
		BrapiStudies studies = new BrapiStudies();
		studies.setStudyDbId(resultSet.getString("datasets.id"));
		studies.setName(resultSet.getString("description"));
		studies.setStudyType(resultSet.getString("trial_type"));
		studies.setTrialDbId(resultSet.getString("datasets.experiment_id"));
		studies.setTrialName(resultSet.getString("trial_name"));
		studies.setStartDate(resultSet.getDate("datasets.date_start"));
		studies.setEndDate(resultSet.getDate("datasets.date_end"));
		studies.setActive(false);
		// Parse out the years
		String seasonString = resultSet.getString("years");
		if(seasonString != null)
		{
			String[] yearArray = seasonString.split(",");
			studies.setSeasons(Arrays.asList(yearArray));
		}

		BrapiLocation location = new BrapiLocation();
		location.setLocationDbId(resultSet.getString("location_id"));

		studies.setLocation(location);
		studies.setOptionalInfo(new LinkedHashMap<String, Object>());

		return studies;
	}

	public BrapiBaseResource<BrapiStudiesAsTable> getTableById(String id)
	{
		// Create empty BrapiBaseResource of type BrapiGermplasm (if for whatever reason we can't get data from the database
		// this is what's returned
		BrapiBaseResource<BrapiStudiesAsTable> result = new BrapiBaseResource<>();

		List<String> phenotypeIds = new ArrayList<>();
		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = DatabaseUtils.createByIdStatement(con, studyDetailsTablePhenotypes, id);
			 ResultSet resultSet = statement.executeQuery())
		{
			while (resultSet.next())
				phenotypeIds.add(resultSet.getString(1));
		}
		catch (SQLException e) { e.printStackTrace(); }


		// Paginate over the data by passing the currentPage and pageSize values to the code which generates the
		// prepared statement (which includes a limit statement)
		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = DatabaseUtils.createByIdStatement(con, studyDetailsTable, id);
			 ResultSet resultSet = statement.executeQuery())
		{
			BrapiStudiesAsTable studiesAsTable = new BrapiStudiesAsTable();
			studiesAsTable.setStudyDbId(id);
			studiesAsTable.setObservationVariableDbId(phenotypeIds);

			List<String> colNames = new ArrayList<>();
			for (int i=6; i <= resultSet.getMetaData().getColumnCount(); i++)
				colNames.add(resultSet.getMetaData().getColumnName(i));

			studiesAsTable.setObservationVariableName(colNames);

			List<List<String>> data = new ArrayList<>();
			while (resultSet.next())
			{
				List<String> list = new ArrayList<>();
				list.add("1");
				list.add("1");
				list.add("1");
				list.add(resultSet.getString("general_identifier"));

				for (int i=6; i <= resultSet.getMetaData().getColumnCount(); i++)
					list.add(resultSet.getString(i));

				data.add(list);
//				studiesAsTable.setStudyDbId();
			}
			studiesAsTable.setData(data);

			// Pass the currentPage and totalCount to the BrapiBaseResource constructor so we generate correct metadata
			result = new BrapiBaseResource<BrapiStudiesAsTable>(studiesAsTable);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return result;
	}
}