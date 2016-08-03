package jhi.brapi.data;

import java.util.*;

/**
 * @author Sebastian Raubach
 */
public class GenotypeEncodingUtils
{
	public static String getString(String allele1, String allele2, GenotypeEncodingParams params)
	{
		allele1 = fixAllele(allele1, params.getUnknownString()); // Replace empty string and dash with the unknownString
		allele2 = fixAllele(allele2, params.getUnknownString()); // Replace empty string and dash with the unknownString

		if (Objects.equals(allele1, params.getUnknownString()) && Objects.equals(allele2, params.getUnknownString())) // If both are unknown return unknownString
			return params.getUnknownString(); // TODO: check if this is correct or if this should return an empty string
		else if (Objects.equals(allele1, params.getUnknownString())) // If only the first is unknown, return the second
			return allele2;
		else if (Objects.equals(allele2, params.getUnknownString())) // If only the second is unknown, return the first
			return allele1;
		else if (Objects.equals(allele1, allele2)) // If both are the same
		{
			if(params.isCollapse()) // Return the first if we collapse
				return allele1;
			else // Else return the combination
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
