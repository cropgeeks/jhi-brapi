package jhi.brapi.api.allelematrices;

import java.util.*;

import jhi.brapi.api.*;


public class ServerAlleleMatrixDataset extends BaseBrapiServerResource
{
	private AlleleMatriceDAO alleleMatriceDAO = new AlleleMatriceDAO();

	private String studyId;

	public void doInit()
	{
		super.doInit();

		studyId = getQueryValue("studyDbId");
	}

	private void addParameter(Map<String, List<String>> map, String key, String value)
	{
		if (value != null && value.length() != 0)
			map.put(key, Collections.singletonList(value));
	}

	public BrapiListResource<BrapiAlleleMatrixDataset> getJson()
	{
		LinkedHashMap<String, List<String>> parameters = new LinkedHashMap<>();
		// We only want to return datasets we have genotype data for
		addParameter(parameters, "experimenttypes.description", "genotype");
		// The user might be subsetting by a given studyId
		addParameter(parameters, "datasets.id", studyId);

		return alleleMatriceDAO.getAll(getContext(), parameters, currentPage, pageSize);
	}
}