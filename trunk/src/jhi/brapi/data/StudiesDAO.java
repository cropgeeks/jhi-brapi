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
			try (Connection con = Database.INSTANCE.getDataSource().getConnection();
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
}
