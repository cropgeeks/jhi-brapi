package jhi.brapi.data;

import jhi.brapi.resource.*;

import java.sql.*;
import java.util.*;

/**
 * Specifies the public interface which any Germplasm data accessing classes must implement.
 */
public class StudiesDAO
{
	// Simply selects all fields from germinatebase
	private final String getStudies = "SELECT datasets.*, (SELECT GROUP_CONCAT(DISTINCT YEAR (recording_date)) FROM phenotypedata WHERE phenotypedata.dataset_id = datasets.id) AS years FROM phenotypedata LEFT JOIN datasets ON datasets.id = phenotypedata.dataset_id LEFT JOIN experiments ON experiments.id = datasets.experiment_id LEFT JOIN experimenttypes ON experimenttypes.id = experiments.experiment_type_id WHERE experimenttypes.description = \"trials\" %s GROUP BY datasets.id LIMIT ?, ?";

	private final String getCountStudies = "SELECT COUNT(DISTINCT datasets.id) AS total_count FROM phenotypedata LEFT JOIN datasets ON datasets.id = phenotypedata.dataset_id LEFT JOIN experiments ON experiments.id = datasets.experiment_id LEFT JOIN experimenttypes ON experimenttypes.id = experiments.experiment_type_id WHERE experimenttypes.description = \"trials\" GROUP BY datasets.id %s";

	private final String studyDetailsTable = "call phenotype_data_complete (?)";

	private final String studyDetailsTablePhenotypes = "select DISTINCT(phenotypes.id) from phenotypes LEFT JOIN phenotypedata ON phenotypes.id = phenotypedata.phenotype_id WHERE dataset_id = ?";

	public BasicResource<DataResult<BrapiStudies>> getAll(int currentPage, int pageSize, String programId, String locationId, String seasonId)
	{
		// Create empty BasicResource of type BrapiGermplasm (if for whatever reason we can't get data from the database
		// this is what's returned
		BasicResource<DataResult<BrapiStudies>> result = new BasicResource<>();

		// TODO: Can we do this kind of concept in a generic way somewhere?
		StringBuilder builder = new StringBuilder();
		if (programId != null && !programId.isEmpty())
			builder.append(" AND experiments.id = ").append(programId);
		if (locationId != null && !locationId.isEmpty())
			builder.append(" AND datasets.location_id = ").append(locationId);
		if (seasonId != null && !seasonId.isEmpty())
			builder.append(" AND YEAR(phenotypedata.recording_date) = ").append(seasonId);

		String finalCountStudies = String.format(getCountStudies, builder.toString());
		String finalGetStudies = String.format(getStudies, builder.toString());

		long totalCount = DatabaseUtils.getTotalCount(finalCountStudies);

		if (totalCount != -1)
		{
			// Paginate over the data by passing the currentPage and pageSize values to the code which generates the
			// prepared statement (which includes a limit statement)
			try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
				 PreparedStatement statement = DatabaseUtils.createLimitStatement(con, finalGetStudies, currentPage, pageSize);
				 ResultSet resultSet = statement.executeQuery())
			{
				List<BrapiStudies> list = new ArrayList<>();

				while (resultSet.next())
					list.add(getBrapiStudies(resultSet));

				// Pass the currentPage and totalCount to the BasicResource constructor so we generate correct metadata
				result = new BasicResource<DataResult<BrapiStudies>>(new DataResult(list), currentPage, pageSize, totalCount);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

		return result;
	}

	private BrapiStudies getBrapiStudies(ResultSet resultSet)
			throws SQLException
	{
		BrapiStudies studies = new BrapiStudies();
		studies.setStudyDbId(resultSet.getString("datasets.id"));
		studies.setStudyName(resultSet.getString("description"));
		studies.setLocationDbId(resultSet.getString("location_id"));
		studies.setOptionalInfo(new LinkedHashMap<String, Object>());

		// Parse out the years
		String yearString = resultSet.getString("years");
		String[] yearArray = yearString.split(",");
		studies.setYears(Arrays.asList(yearArray));

		return studies;
	}

	public BasicResource<BrapiStudiesAsTable> getTableById(String id)
	{
		// Create empty BasicResource of type BrapiGermplasm (if for whatever reason we can't get data from the database
		// this is what's returned
		BasicResource<BrapiStudiesAsTable> result = new BasicResource<>();

		System.out.println("ID: " + id);

		List<String> phenotypeIds = new ArrayList<>();
		try (Connection con = Database.INSTANCE.getDataSource().getConnection();
			 PreparedStatement statement = DatabaseUtils.createByIdStatement(con, studyDetailsTablePhenotypes, id);
			 ResultSet resultSet = statement.executeQuery())
		{
			while (resultSet.next())
				phenotypeIds.add(resultSet.getString(1));
		}
		catch (SQLException e) { e.printStackTrace(); }


		// Paginate over the data by passing the currentPage and pageSize values to the code which generates the
		// prepared statement (which includes a limit statement)
		try (Connection con = Database.INSTANCE.getDataSource().getConnection();
			 PreparedStatement statement = DatabaseUtils.createByIdStatement(con, studyDetailsTable, id);
			 ResultSet resultSet = statement.executeQuery())
		{
			BrapiStudiesAsTable studiesAsTable = new BrapiStudiesAsTable();
			studiesAsTable.setStudyDbId(id);
			studiesAsTable.setObservationVariableDbId(phenotypeIds);

			List<String> colNames = new ArrayList<>();
			for (int i=5; i <= resultSet.getMetaData().getColumnCount(); i++)
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

				for (int i=5; i <= resultSet.getMetaData().getColumnCount(); i++)
					list.add(resultSet.getString(i));

				data.add(list);
//				studiesAsTable.setStudyDbId();
			}
			studiesAsTable.setData(data);

			// Pass the currentPage and totalCount to the BasicResource constructor so we generate correct metadata
			result = new BasicResource<BrapiStudiesAsTable>(studiesAsTable);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return result;
	}
}
