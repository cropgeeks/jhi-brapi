package jhi.brapi.data;

import jhi.brapi.resource.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Specifies the public interface which any Germplasm data accessing classes must implement.
 */
public class StudiesDAO
{
	// Simply selects all fields from germinatebase
	private final String getStudies = "SELECT datasets.*, GROUP_CONCAT(DISTINCT YEAR(recording_date) ORDER BY recording_date SEPARATOR \",\") AS years FROM phenotypedata LEFT JOIN datasets ON datasets.id = phenotypedata.dataset_id LEFT JOIN experiments ON experiments.id = datasets.experiment_id LEFT JOIN experimenttypes ON experimenttypes.id = experiments.experiment_type_id WHERE experimenttypes.description = \"trials\" GROUP BY datasets.id LIMIT ?, ?";

	private final String getCountStudies = "SELECT COUNT(DISTINCT datasets.id) AS total_count FROM phenotypedata LEFT JOIN datasets ON datasets.id = phenotypedata.dataset_id LEFT JOIN experiments ON experiments.id = datasets.experiment_id LEFT JOIN experimenttypes ON experimenttypes.id = experiments.experiment_type_id WHERE experimenttypes.description = \"trials\" GROUP BY datasets.id";

	public BasicResource<DataResult<BrapiStudies>> getAll(int currentPage, int pageSize)
	{
		// Create empty BasicResource of type BrapiGermplasm (if for whatever reason we can't get data from the database
		// this is what's returned
		BasicResource<DataResult<BrapiStudies>> result = new BasicResource<>();

		long totalCount = DatabaseUtils.getTotalCount(getCountStudies);

		if (totalCount != -1)
		{
			// Paginate over the data by passing the currentPage and pageSize values to the code which generates the
			// prepared statement (which includes a limit statement)
			try (Connection con = Database.INSTANCE.getDataSource().getConnection();
				 PreparedStatement statement = DatabaseUtils.createLimitStatement(con, getStudies, currentPage, pageSize);
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
