package jhi.brapi.data;

import java.util.*;

/**
 * @author Sebastian Raubach
 */
public class GenotypeEncodingUtils
{
	public static String getString(String allele1, String allele2, GenotypeEncodingParams params)
	{
		allele1 = fixAllele(allele1, params.getUnknownString()); // Replace empty string and dash into the unknownString
		allele2 = fixAllele(allele2, params.getUnknownString()); // Replace empty string and dash into the unknownString

		if (Objects.equals(allele1, params.getUnknownString()) && Objects.equals(allele2, params.getUnknownString())) // If both are unknown return empty string
			return "";
		else if (Objects.equals(allele1, params.getUnknownString())) // If only the first is unknown, return the second
			return allele2;
		else if (Objects.equals(allele1, params.getUnknownString())) // If only the second is unknown, return the first
			return allele1;
		else if (Objects.equals(allele1, allele2)) // If both are the same, return the first
		{
			if(params.isCollapse())
				return allele1;
			else
				return allele1 + params.getSepUnphased() + allele2;
		}
		else // Else combine them
			return allele1 + params.getSepUnphased() + allele2;
	}

	private static String fixAllele(String input, String unknownString)
	{
		if (Objects.equals(input, "") || Objects.equals(input, "-"))
			return unknownString;
		else
			return input;
	}
}
