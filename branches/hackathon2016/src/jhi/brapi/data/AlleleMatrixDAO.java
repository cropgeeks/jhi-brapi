package jhi.brapi.data;

import java.io.*;
import java.sql.*;
import java.util.*;

import jhi.brapi.resource.*;

/**
 * Created by gs40939 on 16/06/2015.
 */
public class AlleleMatrixDAO
{
	private String allMarkers = "select genotypes.allele1, genotypes.allele2, genotypes.marker_id, " +
			"genotypes.germinatebase_id, genotypes.dataset_id, markers.id from genotypes INNER JOIN markers ON " +
			"genotypes.marker_id = markers.id INNER JOIN datasets ON genotypes.dataset_id = datasets.id where germinatebase_id IN (%s) AND datasets.id IN (%s) ORDER BY markers.marker_name, dataset_id, germinatebase_id LIMIT ?, ?";

	private String countAllMarkers = "select COUNT(1) AS total_count, genotypes.allele1, genotypes.allele2, genotypes.marker_id, " +
			"genotypes.germinatebase_id, genotypes.dataset_id, markers.id from genotypes INNER JOIN markers ON " +
			"genotypes.marker_id = markers.id INNER JOIN datasets ON genotypes.dataset_id = datasets.id where ";

	public BasicResource<BrapiAlleleMatrix> get(List<String> markerProfileDbIds, String format, String unknownString, String sepPhased, String sepUnphased, int currentPage, int pageSize)
	{
		if (format != null)
		{
			currentPage = 0;
			pageSize = Integer.MAX_VALUE;
		}

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

		String countQuery = countBuilder.toString();

		long totalCount = -1;

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = createByIdStatement(con, countQuery, germinatebaseIds, datasetIds);
			 ResultSet resultSet = statement.executeQuery())
		{
			if (resultSet.first())
				totalCount = resultSet.getInt("total_count");
		}
		catch (SQLException e) { e.printStackTrace(); }

		if (totalCount != -1)
		{
			try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
				 PreparedStatement statement = DatabaseUtils.createInLimitStatement(con, allMarkers, currentPage, pageSize, germinatebaseIds, datasetIds);
				 ResultSet resultSet = statement.executeQuery())
			{
				if (format != null)
				{
					result = getMatrixForFile(resultSet, markerProfileDbIds, unknownString, sepPhased, sepUnphased);
				}
				else
				{
					result = new BasicResource<BrapiAlleleMatrix>(getMatrixForJson(resultSet, unknownString, sepPhased, sepUnphased), currentPage, pageSize, totalCount);
				}
			}
			catch (SQLException | IOException e)
			{
				e.printStackTrace();
			}
		}

		return result;
	}

	private BasicResource<BrapiAlleleMatrix> getMatrixForFile(ResultSet resultSet, List<String> markerProfileDbIds, String unknownString, String sepPhased, String sepUnphased)
			throws SQLException, IOException
	{
		BrapiAlleleMatrix matrix = new BrapiAlleleMatrix();

		LinkedHashMap<String, List<String>> scores = new LinkedHashMap<>();
		LinkedHashSet<String> lines = new LinkedHashSet<>();

		File file = File.createTempFile("allelematrix", ".tsv");

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file)))
		{
			bw.write("markerprofileDbIds");

			for (String markerprofileDbId : markerProfileDbIds)
				bw.write("\t" + markerprofileDbId);

			String previousMarkerName = null;
			while (resultSet.next())
			{
				String markerName = resultSet.getString("markers.id");
				String allele1 = resultSet.getString("allele1");
				String allele2 = resultSet.getString("allele2");

				if (!Objects.equals(previousMarkerName, markerName))
				{
					previousMarkerName = markerName;
					bw.newLine();
					bw.write(markerName);
				}

				bw.write("\t" + getString(true, allele1, allele2, unknownString, sepPhased, sepUnphased));
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		/*
		 * The sorting is required because the first marker may have been split across two pages. In that case, the markerprofiles won't be
		 * in the correct order, as the code will start with the rest of this marker and then jump to the next
		 */
		List<String> linesSorted = new ArrayList<>(lines);
		Collections.sort(linesSorted);
		matrix.setMarkerprofileDbIds(linesSorted);
		matrix.setData(new ArrayList<>());

		for (Map.Entry<String, List<String>> entry : scores.entrySet())
		{
			LinkedHashMap<String, List<String>> map = new LinkedHashMap<>();
			map.put(entry.getKey(), entry.getValue());
		}

//				finalScores.add(scores);

		BasicResource<BrapiAlleleMatrix> result = new BasicResource<BrapiAlleleMatrix>(matrix, 0, 1, 1);
		Datafile datafile = new Datafile("https://ics.hutton.ac.uk/brapi/cactuar/v1/files/" + file.getName()); // TODO: get absolute URL
		result.getMetadata().setDatafiles(Collections.singletonList(datafile));
		return result;
	}

	private BrapiAlleleMatrix getMatrixForJson(ResultSet resultSet, String unknownString, String sepPhased, String sepUnphased)
			throws SQLException
	{
		BrapiAlleleMatrix matrix = new BrapiAlleleMatrix();

		LinkedHashMap<String, List<String>> scores = new LinkedHashMap<>();
		LinkedHashSet<String> lines = new LinkedHashSet<>();

		while (resultSet.next())
		{
			String markerName = resultSet.getString("markers.id");
			String allele1 = resultSet.getString("allele1");
			String allele2 = resultSet.getString("allele2");
			String lineName = resultSet.getString("dataset_id") + "-" + resultSet.getString("germinatebase_id");

			lines.add(lineName);

			List<String> score = scores.get(markerName);
			String call = getString(true, allele1, allele2, unknownString, sepPhased, sepUnphased);
			if (score == null)
			{
				score = new ArrayList<>();
				score.add(call);
//				score.add(allele1 + allele2);
				scores.put(markerName, score);
			}
			else
			{
				score.add(call);
//				score.add(allele1 + allele2);
				scores.put(markerName, score);
			}
		}

		/*
		 * The sorting is required because the first marker may have been split across two pages. In that case, the markerprofiles won't be
		 * in the correct order, as the code will start with the rest of this marker and then jump to the next
		 */
		List<String> linesSorted = new ArrayList<>(lines);
		Collections.sort(linesSorted);
		matrix.setMarkerprofileDbIds(linesSorted);

		List<LinkedHashMap<String, List<String>>> finalScores = new ArrayList<>();

		for (Map.Entry<String, List<String>> entry : scores.entrySet())
		{
			LinkedHashMap<String, List<String>> map = new LinkedHashMap<>();
			map.put(entry.getKey(), entry.getValue());
			finalScores.add(map);
		}

//				finalScores.add(scores);

		matrix.setData(finalScores);

		return matrix;
	}

	// TODO: Get the parameter from the request
	private String getString(boolean collapse, String allele1, String allele2, String unknownString, String sepPhased, String sepUnphased)
	{
		allele1 = fixAllele(allele1, unknownString); // Replace empty string and dash into the unknownString
		allele2 = fixAllele(allele2, unknownString); // Replace empty string and dash into the unknownString

		if (Objects.equals(allele1, unknownString) && Objects.equals(allele2, unknownString)) // If both are unknown return empty string
			return "";
		else if (Objects.equals(allele1, unknownString)) // If only the first is unknown, return the second
			return allele2;
		else if (Objects.equals(allele1, unknownString)) // If only the second is unknown, return the first
			return allele1;
		else if (Objects.equals(allele1, allele2)) // If both are the same, return the first
		{
			if(collapse)
				return allele1;
			else
				return allele1 + sepUnphased + allele2;
		}
		else // Else combine them
			return allele1 + sepUnphased + allele2;
	}

	private String fixAllele(String input, String unknownString)
	{
		if (Objects.equals(input, "") || Objects.equals(input, "-"))
			return unknownString;
		else
			return input;
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
			}
			else
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