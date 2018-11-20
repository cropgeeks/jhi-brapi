package jhi.brapi.api.allelematrices;

import java.io.*;
import java.sql.*;
import java.util.*;

import jhi.brapi.api.*;
import jhi.brapi.util.*;

import org.restlet.*;

public class AlleleMatriceDAO
{
	private final String getMatrices = "SELECT SQL_CALC_FOUND_ROWS * FROM datasets LEFT JOIN experiments ON experiments.id = datasets.experiment_id LEFT JOIN experimenttypes ON experimenttypes.id = experiments.experiment_type_id %s AND datasets.source_file IS NOT NULL LIMIT ?, ?";

	public BrapiListResource<BrapiAlleleMatrixDataset> getAll(Context context, Map<String, List<String>> parameters, int currentPage, int pageSize)
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
				list.add(getBrapiAlleleMatrice(context, resultSet));

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

	private BrapiAlleleMatrixDataset getBrapiAlleleMatrice(Context context, ResultSet resultSet)
		throws SQLException
	{
		BrapiAlleleMatrixDataset alleleMatrice = new BrapiAlleleMatrixDataset();

		alleleMatrice.setMatrixDbId(resultSet.getString("datasets.id"));
		alleleMatrice.setStudyDbId(resultSet.getString("datasets.id"));
		alleleMatrice.setName(resultSet.getString("description"));
		alleleMatrice.setDescription(resultSet.getString("description"));
		alleleMatrice.setLastUpdated(resultSet.getDate("updated_on"));

		// TODO: Currently the only way to get information about the number of markers and number of samples in
		// a dataset is by querying the associated hdf5 file
		String filename = HDF5Utils.getHdf5File(resultSet.getString("datasets.id"));
		String folder = context.getParameters().getFirstValue("hdf5-folder");

		Hdf5DataExtractor extractor = new Hdf5DataExtractor(new File(folder, filename));

		alleleMatrice.setMarkerCount(extractor.getMarkers().size());
		alleleMatrice.setSampleCount(extractor.getLines().size());
		extractor.close();

		return alleleMatrice;
	}
}
