package brapi.client;

import brapi.resource.*;

import org.restlet.resource.*;

/**
 * Created by gs40939 on 01/05/2015.
 */
public class Client
{
	public static void main(String[] args)
	{
		new Client().testAPI();
	}

	private void testAPI()
	{
		testGermplasm1();

		testMaps();

		testMap();

		testMarkerProfile();

		testMarkerProfileOnlySpecificMap();
	}

	private void testGermplasm1()
	{
		ClientResource clientResource = new ClientResource("http://localhost:8080/germplasm/1");
		Germplasm germplasm = clientResource.get(Germplasm.class);
		System.out.println(germplasm);
	}

	private void testMaps()
	{
		ClientResource mapResource = new ClientResource("http://localhost:8080/maps/");
		Maps maps = mapResource.get(Maps.class);
		System.out.println(maps);
	}

	private void testMap()
	{
		ClientResource mapResource = new ClientResource("http://localhost:8080/maps/20");
		MapDetail map = mapResource.get(MapDetail.class);
		System.out.println(map);
	}

	private void testMarkerProfile()
	{
		ClientResource markerResource = new ClientResource("http://localhost:8080/germplasm/1/markerprofiles");
		MarkerProfile markerProfile = markerResource.get(MarkerProfile.class);
		System.out.println(markerProfile);
	}

	private void testMarkerProfileOnlySpecificMap()
	{
		ClientResource markerResource = new ClientResource("http://localhost:8080/germplasm/1/markerprofiles?map=20");
		MarkerProfile markerProfile = markerResource.get(MarkerProfile.class);
		System.out.println(markerProfile);
	}
}
