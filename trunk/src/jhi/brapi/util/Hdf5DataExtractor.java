package jhi.brapi.util;

import java.io.*;
import java.util.*;

import ch.systemsx.cisd.hdf5.*;

public class Hdf5DataExtractor implements AutoCloseable
{
	private IHDF5Reader reader;

	private List<String> hdf5Lines;
	private List<String> hdf5Markers;
	private final String[] stateTable;

	public Hdf5DataExtractor(File hdf5File)
	{
		// Setup input and output files
		this.reader = HDF5Factory.openForReading(hdf5File);

		// Get the line names
		String[] hdf5LinesArray = reader.readStringArray("Lines");
		hdf5Lines = Arrays.asList(hdf5LinesArray);

		// Get the marker names
		String[] hdf5MarkersArray = reader.readStringArray("Markers");
		hdf5Markers = Arrays.asList(hdf5MarkersArray);

		// Get the state table
		stateTable = reader.readStringArray("StateTable");
	}

	public String get(int lineIndex, int markerIndex, GenotypeEncodingParams params)
	{
		return get(hdf5Lines.get(lineIndex), hdf5Markers.get(markerIndex), params);
	}

	public String get(String line, String marker, GenotypeEncodingParams params)
	{
		int markerIndex = hdf5Markers.indexOf(marker);
		int lineIndex = hdf5Lines.indexOf(line);

		String alleleValue = params.getUnknownString();

		if(markerIndex != -1 && lineIndex != -1)
		{
			byte[] genotypes = reader.int8().readMatrixBlock("DataMatrix", 1, hdf5Markers.size(), hdf5Lines.indexOf(line), 0)[0];

			alleleValue = stateTable[genotypes[markerIndex]];

			String[] values;

			if (alleleValue.contains("/"))
			{
				values = alleleValue.split("/");
				alleleValue = jhi.brapi.util.GenotypeEncodingUtils.getString(values[0], values[1], params);
			}
			else if (alleleValue.length() == 2)
			{
				alleleValue = jhi.brapi.util.GenotypeEncodingUtils.getString(Character.toString(alleleValue.charAt(0)), Character.toString(alleleValue.charAt(1)), params);
			}
		}

		return alleleValue;
	}

	@Override
	public void close()
	{
		reader.close();
	}

	public List<String> getLines()
	{ return hdf5Lines; }

	public List<String> getMarkers()
	{ return hdf5Markers; }
}