package jhi.brapi.api.markers;

import java.sql.*;
import java.util.*;

import jhi.brapi.api.*;
import jhi.brapi.api.germplasm.BrapiGermplasm.*;
import jhi.brapi.util.*;

/**
 * Specifies the public interface which any Marker data accessing classes must implement.
 */
public class MarkerDAO
{
	private final String queryPartSynonyms = " ( SELECT GROUP_CONCAT( DISTINCT synonym ORDER BY synonym ) FROM synonyms LEFT JOIN synonymtypes ON synonyms.synonymtype_id = synonymtypes.id WHERE synonymtypes.target_table = 'markers' AND synonyms.foreign_id = markers.id ) AS synonyms ";

	private final String getMarkers = "SELECT SQL_CALC_FOUND_ROWS markers.*, genotypes.allele1, genotypes.allele2, markertypes.description," + queryPartSynonyms + " FROM markers LEFT JOIN genotypes ON genotypes.id = markers.id LEFT JOIN markertypes ON markertypes.id = markers.markertype_id LIMIT ?, ?";

	private final String getMarkersByType = "SELECT SQL_CALC_FOUND_ROWS markers.*, " + queryPartSynonyms + " FROM markers LEFT JOIN markertypes ON markertypes.id = markers.markertype_id WHERE markertypes.description = ? LIMIT ?, ?";

	private final String getMarkersByNameExact = "SELECT SQL_CALC_FOUND_ROWS markers.*, " + queryPartSynonyms + " FROM markers WHERE markers.marker_name = ? LIMIT ?, ?";

	private final String getMarkersByNameRegex = "SELECT SQL_CALC_FOUND_ROWS markers.*, " + queryPartSynonyms + " FROM markers WHERE markers.marker_name LIKE ? LIMIT ?, ?";

	private final String getMarkerById = "SELECT SQL_CALC_FOUND_ROWS markers.*, genotypes.allele1, genotypes.allele2, markertypes.description," + queryPartSynonyms + " FROM markers LEFT JOIN genotypes ON genotypes.id = markers.id LEFT JOIN markertypes ON markertypes.id = markers.markertype_id WHERE markers.id = ?";

	public BrapiListResource<BrapiMarker> getAll(int currentPage, int pageSize)
	{
		// Create empty BrapiBaseResource of type BrapiMarker (if for whatever reason we can't get data from the database
		// this is what's returned
		BrapiListResource<BrapiMarker> result = new BrapiListResource<>();

		// Paginate over the data by passing the currentPage and pageSize values to the code which generates the
		// prepared statement (which includes a limit statement)
		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = DatabaseUtils.createLimitStatement(con, getMarkers, currentPage, pageSize);
			 ResultSet resultSet = statement.executeQuery())
		{
			List<BrapiMarker> list = new ArrayList<>();

			while (resultSet.next())
				list.add(getBrapiMarker(resultSet));

			long totalCount = DatabaseUtils.getTotalCount(statement);

			// Pass the currentPage and totalCount to the BrapiBaseResource constructor so we generate correct metadata
			result = new BrapiListResource<BrapiMarker>(list, currentPage, pageSize, totalCount);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return result;
	}

	public BrapiListResource<BrapiMarker> getByType(String type, int currentPage, int pageSize)
	{
		return getData(getMarkersByType, type, currentPage, pageSize);
	}

	private BrapiListResource<BrapiMarker> getData(String dataSql, String parameter, int currentPage, int pageSize)
	{
		BrapiListResource<BrapiMarker> result = new BrapiListResource<>();

		// Paginate over the data by passing the currentPage and pageSize values to the code which generates the
		// prepared statement (which includes a limit statement)
		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = DatabaseUtils.createByIdLimitStatement(con, dataSql, parameter, currentPage, pageSize);
			 ResultSet resultSet = statement.executeQuery())
		{
			List<BrapiMarker> markers = new ArrayList<>();
			while (resultSet.next())
				markers.add(getBrapiMarker(resultSet));

			long totalCount = DatabaseUtils.getTotalCount(statement);

			// Pass the currentPage and totalCount to the BrapiBaseResource constructor so we generate correct metadata
			result = new BrapiListResource<BrapiMarker>(markers, currentPage, pageSize, totalCount);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return result;
	}

	public BrapiListResource<BrapiMarker> getByName(String name, MatchingMethod matchingMethod, int currentPage, int pageSize)
	{
		String getQuery;

		switch (matchingMethod)
		{
			case WILDCARD:
				getQuery = getMarkersByNameRegex;
				name = name.replace("*", "%");
				name = name.replace("?", "_");
				break;
			default:
				getQuery = getMarkersByNameExact;
		}

		return getData(getQuery, name, currentPage, pageSize);
	}

	public BrapiBaseResource<BrapiMarker> getById(String id)
	{
		BrapiBaseResource<BrapiMarker> result = new BrapiBaseResource<>();

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statemet = DatabaseUtils.createByIdStatement(con, getMarkerById, id);
			 ResultSet resultSet = statemet.executeQuery())
		{
			if (resultSet.next())
				result = new BrapiBaseResource<>(getBrapiMarker(resultSet));
		}
		catch (SQLException e) { e.printStackTrace(); }

		return result;
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

		String allele1 = resultSet.getString("allele1");
		String allele2 = resultSet.getString("allele2");
		if (allele1 != null && allele2 != null)
		{
			List<String> refAlt = new ArrayList<>();
			refAlt.add(allele1);
			refAlt.add(allele2);
			marker.setRefAlt(refAlt);
		}

		marker.setType(resultSet.getString("description"));

		return marker;
	}
}