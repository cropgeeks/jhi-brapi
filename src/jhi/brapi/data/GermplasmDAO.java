package jhi.brapi.data;

import jhi.brapi.resource.*;
import jhi.brapi.server.Germplasm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Specifies the public interface which any Germplasm data accessing classes must implement.
 */
public class GermplasmDAO
{
	private static final SimpleDateFormat FORMAT_OUTPUT = new SimpleDateFormat("YYYYMMDD");

	// Simply selects all fields from germinatebase
	private final String getLines = "SELECT * FROM germinatebase LEFT JOIN locations ON germinatebase.location_id = locations.id LEFT JOIN countries ON locations.country_id = countries.id LEFT JOIN taxonomies ON germinatebase.taxonomy_id = taxonomies.id LEFT JOIN subtaxa ON subtaxa.id = germinatebase.subtaxa_id LEFT JOIN institutions ON germinatebase.institution_id = institutions.id";

	private final String getLinesByNameExact = "select * from germinatebase where number=? OR name=?";

	private final String getLinesByNameRegex = "select * from germinatebase where number LIKE ? OR name LIKE ?";

	// Simply selects all fields from germinatebase where the given id matches the id from the URI
	private final String getSpecificLine = "SELECT * FROM germinatebase LEFT JOIN locations ON germinatebase.location_id = locations.id LEFT JOIN countries ON locations.country_id = countries.id LEFT JOIN taxonomies ON germinatebase.taxonomy_id = taxonomies.id LEFT JOIN subtaxa ON subtaxa.id = germinatebase.subtaxa_id LEFT JOIN institutions ON germinatebase.institution_id = institutions.id where germinatebase.id=?";

	// Query to extract the markerprofiles which relate to the germplasm indicated by id
	private final String markrerProfileIdQuery = "select DISTINCT(dataset_id), germinatebase_id from genotypes where germinatebase_id=?";

	public List<BrapiGermplasm> getAll()
	{
		List<BrapiGermplasm> list = new ArrayList<>();

		try (Connection con = Database.INSTANCE.getDataSource().getConnection();
			 PreparedStatement statement = con.prepareStatement(getLines);
			 ResultSet resultSet = statement.executeQuery())
		{
			int i=0;
			while (resultSet.next())
			{
				// Set the Germplasm bean using the data returned from the database
				BrapiGermplasm germplasm = getGermplasm(resultSet);

				list.add(germplasm);
			}
		}
		catch (SQLException e) { e.printStackTrace(); }

		return list;
	}

	public BrapiGermplasm getById(int id)
	{
		BrapiGermplasm germplasm = null;

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

	private BrapiGermplasm getGermplasm(ResultSet resultSet)
		throws SQLException
	{
		BrapiGermplasm germplasm = new BrapiGermplasm();
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

	private PreparedStatement createByNameStatement(Connection con, String name, Germplasm.MatchingMethod matchingMethod)
		throws SQLException
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

	public GermplasmSearchListPagination getByName(String name, Germplasm.MatchingMethod matchingMethod, int page, int pageSize)
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

	public Pedigree getPedigreeById(int id)
	{
		// Do black magic here, as we don't have the pedigree database structure yet
		return null;
	}

	public static List<String> getSynonyms(ResultSet resultSet)
		throws SQLException
	{
		List<String> synonyms = new ArrayList<>();

		String name = resultSet.getString("germinatebase.name");
		String number = resultSet.getString("germinatebase.number");

		if(name != null)
			synonyms.add(name);
		if(number != null)
			synonyms.add(number);

		return synonyms;
	}
}
