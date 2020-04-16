package jhi.brapi.api.germplasm;

import java.util.*;

import jhi.brapi.api.*;

import jhi.brapi.api.Status;
import org.restlet.resource.*;

/**
 * Given an id, returns the markerprofile ids which relate to the germplasm indicated by id.
 */
public class ServerGermplasm extends BaseBrapiServerResource
{
	private static final String PARAM_COMMON_CROP_NAME = "commonCropName";
	private static final String PARAM_GERMPLASM_PUI = "germplasmPUI";
	private static final String PARAM_GERMPLASM_DB_ID= "germplasmDbId";
	private static final String PARAM_GERMPLASM_NAME = "germplasmName";

	private GermplasmDAO germplasmDAO = new GermplasmDAO();

	private String germplasmPUI;
	private String germplasmDbId;
	private String germplasmName;
	private String commonCropName;

	@Override
	public void doInit()
	{
		super.doInit();
		commonCropName = getQueryValue(PARAM_COMMON_CROP_NAME);
		germplasmDbId = getQueryValue(PARAM_GERMPLASM_DB_ID);
		germplasmPUI = getQueryValue(PARAM_GERMPLASM_PUI);
		germplasmName = getQueryValue(PARAM_GERMPLASM_NAME);

	}

	@Get("json")
	public BrapiListResource<BrapiGermplasm> getJson()
	{
		Map<String, List<String>> parameters = new LinkedHashMap<>();
		addParameter(parameters, "germinatebase.id", germplasmDbId);
		addParameter(parameters, "germinatebase.name", germplasmName);

		BrapiListResource<BrapiGermplasm> result = new BrapiListResource<BrapiGermplasm>();
		try
		{
			result = germplasmDAO.getMcpdForSearch(parameters, currentPage, pageSize);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result.getMetadata().getStatus().add(new Status("500", "The server could not complete the request"));
			setStatus(org.restlet.data.Status.SERVER_ERROR_INTERNAL);
			return result;
		}

		if (result.data().isEmpty())
		{
			result.getMetadata().getStatus().add(new Status("40", "No objects found for given parameters"));
			setStatus(org.restlet.data.Status.CLIENT_ERROR_BAD_REQUEST);
		}

		return result;
	}
}