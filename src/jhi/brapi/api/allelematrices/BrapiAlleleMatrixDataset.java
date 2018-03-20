package jhi.brapi.api.allelematrices;

import java.sql.*;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrapiAlleleMatrixDataset
{
	private String name;
	private String matrixDbId;
	private String description;
	private Date lastUpdated;
	private String studyDbId;
	private int markerCount;
	private int sampleCount;

	public String getName()
		{ return name; }

	public void setName(String name)
		{ this.name = name; }

	public String getMatrixDbId()
		{ return matrixDbId; }

	public void setMatrixDbId(String matrixDbId)
		{ this.matrixDbId = matrixDbId; }

	public String getDescription()
		{ return description; }

	public void setDescription(String description)
		{ this.description = description; }

	public Date getLastUpdated()
		{ return lastUpdated; }

	public void setLastUpdated(Date lastUpdated)
		{ this.lastUpdated = lastUpdated; }

	public String getStudyDbId()
		{ return studyDbId; }

	public void setStudyDbId(String studyDbId)
		{ this.studyDbId = studyDbId; }

	public int getMarkerCount()
		{ return markerCount; }

	public void setMarkerCount(int markerCount)
		{ this.markerCount = markerCount; }

	public int getSampleCount()
		{ return sampleCount; }

	public void setSampleCount(int sampleCount)
		{ this.sampleCount = sampleCount; }
}
