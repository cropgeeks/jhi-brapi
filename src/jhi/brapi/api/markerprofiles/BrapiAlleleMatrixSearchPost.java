package jhi.brapi.api.markerprofiles;

import java.util.*;

import jhi.brapi.api.*;

public class BrapiAlleleMatrixSearchPost extends BasicPost
{
	private List<String> markerprofileDbId;
	private List<String> markerDbId;
	private List<String> matrixDbId;
	private String format;
	private boolean expandHomozygotes;
	private String unknownString;
	private String sepPhased;
	private String sepUnphased;

	public List<String> getMarkerprofileDbId()
		{ return markerprofileDbId; }

	public void setMarkerprofileDbId(List<String> markerprofileDbId)
		{ this.markerprofileDbId = markerprofileDbId; }

	public List<String> getMarkerDbId()
		{ return markerDbId; }

	public void setMarkerDbId(List<String> markerDbId)
		{ this.markerDbId = markerDbId; }

	public List<String> getMatrixDbId()
		{ return matrixDbId; }

	public void setMatrixDbId(List<String> matrixDbId)
		{ this.matrixDbId = matrixDbId; }

	public String getFormat()
		{ return format; }

	public void setFormat(String format)
		{ this.format = format; }

	public boolean isExpandHomozygotes()
		{ return expandHomozygotes; }

	public void setExpandHomozygotes(boolean expandHomozygotes)
		{ this.expandHomozygotes = expandHomozygotes; }

	public String getUnknownString()
		{ return unknownString; }

	public void setUnknownString(String unknownString)
		{ this.unknownString = unknownString; }

	public String getSepPhased()
		{ return sepPhased; }

	public void setSepPhased(String sepPhased)
		{ this.sepPhased = sepPhased; }

	public String getSepUnphased()
		{ return sepUnphased; }

	public void setSepUnphased(String sepUnphased)
		{ this.sepUnphased = sepUnphased; }

	@Override
	public String toString()
	{
		return "BrapiAlleleMatrixSearchPost{" +
			"markerprofileDbId=" + markerprofileDbId +
			", markerDbId=" + markerDbId +
			", matrixDbId=" + matrixDbId +
			", format='" + format + '\'' +
			", expandHomozygotes=" + expandHomozygotes +
			", unknownString='" + unknownString + '\'' +
			", sepPhased='" + sepPhased + '\'' +
			", sepUnphased='" + sepUnphased + '\'' +
			", pageSize=" + pageSize +
			", page=" + page +
			'}';
	}
}
