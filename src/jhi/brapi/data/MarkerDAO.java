package jhi.brapi.data;

import java.sql.*;
import java.util.*;

import jhi.brapi.resource.*;

/**
 * Specifies the public interface which any Marker data accessing classes must implement.
 */
public class MarkerDAO
{
	private final String queryPartSynonyms = " ( SELECT GROUP_CONCAT( DISTINCT synonym ORDER BY synonym ) FROM synonyms LEFT JOIN synonymtypes ON synonyms.synonymtype_id = synonymtypes.id WHERE synonymtypes.target_table = 'markers' AND synonyms.foreign_id = markers.id ) AS synonyms ";

	private final String getLines = "SELECT markers.*, " + queryPartSynonyms + " FROM markers LIMIT ?, ?";
	private final String getCountLines = "SELECT COUNT(1) AS total_count FROM markers";

	private final String getLinesByType = "SELECT markers.*, " + queryPartSynonyms + " FROM markers LEFT JOIN markertypes ON markertypes.id = markers.markertype_id WHERE markertypes.description = ? LIMIT ?, ?";
	private final String getCountLinesByType = "SELECT COUNT(1) AS total_count FROM markers LEFT JOIN markertypes ON markertypes.id = markers.markertype_id WHERE markertypes.description = ?";

	private final String getLinesByNameExact = "SELECT markers.*, " + queryPartSynonyms + " FROM markers WHERE markers.marker_name = ? LIMIT ?, ?";
	private final String getCountLinesByNameExact = "SELECT COUNT(1) AS total_count FROM markers WHERE markers.marker_name = ?";

	private final String getLinesByNameRegex = "SELECT markers.*, " + queryPartSynonyms + " FROM markers WHERE markers.marker_name LIKE ? LIMIT ?, ?";
	private final String getCountLinesByNameRegex = "SELECT COUNT(1) AS total_count FROM markers WHERE markers.marker_name LIKE ?";

	public BrapiListResource<BrapiMarker> getAll(int currentPage, int pageSize)
	{
		// Create empty BrapiBaseResource of type BrapiMarker (if for whatever reason we can't get data from the database
		// this is what's returned
		BrapiListResource<BrapiMarker> result = new BrapiListResource<>();

		long totalCount = DatabaseUtils.getTotalCount(getCountLines);

		if (totalCount != -1)
		{
			// Paginate over the data by passing the currentPage and pageSize values to the code which generates the
			// prepared statement (which includes a limit statement)
			try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
				 PreparedStatement statement = DatabaseUtils.createLimitStatement(con, getLines, currentPage, pageSize);
				 ResultSet resultSet = statement.executeQuery())
			{
				List<BrapiMarker> list = new ArrayList<>();

				while (resultSet.next())
					list.add(getBrapiMarker(resultSet));

				// Pass the currentPage and totalCount to the BrapiBaseResource constructor so we generate correct metadata
				result = new BrapiListResource<BrapiMarker>(list, currentPage, pageSize, totalCount);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

		return result;
	}

	public BrapiListResource<BrapiMarker> getByType(String type, int currentPage, int pageSize)
	{
		return getData(getCountLinesByType, getLinesByType, type, currentPage, pageSize);
	}

	private BrapiListResource<BrapiMarker> getData(String countSql, String dataSql, String parameter, int currentPage, int pageSize)
	{
		BrapiListResource<BrapiMarker> result = new BrapiListResource<>();

		long totalCount = DatabaseUtils.getTotalCountById(countSql, parameter);

		if (totalCount != -1)
		{
			// Paginate over the data by passing the currentPage and pageSize values to the code which generates the
			// prepared statement (which includes a limit statement)
			try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
				 PreparedStatement statement = DatabaseUtils.createByIdLimitStatement(con, dataSql, parameter, currentPage, pageSize);
				 ResultSet resultSet = statement.executeQuery())
			{
				List<BrapiMarker> markers = new ArrayList<>();

				while (resultSet.next())
				{
					markers.add(getBrapiMarker(resultSet));
				}

				// Pass the currentPage and totalCount to the BrapiBaseResource constructor so we generate correct metadata
				result = new BrapiListResource<BrapiMarker>(markers, currentPage, pageSize, totalCount);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

		return result;
	}

	public BrapiListResource<BrapiMarker> getByName(String name, BrapiGermplasm.MatchingMethod matchingMethod, int currentPage, int pageSize)
	{
		String countQuery;
		String getQuery;

		switch (matchingMethod)
		{
			case WILDCARD:
				countQuery = getCountLinesByNameRegex;
				getQuery = getLinesByNameRegex;
				name = name.replace("*", "%");
				name = name.replace("?", "_");
				break;
			default:
				countQuery = getCountLinesByNameExact;
				getQuery = getLinesByNameExact;
		}

		return getData(countQuery, getQuery, name, currentPage, pageSize);
	}

	private BrapiMarker getBrapiMarker(ResultSet resultSet)
			throws SQLException
	{
		BrapiMarker marker = new BrapiMarker();
		marker.setMarkerDbId(resultSet.getString("markers.id"));
		marker.setDefaultDisplayName(resultSet.getString("markers.marker_name"));

		// Parse out the synonyms
		String synonymsString = resultSet.getString("synonyms");
		if(synonymsString != null)
		{
			String[] synonymsArray = synonymsString.split(",");
			marker.setSynonyms(Arrays.asList(synonymsArray));
		}

		return marker;
	}
}
