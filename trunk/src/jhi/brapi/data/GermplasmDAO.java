package jhi.brapi.data;

import java.sql.*;
import java.text.*;
import java.util.*;

import jhi.brapi.resource.*;
import jhi.brapi.server.*;

/**
 * Specifies the public interface which any Germplasm data accessing classes must implement.
 */
public class GermplasmDAO
{
	private static final SimpleDateFormat FORMAT_OUTPUT = new SimpleDateFormat("YYYYMMDD");

	// Simply selects all fields from germinatebase
	private final String getLines = "SELECT * FROM germinatebase LEFT JOIN locations ON germinatebase.location_id = locations.id LEFT JOIN countries ON locations.country_id = countries.id LEFT JOIN taxonomies ON germinatebase.taxonomy_id = taxonomies.id LEFT JOIN subtaxa ON subtaxa.id = germinatebase.subtaxa_id LEFT JOIN institutions ON germinatebase.institution_id = institutions.id LEFT JOIN pedigreedefinitions ON germinatebase.id = pedigreedefinitions.germinatebase_id";

	private final String getLinesByNameExact = "select * from germinatebase LEFT JOIN locations ON germinatebase.location_id = locations.id LEFT JOIN countries ON locations.country_id = countries.id LEFT JOIN taxonomies ON germinatebase.taxonomy_id = taxonomies.id LEFT JOIN subtaxa ON subtaxa.id = germinatebase.subtaxa_id LEFT JOIN institutions ON germinatebase.institution_id = institutions.id LEFT JOIN pedigreedefinitions ON germinatebase.id = pedigreedefinitions.germinatebase_id where germinatebase.number = ? OR germinatebase.name = ? OR germinatebase.general_identifier = ?";

	private final String getLinesByNameRegex = "select * from germinatebase LEFT JOIN locations ON germinatebase.location_id = locations.id LEFT JOIN countries ON locations.country_id = countries.id LEFT JOIN taxonomies ON germinatebase.taxonomy_id = taxonomies.id LEFT JOIN subtaxa ON subtaxa.id = germinatebase.subtaxa_id LEFT JOIN institutions ON germinatebase.institution_id = institutions.id LEFT JOIN pedigreedefinitions ON germinatebase.id = pedigreedefinitions.germinatebase_id where germinatebase.number LIKE ? OR germinatebase.name LIKE ? OR germinatebase.general_identifier LIKE ?";

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
			while (resultSet.next())
			{
				// Set the Germplasm bean using the data returned from the database
				BrapiGermplasm germplasm = getGermplasm(resultSet);

				list.add(germplasm);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

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
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return germplasm;
	}

	private BrapiGermplasm getGermplasm(ResultSet resultSet)
			throws SQLException
	{
		BrapiGermplasm germplasm = new BrapiGermplasm();
		germplasm.setGermplasmDbId(resultSet.getString("germinatebase.id"));
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
		germplasm.setPedigree(resultSet.getString("pedigreedefinitions.definition"));
		germplasm.setDefaultDisplayName(resultSet.getString("germinatebase.name"));
		germplasm.setSeedSource(null); // TODO

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


	public List<BrapiGermplasmMarkerProfiles> getMarkerProfilesFor(int id)
	{
		List<BrapiGermplasmMarkerProfiles> list = new ArrayList<>();

		try (Connection con = Database.INSTANCE.getDataSource().getConnection();
			 PreparedStatement statement = createByIdStatement(con, markrerProfileIdQuery, id);
			 ResultSet resultSet = statement.executeQuery())
		{
			BrapiGermplasmMarkerProfiles profiles = new BrapiGermplasmMarkerProfiles();
			List<String> markerProfileIdList = new ArrayList<>();
			while (resultSet.next())
			{
				profiles.setGermplasmId(resultSet.getInt("germinatebase_id"));
				markerProfileIdList.add(resultSet.getInt("dataset_id") + "-" + resultSet.getInt("germinatebase_id"));
			}
			profiles.setMarkerProfiles(markerProfileIdList);

			list.add(profiles);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return list;
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

		if (name != null)
			synonyms.add(name);
		if (number != null)
			synonyms.add(number);

		return synonyms;
	}

	public List<BrapiGermplasm> getByName(String name, BrapiGermplasm.MatchingMethod matchingMethod)
	{
		List<BrapiGermplasm> resultGermplasm = new ArrayList<>();

		try (Connection con = Database.INSTANCE.getDataSource().getConnection();
			 PreparedStatement statement = createByNameStatement(con, name, matchingMethod);
			 ResultSet resultSet = statement.executeQuery())
		{
			// Keep count of the total number of accessions
			while (resultSet.next())
			{
				resultGermplasm.add(getGermplasm(resultSet));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return resultGermplasm;
	}

	private PreparedStatement createByNameStatement(Connection con, String name, BrapiGermplasm.MatchingMethod matchingMethod)
			throws SQLException
	{
		// Replace the non-sql wildcards with sql wildcards
		name = name.replace("*", "%");
		name = name.replace("?", "_");

		String query;

		switch (matchingMethod)
		{
			case WILDCARD:
				query = getLinesByNameRegex;
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
}
