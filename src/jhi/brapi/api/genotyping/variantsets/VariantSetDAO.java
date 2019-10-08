package jhi.brapi.api.genotyping.variantsets;

import jhi.brapi.api.*;
import jhi.brapi.util.*;

import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * Specifies the public interface which any BrapiGenomeMap data accessing classes must implement.
 */
public class VariantSetDAO
{
	private final String variantSetsQuery = "SELECT SQL_CALC_FOUND_ROWS * FROM datasets LEFT JOIN experiments ON " +
		"experiments.id = datasets.experiment_id LEFT JOIN experimenttypes ON " +
		"experimenttypes.id = experiments.experiment_type_id WHERE experimenttypes.id = 1 AND datasets.source_file IS NOT NULL LIMIT ?, ?";

	private final String variantSetsByIdQuery = "SELECT * FROM datasets LEFT JOIN experiments ON " +
		"experiments.id = datasets.experiment_id LEFT JOIN experimenttypes ON " +
		"experimenttypes.id = experiments.experiment_type_id WHERE experimenttypes.id = 1 AND datasets.id = ? AND " +
		"datasets.source_file IS NOT NULL";

	/**
	 * Queries the database (using mapQuery defined above) for the complete list of Maps which the database holds.
	 *
	 * @return A MapList object which is a wrapper around a List of BrapiGenomeMap objects.
	 */
	public BrapiListResource<VariantSet> getAll(String dataFolderPath, int currentPage, int pageSize)
	{
		BrapiListResource<VariantSet> variantSets = new BrapiListResource<>();

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = DatabaseUtils.createLimitStatement(con, variantSetsQuery, currentPage, pageSize);
			 ResultSet resultSet = statement.executeQuery())
		{
			List<VariantSet> list = new ArrayList<>();

			while (resultSet.next())
				list.add(getVariantSetFrom(dataFolderPath, resultSet));

			long totalCount = DatabaseUtils.getTotalCount(statement);

			// Pass the currentPage and totalCount to the BrapiBaseResource constructor so we generate correct metadata
			variantSets = new BrapiListResource<VariantSet>(list, currentPage, pageSize, totalCount);

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return variantSets;
	}

	public BrapiBaseResource<VariantSet> getById(String dataFolderPath, String id)
	{
		BrapiBaseResource<VariantSet> variantSet = new BrapiBaseResource<>();

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = DatabaseUtils.createByIdStatement(con, variantSetsByIdQuery, id);
			 ResultSet resultSet = statement.executeQuery())
		{
			if (resultSet.next())
				variantSet = new BrapiBaseResource<>(getVariantSetFrom(dataFolderPath, resultSet));

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return variantSet;
	}

	private VariantSet getVariantSetFrom(String dataFolderPath, ResultSet resultSet)
		throws SQLException
	{
		VariantSet variantSet = new VariantSet();

		variantSet.setVariantSetDbId(resultSet.getString("datasets.id"));
		variantSet.setStudyDbId(resultSet.getString("datasets.id"));
		variantSet.setVariantSetName(resultSet.getString("description"));

		String filename = Hdf55Utils.getHdf5File(variantSet.getVariantSetDbId());
		Hdf5DataExtractor hdf5 = new Hdf5DataExtractor(new File(dataFolderPath, filename));

		variantSet.setCallSetCount(hdf5.getLineCount());
		variantSet.setVariantCount(hdf5.getMarkerCount());

		return variantSet;
	}
}