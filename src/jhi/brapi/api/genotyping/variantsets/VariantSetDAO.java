package jhi.brapi.api.genotyping.variantsets;

import jhi.brapi.api.*;
import jhi.brapi.api.genotyping.callsets.*;
import jhi.brapi.util.*;
import org.restlet.resource.*;

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

	private final String markerNameIdQuery = "SELECT marker_name, markers.id FROM datasetmembers LEFT JOIN markers ON " +
		"datasetmembers.foreign_id = markers.id WHERE datasetmembers.datasetmembertype_id = 1 AND dataset_id = ?";

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

	public BrapiMasterDetailResource<CallSetCalls> getVariantSetCallsById(String dataFolderPath, String id, int currentPage, int pageSize)
	{
		BrapiMasterDetailResource<CallSetCalls> result = new BrapiMasterDetailResource<>();

		String dataSetId = id;

		String filename = Hdf55Utils.getHdf5File(dataSetId);
		Hdf5DataExtractor hdf5 = new Hdf5DataExtractor(new File(dataFolderPath, filename));

		CallSetCalls calls = getVariantSetCalls(hdf5, dataSetId, currentPage, pageSize);

		result = new BrapiMasterDetailResource<CallSetCalls>(calls, currentPage, pageSize, hdf5.getMarkerCount() * hdf5.getLineCount(), true);

		return result;
	}

	// This needs to return all of the calls for a given variant set, this means each individual genotype call for each
	// line at each marker...
	private CallSetCalls getVariantSetCalls(Hdf5DataExtractor hdf5, String dataSetId, int currentPage, int pageSize)
	{
		int pageStart = DatabaseUtils.getLimitStart(currentPage, pageSize);
		int pageEnd = pageStart + pageSize;

		System.out.println("Page start: " + pageStart + " pageEnd: " + pageEnd);

		Map<String, Integer> markerIdsByName = getMarkerIdsByNameMap(dataSetId);

		CallSetCalls c = new CallSetCalls();

		int markerCount = hdf5.getMarkerCount();
		int finalLine = hdf5.getLineCount();

		System.out.println("markerCount: " + markerCount + " lineCount: " + finalLine + " M x L: " + markerCount * finalLine);

		int lineStart = (int)Math.floor(pageStart / (double)markerCount);
		int lineEnd = (int)Math.ceil((pageStart + pageSize) / (double)markerCount);

		List<String> lineNames = hdf5.getLines();
		List<String> markerNames = hdf5.getMarkers();

		List<CallSetCallsDetail> details = new ArrayList<>();

		// TODO: tidy up this logic, there has to be a better way of finding the line and markers for the current page
		// of data
		for (int lineId=lineStart; lineId < lineEnd && lineId < finalLine; lineId++)
		{
			int markerStart = lineId * markerCount;
			for (int markerIndex = 0; markerIndex < markerCount; markerIndex++)
			{
				if (markerStart + markerIndex >= pageStart && markerIndex < pageEnd)
				{
					String geno = hdf5.get(lineId, markerIndex, new GenotypeEncodingParams());
					Genotype genotype = new Genotype();
					genotype.setValues(Collections.singletonList(geno));

					CallSetCallsDetail calls = new CallSetCallsDetail();
					calls.setCallSetDbId(dataSetId + "-" + lineId);
					calls.setCallSetName(lineNames.get(lineId));
					calls.setGenotype(genotype);
					details.add(calls);

					String markerName = markerNames.get(markerIndex);
					calls.setVariantName(markerName);

					int markerId = markerIdsByName.get(markerName);
					if (markerId != -1)
						calls.setVariantDbId("" + markerId);
				}
			}
		}

		c.setData(details);

		return c;
	}

	private Map<String, Integer> getMarkerIdsByNameMap(String dataSetId)
	{
		Map<String, Integer> markerIdsByName = new HashMap<>();

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = DatabaseUtils.createByIdStatement(con, markerNameIdQuery, dataSetId);
			 ResultSet resultSet = statement.executeQuery())
		{
			while (resultSet.next())
			{
				String markerName = resultSet.getString("marker_name");
				int id = resultSet.getInt("id");

				markerIdsByName.put(markerName, id);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return markerIdsByName;
	}
}