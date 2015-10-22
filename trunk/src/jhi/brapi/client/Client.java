package jhi.brapi.client;

import jhi.brapi.resource.*;

import org.restlet.data.Encoding;
import org.restlet.data.Protocol;
import org.restlet.engine.application.*;
import org.restlet.ext.jackson.JacksonRepresentation;
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

		testGermplasmMarkerProfile();

		testMarkerProfileOnlySpecificMap();
	}

	private void testGermplasm1()
	{
		org.restlet.Client client = new org.restlet.Client(Protocol.HTTP);
		Decoder decoder = new Decoder(client.getContext(), false, true);
		decoder.setNext(client);

		ClientResource clientResource = new ClientResource("http://localhost:8080//brapi/germplasm/1");
		clientResource.setNext(decoder);
		clientResource.accept(Encoding.GZIP);
		Germplasm germplasm = clientResource.get(Germplasm.class);
		System.out.println(germplasm);
	}

	private void testMaps()
	{
		ClientResource mapResource = new ClientResource("http://localhost:8080//brapi/maps/");
		MapList mapList = mapResource.get(MapList.class);
//		java.util.List<Map> maps = mapResource.get()
		System.out.println(mapList);
	}

	private void testMap()
	{
		ClientResource mapResource = new ClientResource("http://localhost:8080//brapi/maps/20");
		MapDetail map = mapResource.get(MapDetail.class);
		System.out.println(map);
	}

	private void testGermplasmMarkerProfile()
	{
		ClientResource markerResource = new ClientResource("http://localhost:8080//brapi/germplasm/1/markerprofiles");
		MarkerProfile markerProfile = markerResource.get(MarkerProfile.class);
		System.out.println(markerProfile);
	}

	private void testMarkerProfileOnlySpecificMap()
	{
		ClientResource markerResource = new ClientResource("http://localhost:8080//brapi/germplasm/1/markerprofiles?map=20");
		jhi.brapi.resource.MarkerProfile markerProfile = markerResource.get(MarkerProfile.class);
		System.out.println(markerProfile);
	}

	private void testMarkerProfile()
	{
		ClientResource markerResource = new ClientResource("http://localhost:8080//brapi/markerprofiles/13-1/");
		MarkerProfile markerProfile = markerResource.get(MarkerProfile.class);
		System.out.println(markerProfile);
	}
}
