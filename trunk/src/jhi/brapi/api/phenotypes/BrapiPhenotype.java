package jhi.brapi.api.phenotypes;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrapiPhenotype
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

	public BrapiPhenotype setObservationUnitDbId(String observationUnitDbId)
	{
		this.observationUnitDbId = observationUnitDbId;
		return this;
	}

	public String getStudyDbId()
	{
		return studyDbId;
	}

	public BrapiPhenotype setStudyDbId(String studyDbId)
	{
		this.studyDbId = studyDbId;
		return this;
	}

	public String getStudyName()
	{
		return studyName;
	}

	public BrapiPhenotype setStudyName(String studyName)
	{
		this.studyName = studyName;
		return this;
	}

	public String getStudyLocationDbId()
	{
		return studyLocationDbId;
	}

	public BrapiPhenotype setStudyLocationDbId(String studyLocationDbId)
	{
		this.studyLocationDbId = studyLocationDbId;
		return this;
	}

	public String getStudyLocation()
	{
		return studyLocation;
	}

	public BrapiPhenotype setStudyLocation(String studyLocation)
	{
		this.studyLocation = studyLocation;
		return this;
	}

	public String getObservationLevel()
	{
		return observationLevel;
	}

	public BrapiPhenotype setObservationLevel(String observationLevel)
	{
		this.observationLevel = observationLevel;
		return this;
	}

	public String getObservationLevels()
	{
		return observationLevels;
	}

	public BrapiPhenotype setObservationLevels(String observationLevels)
	{
		this.observationLevels = observationLevels;
		return this;
	}

	public String getPlotNumber()
	{
		return plotNumber;
	}

	public BrapiPhenotype setPlotNumber(String plotNumber)
	{
		this.plotNumber = plotNumber;
		return this;
	}

	public String getPlantNumber()
	{
		return plantNumber;
	}

	public BrapiPhenotype setPlantNumber(String plantNumber)
	{
		this.plantNumber = plantNumber;
		return this;
	}

	public String getBlockNumber()
	{
		return blockNumber;
	}

	public BrapiPhenotype setBlockNumber(String blockNumber)
	{
		this.blockNumber = blockNumber;
		return this;
	}

	public String getReplicate()
	{
		return replicate;
	}

	public BrapiPhenotype setReplicate(String replicate)
	{
		this.replicate = replicate;
		return this;
	}

	public String getProgramName()
	{
		return programName;
	}

	public BrapiPhenotype setProgramName(String programName)
	{
		this.programName = programName;
		return this;
	}

	public String getGermplasmDbId()
	{
		return germplasmDbId;
	}

	public BrapiPhenotype setGermplasmDbId(String germplasmDbId)
	{
		this.germplasmDbId = germplasmDbId;
		return this;
	}

	public String getGermplasmName()
	{
		return germplasmName;
	}

	public BrapiPhenotype setGermplasmName(String germplasmName)
	{
		this.germplasmName = germplasmName;
		return this;
	}

	public String getX()
	{
		return X;
	}

	public BrapiPhenotype setX(String x)
	{
		X = x;
		return this;
	}

	public String getY()
	{
		return Y;
	}

	public BrapiPhenotype setY(String y)
	{
		Y = y;
		return this;
	}

	public List<BrapiTreatment> getTreatments()
	{
		return treatments;
	}

	public BrapiPhenotype setTreatments(List<BrapiTreatment> treatments)
	{
		this.treatments = treatments;
		return this;
	}

	public List<BrapiObservation> getObservations()
	{
		return observations;
	}

	public BrapiPhenotype setObservations(List<BrapiObservation> observations)
	{
		this.observations = observations;
		return this;
	}
}
