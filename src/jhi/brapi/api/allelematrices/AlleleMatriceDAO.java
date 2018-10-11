package jhi.brapi.api.allelematrices;

import java.sql.*;
import java.util.*;

import jhi.brapi.api.*;
import jhi.brapi.util.*;

public class AlleleMatriceDAO
{
	private final String getMatrices = "SELECT SQL_CALC_FOUND_ROWS * FROM datasets LEFT JOIN experiments ON experiments.id = datasets.experiment_id LEFT JOIN experimenttypes ON experimenttypes.id = experiments.experiment_type_id %s LIMIT ?, ?";

	public BrapiListResource<BrapiAlleleMatrixDataset> getAll(Map<String, List<String>> parameters, int currentPage, int pageSize)
	{
		// Create empty BrapiBaseResource of type BrapiAlleleMatrixDataset (if for whatever reason we can't get data from the database
		// this is what's returned
		BrapiListResource<BrapiAlleleMatrixDataset> result = new BrapiListResource<>();

		// Paginate over the data by passing the currentPage and pageSize values to the code which generates the
		// prepared statement (which includes a limit statement)
		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = DatabaseUtils.createParameterizedLimitStatement(con, getMatrices, parameters, currentPage, pageSize);
			 ResultSet resultSet = statement.executeQuery())
		{
			List<BrapiAlleleMatrixDataset> list = new ArrayList<>();

			while (resultSet.next())
				list.add(getBrapiAlleleMatrice(resultSet));

			long totalCount = DatabaseUtils.getTotalCount(statement);

			// Pass the currentPage and totalCount to the BrapiBaseResource constructor so we generate correct metadata
			result = new BrapiListResource<BrapiAlleleMatrixDataset>(list, currentPage, pageSize, totalCount);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return result;
	}

	private BrapiAlleleMatrixDataset getBrapiAlleleMatrice(ResultSet resultSet)
		throws SQLException
	{
		BrapiAlleleMatrixDataset alleleMatrice = new BrapiAlleleMatrixDataset();

		alleleMatrice.setMatrixDbId(resultSet.getString("datasets.id"));
		alleleMatrice.setStudyDbId(resultSet.getString("datasets.id"));
		alleleMatrice.setName(resultSet.getString("description"));
		alleleMatrice.setDescription(resultSet.getString("description"));
		alleleMatrice.setLastUpdated(resultSet.getDate("updated_on"));

		return alleleMatrice;
	}
}
