package uk.ac.hutton.brapi.client;

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
		uk.ac.hutton.brapi.resource.Germplasm germplasm = clientResource.get(uk.ac.hutton.brapi.resource.Germplasm.class);
		System.out.println(germplasm);
	}

	private void testMaps()
	{
		ClientResource mapResource = new ClientResource("http://localhost:8080/maps/");
		uk.ac.hutton.brapi.resource.Maps maps = mapResource.get(uk.ac.hutton.brapi.resource.Maps.class);
		System.out.println(maps);
	}

	private void testMap()
	{
		ClientResource mapResource = new ClientResource("http://localhost:8080/maps/20");
		uk.ac.hutton.brapi.resource.MapDetail map = mapResource.get(uk.ac.hutton.brapi.resource.MapDetail.class);
		System.out.println(map);
	}

	private void testMarkerProfile()
	{
		ClientResource markerResource = new ClientResource("http://localhost:8080/germplasm/1/markerprofiles");
		uk.ac.hutton.brapi.resource.MarkerProfile markerProfile = markerResource.get(uk.ac.hutton.brapi.resource.MarkerProfile.class);
		System.out.println(markerProfile);
	}

	private void testMarkerProfileOnlySpecificMap()
	{
		ClientResource markerResource = new ClientResource("http://localhost:8080/germplasm/1/markerprofiles?map=20");
		uk.ac.hutton.brapi.resource.MarkerProfile markerProfile = markerResource.get(uk.ac.hutton.brapi.resource.MarkerProfile.class);
		System.out.println(markerProfile);
	}
}
