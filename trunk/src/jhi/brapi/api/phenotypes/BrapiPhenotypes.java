package jhi.brapi.api.phenotypes;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrapiPhenotypes
{
	private String observationUnitDbId;
	private String studyDbId;
	private String studyName;
	private String studyLocationDbId;
	private String studyLocation;
	private String observationLevel;
	private String observationLevels;
	private String plotNumber;
	private String plantNumber;
	private String blockNumber;
	private String replicate;
	private String programName;
	private String germplasmDbId;
	private String germplasmName;
	private String X;
	private String Y;
	private List<BrapiTreatment> treatments;
	private List<BrapiObservation> observations;

	public String getObservationUnitDbId()
	{
		return observationUnitDbId;
	}

	public BrapiPhenotypes setObservationUnitDbId(String observationUnitDbId)
	{
		this.observationUnitDbId = observationUnitDbId;
		return this;
	}

	public String getStudyDbId()
	{
		return studyDbId;
	}

	public BrapiPhenotypes setStudyDbId(String studyDbId)
	{
		this.studyDbId = studyDbId;
		return this;
	}

	public String getStudyName()
	{
		return studyName;
	}

	public BrapiPhenotypes setStudyName(String studyName)
	{
		this.studyName = studyName;
		return this;
	}

	public String getStudyLocationDbId()
	{
		return studyLocationDbId;
	}

	public BrapiPhenotypes setStudyLocationDbId(String studyLocationDbId)
	{
		this.studyLocationDbId = studyLocationDbId;
		return this;
	}

	public String getStudyLocation()
	{
		return studyLocation;
	}

	public BrapiPhenotypes setStudyLocation(String studyLocation)
	{
		this.studyLocation = studyLocation;
		return this;
	}

	public String getObservationLevel()
	{
		return observationLevel;
	}

	public BrapiPhenotypes setObservationLevel(String observationLevel)
	{
		this.observationLevel = observationLevel;
		return this;
	}

	public String getObservationLevels()
	{
		return observationLevels;
	}

	public BrapiPhenotypes setObservationLevels(String observationLevels)
	{
		this.observationLevels = observationLevels;
		return this;
	}

	public String getPlotNumber()
	{
		return plotNumber;
	}

	public BrapiPhenotypes setPlotNumber(String plotNumber)
	{
		this.plotNumber = plotNumber;
		return this;
	}

	public String getPlantNumber()
	{
		return plantNumber;
	}

	public BrapiPhenotypes setPlantNumber(String plantNumber)
	{
		this.plantNumber = plantNumber;
		return this;
	}

	public String getBlockNumber()
	{
		return blockNumber;
	}

	public BrapiPhenotypes setBlockNumber(String blockNumber)
	{
		this.blockNumber = blockNumber;
		return this;
	}

	public String getReplicate()
	{
		return replicate;
	}

	public BrapiPhenotypes setReplicate(String replicate)
	{
		this.replicate = replicate;
		return this;
	}

	public String getProgramName()
	{
		return programName;
	}

	public BrapiPhenotypes setProgramName(String programName)
	{
		this.programName = programName;
		return this;
	}

	public String getGermplasmDbId()
	{
		return germplasmDbId;
	}

	public BrapiPhenotypes setGermplasmDbId(String germplasmDbId)
	{
		this.germplasmDbId = germplasmDbId;
		return this;
	}

	public String getGermplasmName()
	{
		return germplasmName;
	}

	public BrapiPhenotypes setGermplasmName(String germplasmName)
	{
		this.germplasmName = germplasmName;
		return this;
	}

	public String getX()
	{
		return X;
	}

	public BrapiPhenotypes setX(String x)
	{
		X = x;
		return this;
	}

	public String getY()
	{
		return Y;
	}

	public BrapiPhenotypes setY(String y)
	{
		Y = y;
		return this;
	}

	public List<BrapiTreatment> getTreatments()
	{
		return treatments;
	}

	public BrapiPhenotypes setTreatments(List<BrapiTreatment> treatments)
	{
		this.treatments = treatments;
		return this;
	}

	public List<BrapiObservation> getObservations()
	{
		return observations;
	}

	public BrapiPhenotypes setObservations(List<BrapiObservation> observations)
	{
		this.observations = observations;
		return this;
	}
}
