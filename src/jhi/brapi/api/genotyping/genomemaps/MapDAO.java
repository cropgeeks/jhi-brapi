package jhi.brapi.api.genotyping.genomemaps;

import java.sql.*;
import java.time.*;
import java.util.*;

import jhi.brapi.api.*;
import jhi.brapi.util.*;

/**
 * Specifies the public interface which any BrapiGenomeMap data accessing classes must implement.
 */
public class MapDAO
{
	// Selects the id, description and created_date from the MapList table, and carries out an inner join with the
	// mapdefinitions table so we can get a count of markers (by marker_id) and chromosomes.
	private final String mapsQuery = "SELECT SQL_CALC_FOUND_ROWS maps.id, maps.description, maps.created_on, COUNT(DISTINCT " +
			"mapdefinitions.marker_id) AS markercount, COUNT(DISTINCT mapdefinitions.chromosome) AS chromosomeCount " +
			"from maps INNER JOIN mapdefinitions ON maps.id = mapdefinitions.map_id GROUP BY maps.id %s LIMIT ?, ?";

	private final String mapById = "SELECT SQL_CALC_FOUND_ROWS maps.id, maps.description, maps.created_on, COUNT(DISTINCT " +
		"mapdefinitions.marker_id) AS markercount, COUNT(DISTINCT mapdefinitions.chromosome) AS chromosomeCount " +
		"from maps INNER JOIN mapdefinitions ON maps.id = mapdefinitions.map_id WHERE maps.id = ?";

	private final String linkageGroupQuery = "SELECT SQL_CALC_FOUND_ROWS map_id, chromosome, MAX(definition_end) AS max, COUNT(marker_id) " +
			"AS number_markers FROM mapdefinitions WHERE map_id=? GROUP BY chromosome LIMIT ?, ?";

	private final String markersQuery = "SELECT SQL_CALC_FOUND_ROWS map_id, chromosome, definition_start, marker_id, markers.marker_name" +
			", maps.description FROM mapdefinitions JOIN markers ON markers.id = mapdefinitions.marker_id LEFT JOIN maps ON map_id = maps.id %s LIMIT ?, ?";

	/**
	 * Queries the database (using mapQuery defined above) for the complete list of Maps which the database holds.
	 *
	 * @return A MapList object which is a wrapper around a List of BrapiGenomeMap objects.
	 */
	public BrapiListResource<GenomeMap> getAll(Map<String, List<String>> parameters, int currentPage, int pageSize)
	{
		BrapiListResource<GenomeMap> maps = new BrapiListResource<>();

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = DatabaseUtils.createParameterizedLimitStatement(con, mapsQuery, parameters, currentPage, pageSize);
			 ResultSet resultSet = statement.executeQuery())
		{
			List<GenomeMap> genomeMaps = getMapsFromResultSet(resultSet);
			long totalCount = DatabaseUtils.getTotalCount(statement);

			maps = new BrapiListResource<GenomeMap>(genomeMaps, currentPage, pageSize, totalCount);

		}
		catch (SQLException e)
		{
			e.printStackTrace();

			maps.getMetadata().getStatus().clear();
			maps.getMetadata().getStatus().add(new Status("500", "Internal server error"));
		}

		return maps;
	}

	private GenomeMap getMapFrom(ResultSet resultSet)
		throws SQLException
	{
		GenomeMap map = new GenomeMap();
		map.setMapDbId(resultSet.getString("id"));
		map.setMapName(resultSet.getString("description"));
		java.sql.Date createdDate = resultSet.getDate("created_on");
		if (createdDate != null)
			map.setPublishedDate(Instant.ofEpochMilli(createdDate.getTime()).toString());

		map.setLinkageGroupCount(resultSet.getInt("chromosomeCount"));
		map.setMarkerCount(resultSet.getInt("markerCount"));
		// TODO: Germinate doesn't store map types, for now default to genetic
		map.setType("Genetic");

		return map;
	}

	// Takes a resultSet and iterates over it adding each map object in turn to a list of BrapiGenomeMap objects, which is then
	// put in the MapList wrapper (easing Jackson translation of the object to JSON and back).
	private List<GenomeMap> getMapsFromResultSet(ResultSet resultSet)
		throws SQLException
	{
		List<GenomeMap> maps = new ArrayList<>();

		while (resultSet.next())
			maps.add(getMapFrom(resultSet));

		return maps;
	}

	/**
	 * Queries the database (using detailQuery and entiresQuery defined above) for the information on the map identified
	 * by the id given and the entries (MapEntry objects) which make up the detail of the map.
	 *
	 * @param id	The id of the
	 * @return 		A BrapiMapMetaData object which itself holds a List of MapEntry objects. Or null if no
	 * 				BrapiMapMetaData exists for the supplied id.
	 */
	public BrapiBaseResource<GenomeMap> getById(String id, int currentPage, int pageSize)
	{
		BrapiBaseResource<GenomeMap> result = new BrapiBaseResource<>();

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement mapStatement = DatabaseUtils.createByIdStatement(con, mapById, id);
			 ResultSet resultSet = mapStatement.executeQuery())
		{
			if (resultSet.next())
			{
				GenomeMap map = getMapFrom(resultSet);
				result = new BrapiBaseResource<GenomeMap>(map);
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return result;
	}

	// Takes a ResultSet which should represent the result of the linkageGroupsQuery defined above and returns a List of
	// BrapiLinkageGroup objects, each of which has been initialized with the values from the ResultSet
	private LinkageGroup getLinkageGroupFrom(ResultSet resultSet)
		throws SQLException
	{
		LinkageGroup linkageGroup = new LinkageGroup();
		linkageGroup.setLinkageGroupName(resultSet.getString("chromosome"));
		linkageGroup.setMaxPosition(resultSet.getDouble("max"));
		linkageGroup.setMarkerCount(resultSet.getInt("number_markers"));

		return linkageGroup;
	}

	public BrapiListResource<MarkerPosition> getMarkers(Map<String, List<String>> parameters, int currentPage, int pageSize)
	{
		BrapiListResource<MarkerPosition> result = new BrapiListResource<>();

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement mapStatement = DatabaseUtils.createParameterizedLimitStatement(con, markersQuery, parameters, currentPage, pageSize);
			 ResultSet resultSet = mapStatement.executeQuery())
		{
			List<MarkerPosition> markerPositions = getMapMarkersListFromResultSet(resultSet);
			long totalCount = DatabaseUtils.getTotalCount(mapStatement);

			result = new BrapiListResource<MarkerPosition>(markerPositions, currentPage, pageSize, totalCount);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return result;
	}

	// Takes a ResultSet which should represent the result of the linkageGroupsQuery defined above and returns a List of
	// BrapiLinkageGroup objects, each of which has been initialized with the values from the ResultSet
	private ArrayList<MarkerPosition> getMapMarkersListFromResultSet(ResultSet resultSet)
		throws SQLException
	{
		ArrayList<MarkerPosition> mapMarkers = new ArrayList<>();

		while (resultSet.next())
		{
			MarkerPosition mapMarker = new MarkerPosition();
			mapMarker.setVariantDbId(resultSet.getString("marker_id"));
			mapMarker.setVariantName(resultSet.getString("marker_name"));
			mapMarker.setPosition(resultSet.getString("definition_start"));
			mapMarker.setLinkageGroupName(resultSet.getString("chromosome"));
			mapMarker.setMapDbId(resultSet.getString("map_id"));
			mapMarker.setMapName(resultSet.getString("maps.description"));
			mapMarkers.add(mapMarker);
		}

		return mapMarkers;
	}

	public BrapiListResource<LinkageGroup> getByIdLinkageGroups(String id, int currentPage, int pageSize)
	{
		BrapiListResource<LinkageGroup> result = new BrapiListResource<>();

		try (Connection con = jhi.brapi.util.Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement mapStatement = DatabaseUtils.createByIdLimitStatement(con, linkageGroupQuery, id, currentPage, pageSize);
			 ResultSet resultSet = mapStatement.executeQuery())
		{
			List<LinkageGroup> linkageGroups = new ArrayList<>();

			while (resultSet.next())
				linkageGroups.add(getLinkageGroupFrom(resultSet));

			long totalCount = DatabaseUtils.getTotalCount(mapStatement);

			result = new BrapiListResource<LinkageGroup>(linkageGroups, currentPage, pageSize, totalCount);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return result;
	}
}