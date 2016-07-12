package jhi.brapi.data;

import java.sql.*;
import java.text.*;
import java.util.*;

import jhi.brapi.resource.*;

/**
 * Specifies the public interface which any Germplasm data accessing classes must implement.
 */
public class GermplasmDAO
{
	private static final SimpleDateFormat FORMAT_OUTPUT = new SimpleDateFormat("YYYYMMDD");

	// Simply selects all fields from germinatebase
	private final String getLines = "SELECT * FROM germinatebase LEFT JOIN locations ON germinatebase.location_id = locations.id LEFT JOIN countries ON locations.country_id = countries.id LEFT JOIN taxonomies ON germinatebase.taxonomy_id = taxonomies.id LEFT JOIN subtaxa ON subtaxa.id = germinatebase.subtaxa_id LEFT JOIN institutions ON germinatebase.institution_id = institutions.id LEFT JOIN pedigreedefinitions ON germinatebase.id = pedigreedefinitions.germinatebase_id LIMIT ?, ?";

	private final String getCountLines = "SELECT COUNT(*) AS total_count FROM germinatebase LEFT JOIN locations ON germinatebase.location_id = locations.id LEFT JOIN countries ON locations.country_id = countries.id LEFT JOIN taxonomies ON germinatebase.taxonomy_id = taxonomies.id LEFT JOIN subtaxa ON subtaxa.id = germinatebase.subtaxa_id LEFT JOIN institutions ON germinatebase.institution_id = institutions.id LEFT JOIN pedigreedefinitions ON germinatebase.id = pedigreedefinitions.germinatebase_id";

	private final String getLinesByNameExact = "select * from germinatebase LEFT JOIN locations ON germinatebase.location_id = locations.id LEFT JOIN countries ON locations.country_id = countries.id LEFT JOIN taxonomies ON germinatebase.taxonomy_id = taxonomies.id LEFT JOIN subtaxa ON subtaxa.id = germinatebase.subtaxa_id LEFT JOIN institutions ON germinatebase.institution_id = institutions.id LEFT JOIN pedigreedefinitions ON germinatebase.id = pedigreedefinitions.germinatebase_id where germinatebase.number = ? OR germinatebase.name = ? OR germinatebase.general_identifier = ? LIMIT ?, ?";

	private final String getCountLinesByNameExact = "SELECT COUNT(*) AS total_count FROM germinatebase LEFT JOIN locations ON germinatebase.location_id = locations.id LEFT JOIN countries ON locations.country_id = countries.id LEFT JOIN taxonomies ON germinatebase.taxonomy_id = taxonomies.id LEFT JOIN subtaxa ON subtaxa.id = germinatebase.subtaxa_id LEFT JOIN institutions ON germinatebase.institution_id = institutions.id LEFT JOIN pedigreedefinitions ON germinatebase.id = pedigreedefinitions.germinatebase_id where germinatebase.number = ? OR germinatebase.name = ? OR germinatebase.general_identifier = ?";

	private final String getLinesByNameRegex = "SELECT * FROM germinatebase LEFT JOIN locations ON germinatebase.location_id = locations.id LEFT JOIN countries ON locations.country_id = countries.id LEFT JOIN taxonomies ON germinatebase.taxonomy_id = taxonomies.id LEFT JOIN subtaxa ON subtaxa.id = germinatebase.subtaxa_id LEFT JOIN institutions ON germinatebase.institution_id = institutions.id LEFT JOIN pedigreedefinitions ON germinatebase.id = pedigreedefinitions.germinatebase_id where germinatebase.number LIKE ? OR germinatebase.name LIKE ? OR germinatebase.general_identifier LIKE ? LIMIT ?, ?";

	private final String getCountLinesByNameRegex = "SELECT COUNT(*) AS total_count FROM germinatebase LEFT JOIN locations ON germinatebase.location_id = locations.id LEFT JOIN countries ON locations.country_id = countries.id LEFT JOIN taxonomies ON germinatebase.taxonomy_id = taxonomies.id LEFT JOIN subtaxa ON subtaxa.id = germinatebase.subtaxa_id LEFT JOIN institutions ON germinatebase.institution_id = institutions.id LEFT JOIN pedigreedefinitions ON germinatebase.id = pedigreedefinitions.germinatebase_id where germinatebase.number LIKE ? OR germinatebase.name LIKE ? OR germinatebase.general_identifier LIKE ?";

	// Simply selects all fields from germinatebase where the given id matches the id from the URI
	private final String getSpecificLine = "SELECT * FROM germinatebase LEFT JOIN locations ON germinatebase.location_id = locations.id LEFT JOIN countries ON locations.country_id = countries.id LEFT JOIN taxonomies ON germinatebase.taxonomy_id = taxonomies.id LEFT JOIN subtaxa ON subtaxa.id = germinatebase.subtaxa_id LEFT JOIN institutions ON germinatebase.institution_id = institutions.id LEFT JOIN pedigreedefinitions ON germinatebase.id = pedigreedefinitions.germinatebase_id where germinatebase.id=?";

	// Query to extract the markerprofiles which relate to the germplasm indicated by id
	private final String markrerProfileIdQuery = "select DISTINCT(dataset_id), germinatebase_id from genotypes where germinatebase_id=? LIMIT ?, ?";

	private final String markerProfileCountIdQuery = "SELECT COUNT(DISTINCT(dataset_id)) AS total_count, germinatebase_id from genotypes where germinatebase_id = ?";

	private final String pedigreeByIdQuery = "SELECT p1.germinatebase_id, p1.parent_id as 'left_parent', p2.parent_id as 'right_parent', definition , name FROM pedigrees p1 INNER JOIN pedigrees p2 ON p1.germinatebase_id = p2.germinatebase_id and p1.pedigreedescription_id = 1 and p2.pedigreedescription_id = 2 JOIN germinatebase ON p1.germinatebase_id = germinatebase.id JOIN pedigreedefinitions ON p1.germinatebase_id WHERE p1.germinatebase_id = ?";

	public BasicResource<DataResult<BrapiGermplasm>> getAll(int currentPage, int pageSize)
	{
		// Create empty BasicResource of type BrapiGermplasm (if for whatever reason we can't get data from the database
		// this is what's returned
		BasicResource<DataResult<BrapiGermplasm>> result = new BasicResource<>();

		long totalCount = DatabaseUtils.getTotalCount(getCountLines);

		if (totalCount != -1)
		{
			// Paginate over the data by passing the currentPage and pageSize values to the code which generates the
			// prepared statement (which includes a limit statement)
			try (Connection con = Database.INSTANCE.getDataSource().getConnection();
				 PreparedStatement statement = DatabaseUtils.createLimitStatement(con, getLines, currentPage, pageSize);
				 ResultSet resultSet = statement.executeQuery())
			{
				List<BrapiGermplasm> list = new ArrayList<>();

				while (resultSet.next())
					list.add(getBrapiGermplasm(resultSet));

				// Pass the currentPage and totalCount to the BasicResource constructor so we generate correct metadata
				result = new BasicResource<DataResult<BrapiGermplasm>>(new DataResult(list), currentPage, pageSize, totalCount);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

		return result;
	}

	public BasicResource<BrapiGermplasm> getById(String id)
	{
		BasicResource<BrapiGermplasm> wrappedResult = new BasicResource<>();

		try (Connection con = Database.INSTANCE.getDataSource().getConnection();
			 PreparedStatement statement = DatabaseUtils.createByIdStatement(con, getSpecificLine, id);
			 ResultSet resultSet = statement.executeQuery())
		{
			if (resultSet.first())
			{
				// Set the Germplasm bean using the data returned from the database
				wrappedResult = new BasicResource<>(getBrapiGermplasm(resultSet));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return wrappedResult;
	}

	private BrapiGermplasm getBrapiGermplasm(ResultSet resultSet)
			throws SQLException
	{
		BrapiGermplasm germplasm = new BrapiGermplasm();
		germplasm.setGermplasmDbId(resultSet.getString("germinatebase.id"));
		germplasm.setGermplasmPUI(resultSet.getString("germinatebase.general_identifier"));
		germplasm.setGermplasmName(resultSet.getString("germinatebase.name"));
		germplasm.setAccessionNumber(resultSet.getString("germinatebase.number"));
		germplasm.setSynonyms(getSynonyms(resultSet));
		germplasm.setPedigree(resultSet.getString("pedigreedefinitions.definition"));
		germplasm.setDefaultDisplayName(resultSet.getString("germinatebase.name"));
		germplasm.setSeedSource(null); // TODO

		return germplasm;
	}

	public BasicResource<BrapiGermplasmMcpd> getMcpdFor(String id)
	{
		BasicResource<BrapiGermplasmMcpd> result = new BasicResource<>();

		try (Connection con = Database.INSTANCE.getDataSource().getConnection();
			 PreparedStatement statement = DatabaseUtils.createByIdStatement(con, getSpecificLine, id);
			 ResultSet resultSet = statement.executeQuery())
		{
			if (resultSet.first())
				result = new BasicResource<BrapiGermplasmMcpd>(getBrapiGermplasmMcpd(resultSet));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return result;
	}

	private BrapiGermplasmMcpd getBrapiGermplasmMcpd(ResultSet resultSet) throws SQLException
	{
		BrapiGermplasmMcpd mcpd = new BrapiGermplasmMcpd();

		mcpd.setGermplasmDbId(resultSet.getString("germinatebase.id"));
		mcpd.setGermplasmPUI(resultSet.getString("germinatebase.general_identifier"));
		mcpd.setGermplasmName(resultSet.getString("germinatebase.name"));
		mcpd.setAccessionNumber(resultSet.getString("germinatebase.number"));
		mcpd.setSynonyms(getSynonyms(resultSet));
		mcpd.setCommonCropName(null); // TODO
		mcpd.setInstituteCode(resultSet.getString("institutions.code"));
		mcpd.setInstituteName(resultSet.getString("institutions.name"));
		mcpd.setBiologicalStatusOfGermplasmCode(null); // TODO
		mcpd.setCountryOfOriginCode(resultSet.getString("countries.country_code3"));
		mcpd.setTypeOfGermplasmStorageCode(null); // TODO
		mcpd.setGenus(resultSet.getString("taxonomies.genus"));
		mcpd.setSpecies(resultSet.getString("taxonomies.species"));
		mcpd.setSpeciesAuthority(resultSet.getString("taxonomies.species_author"));
		mcpd.setSubtaxa(resultSet.getString("subtaxa.taxonomic_identifier"));
		mcpd.setSubtaxaAuthority(resultSet.getString("subtaxa.subtaxa_author"));
		mcpd.setPedigree(resultSet.getString("pedigreedefinitions.definition"));
		mcpd.setDefaultDisplayName(resultSet.getString("germinatebase.name"));
		mcpd.setSeedSource(null); // TODO

		String donorCode = resultSet.getString("germinatebase.donor_code");
		String donorNumber = resultSet.getString("germinatebase.donor_number");
		List<Donor> donorList = new ArrayList<>();

		if ((donorCode != null && !donorCode.equals("")) || (donorNumber != null && !donorNumber.equals("")))
		{
			Donor donor = new Donor();
			donor.setDonorInstituteCode(donorCode);
			donor.setDonorAccessionNumber(donorNumber);
			donorList.add(donor);
		}

		mcpd.setDonors(donorList);
		mcpd.setAcquisitionDate(FORMAT_OUTPUT.format(resultSet.getDate("germinatebase.colldate")));

		return mcpd;
	}

	public BasicResource<BrapiGermplasmMarkerProfiles> getMarkerProfilesFor(String id, int currentPage, int pageSize)
	{
		BasicResource<BrapiGermplasmMarkerProfiles> result = new BasicResource<>();

		long totalCount = DatabaseUtils.getTotalCountById(markerProfileCountIdQuery, id);

		try (Connection con = Database.INSTANCE.getDataSource().getConnection();
			 PreparedStatement statement = DatabaseUtils.createByIdLimitStatement(con, markrerProfileIdQuery, id, currentPage, pageSize);
			 ResultSet resultSet = statement.executeQuery())
		{
			List<BrapiGermplasmMarkerProfiles> list = new ArrayList<>();

			BrapiGermplasmMarkerProfiles profiles = new BrapiGermplasmMarkerProfiles();
			List<String> markerProfileIdList = new ArrayList<>();
			while (resultSet.next())
			{
				profiles.setGermplasmId(resultSet.getInt("germinatebase_id"));
				markerProfileIdList.add(resultSet.getInt("dataset_id") + "-" + resultSet.getInt("germinatebase_id"));
			}
			profiles.setMarkerProfiles(markerProfileIdList);

			list.add(profiles);

			result = new BasicResource<BrapiGermplasmMarkerProfiles>(profiles, currentPage, list.size(), totalCount);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return result;
	}

	public static List<String> getSynonyms(ResultSet resultSet)
			throws SQLException
	{
		List<String> synonyms = new ArrayList<>();

		String name = resultSet.getString("germinatebase.name");
		String number = resultSet.getString("germinatebase.number");

		if (name != null)
			synonyms.add(name);
		if (number != null)
			synonyms.add(number);

		return synonyms;
	}

	public BasicResource<DataResult<BrapiGermplasm>> getByName(String name, BrapiGermplasm.MatchingMethod matchingMethod, int currentPage, int pageSize)
	{
		List<BrapiGermplasm> resultGermplasm = new ArrayList<>();
		BasicResource<DataResult<BrapiGermplasm>> wrappedList = new BasicResource<>();

		String countQuery;
		String getQuery;

		switch (matchingMethod)
		{
			case WILDCARD:
				countQuery = getCountLinesByNameRegex;
				getQuery = getLinesByNameRegex;
				break;
			default:
				countQuery = getCountLinesByNameExact;
				getQuery = getLinesByNameExact;
		}

		long totalCount = getByNameTotalCount(name, countQuery);

		if (totalCount != -1)
		{
			// Paginate over the data by passing the currentPage and pageSize values to the code which generates the
			// prepared statement (which includes a limit statement)
			try (Connection con = Database.INSTANCE.getDataSource().getConnection();
				 PreparedStatement statement = createByNameLimitStatement(con, name, getQuery, currentPage, pageSize);
				 ResultSet resultSet = statement.executeQuery())
			{
				while (resultSet.next())
				{
					resultGermplasm.add(getBrapiGermplasm(resultSet));

					// Pass the currentPage and totalCount to the BasicResource constructor so we generate correct metadata
					wrappedList = new BasicResource<DataResult<BrapiGermplasm>>(new DataResult(resultGermplasm), currentPage, pageSize, totalCount);
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			wrappedList.getMetadata().setPagination(PaginationUtils.getEmptyPagination());
		}

		return wrappedList;
	}

	private long getByNameTotalCount(String name, String countQuery)
	{
		long totalCount = -1;

		try (Connection con = Database.INSTANCE.getDataSource().getConnection();
			 PreparedStatement statement = createByNameStatement(con, name, countQuery);
			 ResultSet resultSet = statement.executeQuery())
		{
			if (resultSet.first())
				totalCount = resultSet.getLong("total_count");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return totalCount;
	}

	private PreparedStatement createByNameStatement(Connection con, String name, String query)
		throws SQLException
	{
		// Replace the non-sql wildcards with sql wildcards
		name = name.replace("*", "%");
		name = name.replace("?", "_");

		// Prepare statement with NAME
		PreparedStatement statement = con.prepareStatement(query);
		statement.setString(1, name);
		statement.setString(2, name);
		statement.setString(3, name);

		return statement;
	}

	private PreparedStatement createByNameLimitStatement(Connection con, String name, String query, int currentPage, int pageSize)
		throws SQLException
	{
		PreparedStatement statement = createByNameStatement(con, name, query);

		statement.setInt(4, PaginationUtils.getLowLimit(currentPage, pageSize));
		statement.setInt(5, pageSize);

		return statement;
	}

	public BasicResource<BrapiGermplasmPedigree> getPedigreeById(String id)
	{
		BasicResource<BrapiGermplasmPedigree> wrappedResult = new BasicResource<>();

		try (Connection con = Database.INSTANCE.getDataSource().getConnection();
			 PreparedStatement statement = DatabaseUtils.createByIdStatement(con, pedigreeByIdQuery, id);
			 ResultSet resultSet = statement.executeQuery())
		{
			if (resultSet.first())
			{
				// Set the Germplasm bean using the data returned from the database
				wrappedResult = new BasicResource<>(getBrapiGermplasmPedigree(resultSet));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return wrappedResult;
	}

	private BrapiGermplasmPedigree getBrapiGermplasmPedigree(ResultSet resultSet)
		throws SQLException
	{
		BrapiGermplasmPedigree pedigree = new BrapiGermplasmPedigree();
		pedigree.setGermplasmDbId(resultSet.getString("p1.germinatebase_id"));
		pedigree.setDefaultDisplayName(resultSet.getString("name"));
		pedigree.setPedigree(resultSet.getString("definition"));
		pedigree.setParent1Id(resultSet.getString("left_parent"));
		pedigree.setParent2Id(resultSet.getString("right_parent"));

		return pedigree;
	}
}
