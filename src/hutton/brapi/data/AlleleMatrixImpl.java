package hutton.brapi.data;

import hutton.brapi.resource.AlleleMatrix;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by gs40939 on 16/06/2015.
 */
public class AlleleMatrixImpl implements AlleleMatrixDAO
{
	private String allMarkers = "select genotypes.allele1, genotypes.allele2, genotypes.marker_id, " +
		"genotypes.germinatebase_id, genotypes.dataset_id, markers.marker_name from genotypes INNER JOIN markers ON " +
		"genotypes.marker_id = markers.id INNER JOIN datasets ON genotypes.dataset_id = datasets.id where ";

	@Override
	public AlleleMatrix get(List<String> markerProfileIds)
	{
		List<Integer> datasetIds = new ArrayList<>();
		List<Integer> germinatebaseIds = new ArrayList<>();

		for (String profileId : markerProfileIds)
		{
			String[] tokens = profileId.split("-");
			datasetIds.add(Integer.parseInt(tokens[0]));
			germinatebaseIds.add(Integer.parseInt(tokens[1]));
		}

		StringBuilder builder = new StringBuilder(allMarkers);
		builder.append("germinatebase_id IN (");
		boolean baseIdFirst = true;
		for (Integer id : germinatebaseIds)
		{
			if (baseIdFirst)
			{
				builder.append("?");
				baseIdFirst = false;
			}
			else
			{
				builder.append(",");
				builder.append("?");
			}
		}
		builder.append(")");

		builder.append(" AND datasets.id IN (");
		boolean datasetIdFirst = true;
		for (Integer id : datasetIds)
		{
			if (datasetIdFirst)
			{
				builder.append("?");
				datasetIdFirst = false;
			}
			else
			{
				builder.append(",");
				builder.append("?");
			}
		}
		builder.append(") ");
		builder.append("ORDER BY marker_name, dataset_id, germinatebase_id");

		String query = builder.toString();

		AlleleMatrix matrix = new AlleleMatrix();

		try (Connection con = Database.INSTANCE.getDataSource().getConnection();
			 PreparedStatement statement = createByIdStatement(con, query, germinatebaseIds, datasetIds);
			 ResultSet resultSet = statement.executeQuery())
		{
			HashMap<String, List<String>> scores = new HashMap<>();
			HashSet<String> lines = new HashSet<>();

			while(resultSet.next())
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
					score.add(allele1+allele2);
					scores.put(markerName, score);
				}
				else
				{
					score.add(allele1+allele2);
					scores.put(markerName, score);
				}
			}
			matrix.setMarkerprofileIds(new ArrayList<>(lines));
			matrix.setScores(scores);
		}
		catch (SQLException e) { e.printStackTrace(); }

		return matrix;
	}

	@Override
	public AlleleMatrix get(List<String> markerProfileIds, List<String> markerIds)
	{
		List<Integer> datasetIds = new ArrayList<>();
		List<Integer> germinatebaseIds = new ArrayList<>();

		for (String profileId : markerProfileIds)
		{
			String[] tokens = profileId.split("-");
			datasetIds.add(Integer.parseInt(tokens[0]));
			germinatebaseIds.add(Integer.parseInt(tokens[1]));
		}

		StringBuilder builder = new StringBuilder(allMarkers);
		builder.append("germinatebase_id IN (");
		boolean baseIdFirst = true;
		for (Integer id : germinatebaseIds)
		{
			if (baseIdFirst)
			{
				builder.append("?");
				baseIdFirst = false;
			}
			else
			{
				builder.append(",");
				builder.append("?");
			}
		}
		builder.append(")");

		builder.append(" AND datasets.id IN (");
		boolean datasetIdFirst = true;
		for (Integer id : datasetIds)
		{
			if (datasetIdFirst)
			{
				builder.append("?");
				datasetIdFirst = false;
			}
			else
			{
				builder.append(",");
				builder.append("?");
			}
		}
		builder.append(") ");
		builder.append("ORDER BY marker_name, dataset_id, germinatebase_id");

		String query = builder.toString();

		AlleleMatrix matrix = new AlleleMatrix();

		try (Connection con = Database.INSTANCE.getDataSource().getConnection();
			 PreparedStatement statement = createByIdStatement(con, query, germinatebaseIds, datasetIds);
			 ResultSet resultSet = statement.executeQuery())
		{
			HashMap<String, List<String>> scores = new HashMap<>();
			HashSet<String> lines = new HashSet<>();

			while(resultSet.next())
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
					score.add(allele1+allele2);
					scores.put(markerName, score);
				}
				else
				{
					score.add(allele1+allele2);
					scores.put(markerName, score);
				}
			}
			matrix.setMarkerprofileIds(new ArrayList<>(lines));
			matrix.setScores(scores);
		}
		catch (SQLException e) { e.printStackTrace(); }

		return matrix;
	}

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
}