package jhi.brapi.data;

import java.util.*;

import jhi.brapi.resource.*;

/**
 * Created by gs40939 on 16/06/2015.
 */
public interface AlleleMatrixDAO
{
	AlleleMatrix get(List<String> markerProfileIds);

	AlleleMatrix get(List<String> markerProfileIds, List<String> markerIds);
}
