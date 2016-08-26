/*
 * // Copyright 2009-2016 Information & Computational Sciences, JHI. All rights
 * // reserved. Use is subject to the accompanying licence terms.
 */

package jhi.brapi.data;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import ch.systemsx.cisd.hdf5.*;

/**
 * @author Sebastian Raubach
 */
public class Hdf5ToGenotypeConverter
{
	private static final String LINES   = "Lines";
	private static final String MARKERS = "Markers";

	private static final String DATA = "DataMatrix";

	private static final String STATE_TABLE = "StateTable";

	private File hdf5File;
	private Map<String, String> germplasmIdToName = null;
	private Map<String, String> markerIdToName = null;

	private HashMap<String, Integer> lineInds;
	private HashMap<String, Integer> markerInds;

	private GenotypeEncodingParams params;

	private IHDF5Reader reader;

	public Hdf5ToGenotypeConverter(File hdf5File, Map<String, String> germplasmIdToName, Map<String, String> markerIdToName, GenotypeEncodingParams params)
	{
		// Setup input and output files
		this.hdf5File = hdf5File;
		this.germplasmIdToName = germplasmIdToName;
		this.markerIdToName = markerIdToName;
		this.params = params;
	}

	public void readInput()
	{
		reader = HDF5Factory.openForReading(hdf5File);

		long s = System.currentTimeMillis();

		System.out.println();
		System.out.println("Hdf5 file opened for reading: " + (System.currentTimeMillis() - s) + " (ms)");

		s = System.currentTimeMillis();
		// Load lines from HDF5 and find the indices of our loaded lines
		String[] hdf5LinesArray = reader.readStringArray(LINES);

		lineInds = new HashMap<>();
		for (int i = 0; i < hdf5LinesArray.length; i++)
			lineInds.put(hdf5LinesArray[i], i);

		System.out.println();
		System.out.println("Read and filtered lines: " + (System.currentTimeMillis() - s) + " (ms)");

		s = System.currentTimeMillis();
		// Load markers from HDF5 and find the indices of our loaded markers
		String[] hdf5MarkersArray = reader.readStringArray(MARKERS);

		markerInds = new HashMap<>();
		for (int i = 0; i < hdf5MarkersArray.length; i++)
			markerInds.put(hdf5MarkersArray[i], i);

		System.out.println();
		System.out.println("Read and filtered markers: " + (System.currentTimeMillis() - s) + " (ms)");

		reader.close();
	}

	public void extractData(String outputFile, String headerLines)
	{
		System.out.println();
		long s = System.currentTimeMillis();
		List<Integer> lineIndices = germplasmIdToName.entrySet().parallelStream().map(line -> lineInds.get(line.getValue())).collect(Collectors.toList());
		System.out.println("Read and mapped lines: " + (System.currentTimeMillis() - s) + " (ms)");

		reader = HDF5Factory.openForReading(hdf5File);

		s = System.currentTimeMillis();
		String[] stateTable = reader.readStringArray(STATE_TABLE);
		System.out.println("Read statetable: " + (System.currentTimeMillis() - s) + " (ms)");

		// Write our output file line by line
		try (PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF8"))))
		{
			// Output any extra header lines that have been provided such as db link urls
			if (!headerLines.isEmpty())
				writer.print(headerLines);

			// Write the header line of a Flapjack file
			writer.println(germplasmIdToName.entrySet().parallelStream().map(Map.Entry::getKey).collect(Collectors.joining("\t", "markerprofileDbIds\t", "")));

			s = System.currentTimeMillis();

			markerIdToName.entrySet().stream().forEachOrdered(marker ->
			{
				// Read in a line (all of its alleles from file)
				// Get from DATA, 1 row, markerInds.size() columns, start from row lineInds.get(lineName) and column 0.
				// The resulting 2d array only contains one 1d array. Take that as the lines genotype data.
				byte[][] genotypes = reader.int8().readMatrixBlock(DATA, lineInds.size(), 1, 0, markerInds.get(marker.getValue()));
				String outputGenotypes = createGenotypeFlatFileString(marker.getKey(), genotypes, lineIndices, stateTable);
				writer.println(outputGenotypes);
			});
			System.out.println("Output lines to genotype file: " + (System.currentTimeMillis() - s) + " (ms)");
		}
		catch (IOException e) { e.printStackTrace(); }

		reader.close();

		System.out.println();
		System.out.println("HDF5 file converted to Flapjack genotype format");
	}

	private String createGenotypeFlatFileString(String markerId, byte[][] genotypes, List<Integer> lineIndices, String[] stateTable)
	{
		// Collect the alleles which match the line and markers we're looking for
		return lineIndices.parallelStream()
			.map(index -> genotypes[index][0])
			.map(allele -> {
				String decoded = stateTable[allele];

				if(decoded.length() == 2)
				{
					String[] split = decoded.split("(?!^)");
					decoded = GenotypeEncodingUtils.getString(split[0], split[1], params);
				}
				else if(decoded.length() == 1)
				{
					decoded = GenotypeEncodingUtils.getString(decoded, decoded, params);
				}

				return decoded;
			})
			.collect(Collectors.joining("\t", markerId + "\t", ""));
	}
}