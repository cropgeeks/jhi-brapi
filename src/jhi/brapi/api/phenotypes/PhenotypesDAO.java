package jhi.brapi.api.phenotypes;

import java.sql.*;
import java.util.*;

import jhi.brapi.api.*;
import jhi.brapi.util.*;

/**
 * Specifies the public interface which any ServerPhenotypes data accessing classes must implement.
 */
public class PhenotypesDAO
{
	private final String tables              = "phenotypedata LEFT JOIN germinatebase ON germinatebase.id = phenotypedata.germinatebase_id LEFT JOIN datasets ON datasets.id = phenotypedata.dataset_id LEFT JOIN locations ON locations.id = datasets.location_id";
	private final String getLinesForDatasets = "SELECT SQL_CALC_FOUND_ROWS germinatebase.*, datasets.*, locations.* FROM " + tables + " %s GROUP BY germinatebase.id, datasets.id, locations.id LIMIT ?, ?";
	private final String getDataForLines     = "SELECT * FROM phenotypedata LEFT JOIN phenotypes ON phenotypes.id = phenotypedata.phenotype_id %s";

	public BrapiListResource<BrapiPhenotype> getPhenotypesForSearch(Map<String, List<String>> parameters, int currentPage, int pageSize)
	{
		// Create empty BrapiBaseResource of type BrapiGermplasm (if for whatever reason we can't get data from the database
		// this is what's returned
		BrapiListResource<BrapiPhenotype> result = new BrapiListResource<>();

		// Paginate over the data by passing the currentPage and pageSize values to the code which generates the
		// prepared statement (which includes a limit statement)

		Map<String, List<String>> reducedParameters = new HashMap<>();
		if(parameters.get("phenotypedata.germinatebase_id") != null)
			reducedParameters.put("phenotypedata.germinatebase_id", parameters.get("phenotypedata.germinatebase_id"));
		if(parameters.get("phenotypedata.dataset_id") != null)
			reducedParameters.put("phenotypedata.dataset_id", parameters.get("phenotypedata.dataset_id"));

		try (Connection con = Database.INSTANCE.getDataSourceGerminate().getConnection();
			 PreparedStatement statement = DatabaseUtils.createParameterizedLimitStatement(con, getLinesForDatasets, reducedParameters, currentPage, pageSize);
			 ResultSet resultSet = statement.executeQuery())
		{
			List<BrapiPhenotype> list = new ArrayList<>();

			while (resultSet.next())
			{
				BrapiPhenotype phenotypes = getBrapiPhenotypes(resultSet);

				Map<String, List<String>> localParameters = new HashMap<>(parameters);
				localParameters.put("phenotypedata.dataset_id", Collections.singletonList(phenotypes.getStudyDbId()));
				localParameters.put("phenotypedata.germinatebase_id", Collections.singletonList(phenotypes.getGermplasmDbId()));

				List<BrapiObservation> observations = new ArrayList<>();

				try (Connection con2 = Database.INSTANCE.getDataSourceGerminate().getConnection();
					 PreparedStatement innerStatement = DatabaseUtils.createParameterizedStatement(con2, getDataForLines, localParameters);
					 ResultSet innerResultSet = innerStatement.executeQuery())
				{
					while (innerResultSet.next())
						observations.add(getBrapiObservations(innerResultSet));
				}

				phenotypes.setObservations(observations);

				list.add(phenotypes);
			}

			long totalCount = DatabaseUtils.getTotalCount(statement);

			// Pass the currentPage and totalCount to the BrapiBaseResource constructor so we generate correct metadata
			result = new BrapiListResource<BrapiPhenotype>(list, currentPage, pageSize, totalCount);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return result;
	}

	private BrapiObservation getBrapiObservations(ResultSet resultSet) throws SQLException
	{
		BrapiObservation observation = new BrapiObservation();

		observation.setObservationDbId(resultSet.getString("phenotypedata.id"));
		observation.setObservationVariableDbId(resultSet.getString("phenotypes.id"));
		observation.setObservationVariableName(resultSet.getString("phenotypes.name"));
		observation.setValue(resultSet.getString("phenotypedata.phenotype_value"));

		return observation;
	}

	private BrapiPhenotype getBrapiPhenotypes(ResultSet resultSet) throws SQLException
	{
		BrapiPhenotype phenotypes = new BrapiPhenotype();

		phenotypes.setGermplasmDbId(resultSet.getString("germinatebase.id"));
		phenotypes.setGermplasmName(resultSet.getString("germinatebase.name"));
		phenotypes.setStudyDbId(resultSet.getString("datasets.id"));
		phenotypes.setObservationUnitDbId(phenotypes.getStudyDbId() + "-" + phenotypes.getGermplasmDbId());
		phenotypes.setStudyLocationDbId(resultSet.getString("locations.id"));
		phenotypes.setStudyLocation(resultSet.getString("locations.site_name"));

		return phenotypes;
	}
}