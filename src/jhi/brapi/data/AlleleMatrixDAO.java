package jhi.brapi.data;

import java.sql.*;
import java.util.*;

import jhi.brapi.resource.*;

/**
 * Created by gs40939 on 16/06/2015.
 */
public class AlleleMatrixDAO
{
	private String allMarkers = "select genotypes.allele1, genotypes.allele2, genotypes.marker_id, " +
			"genotypes.germinatebase_id, genotypes.dataset_id, markers.marker_name from genotypes INNER JOIN markers ON " +
			"genotypes.marker_id = markers.id INNER JOIN datasets ON genotypes.dataset_id = datasets.id where ";

	private String countAllMarkers = "select COUNT(1) AS total_count, genotypes.allele1, genotypes.allele2, genotypes.marker_id, " +
	"genotypes.germinatebase_id, genotypes.dataset_id, markers.marker_name from genotypes INNER JOIN markers ON " +
	"genotypes.marker_id = markers.id INNER JOIN datasets ON genotypes.dataset_id = datasets.id where ";

	public BasicResource<BrapiAlleleMatrix> get(List<String> markerProfileDbIds, int currentPage, int pageSize)
	{
		BasicResource<BrapiAlleleMatrix> result = new BasicResource<>();

		List<Integer> datasetIds = new ArrayList<>();
		List<Integer> germinatebaseIds = new ArrayList<>();

		for (String profileId : markerProfileDbIds)
		{
			String[] tokens = profileId.split("-");
			datasetIds.add(Integer.parseInt(tokens[0]));
			germinatebaseIds.add(Integer.parseInt(tokens[1]));
		}

		StringBuilder countBuilder = new StringBuilder(countAllMarkers);
		buildInStatement(germinatebaseIds, countBuilder, "germinatebase_id IN (", ")");
		buildInStatement(datasetIds, countBuilder, " AND datasets.id IN (", ") ");

		countBuilder.append("ORDER BY marker_name, dataset_id, germinatebase_id");

		String countQuery = countBuilder.toString();

		long totalCount = -1;

		try (Connection con = Database.INSTANCE.getDataSource().getConnection();
			 PreparedStatement statement = createByIdStatement(con, countQuery, germinatebaseIds, datasetIds);
			 ResultSet resultSet = statement.executeQuery())
		{
			if (resultSet.first())
				totalCount = resultSet.getInt("total_count");
		}
		catch (SQLException e) { e.printStackTrace(); }

		if (totalCount != -1)
		{
			StringBuilder builder = new StringBuilder(allMarkers);
			buildInStatement(germinatebaseIds, builder, "germinatebase_id IN (", ")");
			buildInStatement(datasetIds, builder, " AND datasets.id IN (", ") ");

			builder.append("ORDER BY marker_name, dataset_id, germinatebase_id");
			builder.append(" LIMIT ?, ?");

			String query = builder.toString();

			try (Connection con = Database.INSTANCE.getDataSource().getConnection();
				 PreparedStatement statement = createByIdStatement(con, query, germinatebaseIds, datasetIds, currentPage, pageSize);
				 ResultSet resultSet = statement.executeQuery())
			{
				BrapiAlleleMatrix matrix = new BrapiAlleleMatrix();

				HashMap<String, List<String>> scores = new HashMap<>();
				HashSet<String> lines = new HashSet<>();

				while (resultSet.next())
				{
					String markerName = resultSet.getString("marker_name");
					String allele1 = resultSet.getString("allele1");
					String allele2 = resultSet.getString("allele2");
					String lineName = resultSet.getString("dataset_id") + "-" + resultSet.getString("germinatebase_id");

					lines.add(lineName);

					List<String> score = scores.get(markerName);
					if (score == null)
					{
						score = new ArrayList<>();
						score.add(allele1 + allele2);
						scores.put(markerName, score);
					}
					else
					{
						score.add(allele1 + allele2);
						scores.put(markerName, score);
					}
				}
				matrix.setMarkerprofileDbIds(new ArrayList<>(lines));

				List<LinkedHashMap<String, List<String>>> finalScores = new ArrayList<>();

				for (Map.Entry<String, List<String>> entry : scores.entrySet())
				{
					LinkedHashMap<String, List<String>> map = new LinkedHashMap<>();
					map.put(entry.getKey(), entry.getValue());
					finalScores.add(map);
				}

				matrix.setData(finalScores);

				result = new BasicResource<BrapiAlleleMatrix>(matrix, currentPage, pageSize, totalCount);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

		return result;
	}

	private void buildInStatement(List<Integer> germinatebaseIds, StringBuilder builder, String inClause, String closeInClause)
	{
		builder.append(inClause);
		boolean baseIdFirst = true;
		for (Integer id : germinatebaseIds)
		{
			if (baseIdFirst)
			{
				builder.append("?");
				baseIdFirst = false;
			} else
			{
				builder.append(",");
				builder.append("?");
			}
		}
		builder.append(closeInClause);
	}

	//	public BrapiAlleleMatrix get(List<String> markerProfileIds, List<String> markerIds, int currentPage, int pageSize)
//	{
//		List<Integer> datasetIds = new ArrayList<>();
//		List<Integer> germinatebaseIds = new ArrayList<>();
//
//		for (String profileId : markerProfileIds)
//		{
//			String[] tokens = profileId.split("-");
//			datasetIds.add(Integer.parseInt(tokens[0]));
//			germinatebaseIds.add(Integer.parseInt(tokens[1]));
//		}
//
//		StringBuilder builder = new StringBuilder(allMarkers);
//		builder.append("germinatebase_id IN (");
//		boolean baseIdFirst = true;
//		for (Integer id : germinatebaseIds)
//		{
//			if (baseIdFirst)
//			{
//				builder.append("?");
//				baseIdFirst = false;
//			}
//			else
//			{
//				builder.append(",");
//				builder.append("?");
//			}
//		}
//		builder.append(")");
//
//		builder.append(" AND datasets.id IN (");
//		boolean datasetIdFirst = true;
//		for (Integer id : datasetIds)
//		{
//			if (datasetIdFirst)
//			{
//				builder.append("?");
//				datasetIdFirst = false;
//			}
//			else
//			{
//				builder.append(",");
//				builder.append("?");
//			}
//		}
//		builder.append(") ");
//		builder.append("ORDER BY marker_name, dataset_id, germinatebase_id");
//		builder.append(" LIMIT ?, ?");
//
//		String query = builder.toString();
//
//		BrapiAlleleMatrix matrix = new BrapiAlleleMatrix();
//
//		try (Connection con = Database.INSTANCE.getDataSource().getConnection();
//			 PreparedStatement statement = createByIdStatement(con, query, germinatebaseIds, datasetIds, currentPage, pageSize);
//			 ResultSet resultSet = statement.executeQuery())
//		{
//			HashMap<String, List<String>> scores = new HashMap<>();
//			HashSet<String> lines = new HashSet<>();
//
//			while(resultSet.next())
//			{
//				String markerName = resultSet.getString("marker_name");
//				String allele1 = resultSet.getString("allele1");
//				String allele2 = resultSet.getString("allele2");
//				String lineName = resultSet.getString("dataset_id") + "-" + resultSet.getString("germinatebase_id");
//
//				lines.add(lineName);
//
//				List<String> score = scores.get(markerName);
//				if (score == null)
//				{
//					score = new ArrayList<>();
//					score.add(allele1+allele2);
//					scores.put(markerName, score);
//				}
//				else
//				{
//					score.add(allele1+allele2);
//					scores.put(markerName, score);
//				}
//			}
//			matrix.setMarkerprofileDbIds(new ArrayList<>(lines));
//			matrix.setScores(scores);
//		}
//		catch (SQLException e) { e.printStackTrace(); }
//
//		return matrix;
//	}

	private PreparedStatement createByIdStatement(Connection con, String query, List<Integer> germinatebaseIds, List<Integer> datasetIds)
		throws SQLException
	{
		// Get the basic information on the map
		PreparedStatement statement = con.prepareStatement(query);

		System.out.println(statement.toString());

		int counter = 1;
		for (Integer id : germinatebaseIds)
			statement.setInt(counter++, id);

		for (Integer id : datasetIds)
			statement.setInt(counter++, id);

		return statement;
	}

	private PreparedStatement createByIdStatement(Connection con, String query, List<Integer> germinatebaseIds, List<Integer> datasetIds, int currentPage, int pageSize)
		throws SQLException
	{
		// Get the basic information on the map
		PreparedStatement statement = con.prepareStatement(query);

		System.out.println(statement.toString());

		int counter = 1;
		for (Integer id : germinatebaseIds)
			statement.setInt(counter++, id);

		for (Integer id : datasetIds)
			statement.setInt(counter++, id);

		statement.setInt(counter++, PaginationUtils.getLowLimit(currentPage, pageSize));
		statement.setInt(counter++, pageSize);

		return statement;
	}
}