package jhi.brapi.data;

import java.sql.*;
import java.text.*;
import java.util.*;

import jhi.brapi.resource.*;
import jhi.brapi.server.*;

/**
 * An implementation of the GermplasmDAO interface which provides access via a database.
 */
public class GermplasmDAOImpl implements GermplasmDAO
{
	private static final SimpleDateFormat FORMAT_OUTPUT = new SimpleDateFormat("YYYYMMDD");

	// Simply selects all fields from germinatebase
	private final String getLines = "SELECT * FROM germinatebase LEFT JOIN locations ON germinatebase.location_id = locations.id LEFT JOIN countries ON locations.country_id = countries.id LEFT JOIN taxonomies ON germinatebase.taxonomy_id = taxonomies.id LEFT JOIN subtaxa ON subtaxa.id = germinatebase.subtaxa_id LEFT JOIN institutions ON germinatebase.institution_id = institutions.id";

	private final String getLinesByNameExact = "select * from germinatebase where number=? OR name=? OR old_name=?";

	private final String getLinesByNameRegex = "select * from germinatebase where number LIKE ? OR name LIKE ? or old_name LIKE ?";

	// Simply selects all fields from germinatebase where the given id matches the id from the URI
	private final String getSpecificLine = "SELECT * FROM germinatebase LEFT JOIN locations ON germinatebase.location_id = locations.id LEFT JOIN countries ON locations.country_id = countries.id LEFT JOIN taxonomies ON germinatebase.taxonomy_id = taxonomies.id LEFT JOIN subtaxa ON subtaxa.id = germinatebase.subtaxa_id LEFT JOIN institutions ON germinatebase.institution_id = institutions.id where germinatebase.id=?";

	// Query to extract the markerprofiles which relate to the germplasm indicated by id
	private final String markrerProfileIdQuery = "select DISTINCT(dataset_id), germinatebase_id from genotypes where germinatebase_id=?";

	@Override
	public GermplasmList getAll()
	{
		GermplasmList allGermplasm = new GermplasmList();

		try (Connection con = Database.INSTANCE.getDataSource().getConnection();
			 PreparedStatement statement = con.prepareStatement(getLines);
			 ResultSet resultSet = statement.executeQuery())
		{
			// To store the Germplasm instances created from the results of the db query before adding them to the
			// GermplasmList object
			List<Germplasm> germplasmList = new ArrayList<>();

			while (resultSet.next())
			{
				// Set the Germplasm bean using the data returned from the database
				Germplasm germplasm = getGermplasm(resultSet);

				germplasmList.add(germplasm);
			}
			allGermplasm.setGermplasm(germplasmList);
		}
		catch (SQLException e) { e.printStackTrace(); }

		return allGermplasm;
	}

	@Override
	public Germplasm getById(int id)
	{
		Germplasm germplasm = null;

		try (Connection con = Database.INSTANCE.getDataSource().getConnection();
			 PreparedStatement statement = createByIdStatement(con, getSpecificLine, id);
			 ResultSet resultSet = statement.executeQuery())
		{
			if (resultSet.first())
			{
				// Set the Germplasm bean using the data returned from the database
				germplasm = getGermplasm(resultSet);
			}
		}
		catch (SQLException e) { e.printStackTrace(); }

		return germplasm;
	}

	private Germplasm getGermplasm(ResultSet resultSet) throws SQLException
	{
		Germplasm germplasm = new Germplasm();
		germplasm.setGermplasmId(resultSet.getString("germinatebase.id"));
		germplasm.setGermplasmPUI(resultSet.getString("germinatebase.general_identifier"));
		germplasm.setGermplasmName(resultSet.getString("germinatebase.name"));
		germplasm.setAccessionNumber(resultSet.getString("germinatebase.number"));
		germplasm.setSynonyms(getSynonyms(resultSet));
		germplasm.setCommonCropName(null); // TODO
		germplasm.setInstituteCode(resultSet.getString("institutions.code"));
		germplasm.setInstituteName(resultSet.getString("institutions.name"));
		germplasm.setBiologicalStatusOfGermplasmCode(null); // TODO
		germplasm.setCountryOfOriginCode(resultSet.getString("countries.country_code3"));
		germplasm.setTypeOfGermplasmStorageCode(null); // TODO
		germplasm.setGenus(resultSet.getString("taxonomies.genus"));
		germplasm.setSpecies(resultSet.getString("taxonomies.species"));
		germplasm.setSpeciesAuthority(resultSet.getString("taxonomies.species_author"));
		germplasm.setSubtaxa(resultSet.getString("subtaxa.taxonomic_identifier"));
		germplasm.setSubtaxaAuthority(resultSet.getString("subtaxa.subtaxa_author"));

		String donorCode = resultSet.getString("germinatebase.donor_code");
		String donorNumber = resultSet.getString("germinatebase.donor_number");
		List<Donor> donorList = new ArrayList<>();

		if((donorCode != null && !donorCode.equals("")) || (donorNumber != null && !donorNumber.equals("")))
		{
			Donor donor = new Donor();
			donor.setDonorInstituteCode(donorCode);
			donor.setDonorAccessionNumber(donorNumber);
			donorList.add(donor);
		}

		germplasm.setDonors(donorList);
		germplasm.setAcquisitionDate(FORMAT_OUTPUT.format(resultSet.getDate("germinatebase.colldate")));

		return germplasm;
	}

	private PreparedStatement createByIdStatement(Connection con, String query, int id)
		throws SQLException
	{
		// Prepare statement with ID
		PreparedStatement statement = con.prepareStatement(query);
		statement.setInt(1, id);

		return statement;
	}

	private PreparedStatement createByNameStatement(Connection con, String name, GermplasmListServerResource.MatchingMethod matchingMethod) throws SQLException
	{
		// Replace the non-sql wildcards with sql wildcards
		name = name.replace("*", "%");
		name = name.replace("?", "_");

		String query;

		switch (matchingMethod)
		{
			case WILDCARD:
				query= getLinesByNameRegex;
				break;
			default:
				query = getLinesByNameExact;
		}

		// Prepare statement with NAME
		PreparedStatement statement = con.prepareStatement(query);
		int i = 1;
		statement.setString(i++, name);
		statement.setString(i++, name);
		statement.setString(i++, name);

		return statement;
	}

	@Override
	public GermplasmMarkerProfileList getMarkerProfilesFor(int id)
	{
		try (Connection con = Database.INSTANCE.getDataSource().getConnection();
			 PreparedStatement statement = createByIdStatement(con, markrerProfileIdQuery, id);
			 ResultSet resultSet = statement.executeQuery())
		{
			GermplasmMarkerProfileList profileList = new GermplasmMarkerProfileList();
			List<String> markerProfileIdList = new ArrayList<>();
			while (resultSet.next())
			{
				profileList.setGermplasmId(resultSet.getInt("germinatebase_id"));
				markerProfileIdList.add(resultSet.getInt("dataset_id") + "-" + resultSet.getInt("germinatebase_id"));
			}
			profileList.setMarkerProfiles(markerProfileIdList);

			return profileList;
		}
		catch (SQLException e) { e.printStackTrace(); }

		return null;
	}

	@Override
	public GermplasmSearchListPagination getByName(String name, GermplasmListServerResource.MatchingMethod matchingMethod, int page, int pageSize)
	{
		GermplasmSearchListPagination resultGermplasm = new GermplasmSearchListPagination();

		try (Connection con = Database.INSTANCE.getDataSource().getConnection();
			 PreparedStatement statement = createByNameStatement(con, name, matchingMethod);
			 ResultSet resultSet = statement.executeQuery())
		{

			// To store the Germplasm instances created from the results of the db query before adding them to the
			// GermplasmList object
			List<GermplasmSearch> germplasmList = new ArrayList<>();

			// Keep count of the total number of accessions
			int counter = 0;
			while (resultSet.next())
			{
				// We need the total count, so we need to iterate over all of them
				if (counter >= (page - 1) * pageSize && counter < page * pageSize)
				{
					// Set the Germplasm bean using the data returned from the database
					GermplasmSearch germplasm = new GermplasmSearch();
					germplasm.setGermplasmId(resultSet.getString("id"));
					germplasm.setAccessionNumber(resultSet.getString("number"));
					germplasm.setGermplasmName(resultSet.getString("name"));
					germplasm.setBreederName(resultSet.getString("breeders_code"));
					germplasm.setGermplasmPUI(resultSet.getString("general_identifier"));
					germplasm.setSynonyms(getSynonyms(resultSet));

					// TODO: Store query name in the metadata tag

					germplasmList.add(germplasm);
				}

				counter++;
			}

			// Add the pagination bits
			Pagination pagination = new Pagination();
			pagination.setCurrentPage(page);
			pagination.setTotalPages((int)Math.ceil(counter / (1f * pageSize)));
			pagination.setTotalCount(germplasmList.size());
			pagination.setPageSize(pageSize);

			resultGermplasm.setPagination(pagination);
			resultGermplasm.setGermplasm(germplasmList);
		}
		catch (SQLException e) { e.printStackTrace(); }

		return resultGermplasm;
	}

	@Override
	public Pedigree getPedigreeById(int id)
	{
		// Do black magic here, as we don't have the pedigree database structure yet
		return null;
	}

	public static List<String> getSynonyms(ResultSet resultSet) throws SQLException
	{
		List<String> synonyms = new ArrayList<>();

		String name = resultSet.getString("germinatebase.name");
		String oldName = resultSet.getString("germinatebase.old_name");
		String number = resultSet.getString("germinatebase.number");

		if(name != null)
			synonyms.add(name);
		if(oldName != null)
			synonyms.add(oldName);
		if(number != null)
			synonyms.add(number);

		return synonyms;
	}
}