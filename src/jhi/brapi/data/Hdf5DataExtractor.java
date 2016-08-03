package jhi.brapi.data;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import ch.systemsx.cisd.hdf5.*;

/**
 * @author Sebastian Raubach
 */
public class Hdf5DataExtractor implements AutoCloseable
{
	private IHDF5Reader reader;

	private List<String> hdf5Lines;
	private List<String> originalNames;
	private List<String> hdf5Markers;
	private final String[] stateTable;

	public Hdf5DataExtractor(File hdf5File)
	{
		// Setup input and output files
		this.reader = HDF5Factory.openForReading(hdf5File);

		// Get the line names
		this.hdf5Lines = reader.getGroupMembers("Lines");
		originalNames = hdf5Lines.stream()
								 .map(line -> line.replaceAll("/", "_"))
								 .collect(Collectors.toList());

		// Get the marker names
		String[] hdf5MarkersArray = reader.readStringArray("Markers");
		hdf5Markers = Arrays.asList(hdf5MarkersArray);

		// Get the state table
		stateTable = reader.readStringArray("StateTable");
	}

	public List<String> getLines()
	{
		return originalNames;
	}

	public List<String> getMarkers()
	{
		return hdf5Markers;
	}

	public String get(int lineIndex, int markerIndex, GenotypeEncodingParams params)
	{
		return get(hdf5Lines.get(lineIndex), hdf5Markers.get(markerIndex), params);
	}

	public String get(String line, String marker, GenotypeEncodingParams params)
	{
		line = line.replace("/", "_");

		int markerIndex = hdf5Markers.indexOf(marker);

		byte[] genotypes = reader.int8().readArray("Lines/" + line);

		String alleleValue = stateTable[genotypes[markerIndex]];

		String[] values;

		if(alleleValue.contains("/"))
		{
			values = alleleValue.split("/");
			alleleValue = GenotypeEncodingUtils.getString(values[0], values[1], params);
		}
		else if (alleleValue.length() == 2)
		{
			alleleValue = GenotypeEncodingUtils.getString(Character.toString(alleleValue.charAt(0)), Character.toString(alleleValue.charAt(1)), params);
		}

		return alleleValue;
	}

	@Override
	public void close()
	{
		reader.close();
	}
}