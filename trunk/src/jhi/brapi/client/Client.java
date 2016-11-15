package jhi.brapi.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import jhi.brapi.resource.*;

import org.restlet.data.Encoding;
import org.restlet.data.Protocol;
import org.restlet.engine.application.*;
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
		BrapiGermplasm germplasm = clientResource.get(BrapiGermplasm.class);
		System.out.println(germplasm);
	}

	private void testMaps()
	{
		ClientResource mapResource = new ClientResource("http://localhost:8080//brapi/maps/");

		LinkedHashMap hashMap = mapResource.get(LinkedHashMap.class);
		BrapiBaseResource<BrapiGenomeMap> br = new ObjectMapper().convertValue(hashMap,
			new TypeReference<BrapiBaseResource<BrapiGenomeMap>>() {});
		
//		List<BrapiGenomeMap> maps = br.getResult();
//
//		System.out.println(maps);
	}

	private void testMap()
	{
		ClientResource mapResource = new ClientResource("http://localhost:8080//brapi/maps/20");
		BrapiMapMetaData map = mapResource.get(BrapiMapMetaData.class);
		System.out.println(map);
	}

	private void testGermplasmMarkerProfile()
	{
		ClientResource markerResource = new ClientResource("http://localhost:8080//brapi/germplasm/1/markerprofiles");
		BrapiMarkerProfile markerProfile = markerResource.get(BrapiMarkerProfile.class);
		System.out.println(markerProfile);
	}

	private void testMarkerProfileOnlySpecificMap()
	{
		ClientResource markerResource = new ClientResource("http://localhost:8080//brapi/germplasm/1/markerprofiles?map=20");
		jhi.brapi.resource.BrapiMarkerProfile markerProfile = markerResource.get(BrapiMarkerProfile.class);
		System.out.println(markerProfile);
	}

	private void testMarkerProfile()
	{
		ClientResource markerResource = new ClientResource("http://localhost:8080//brapi/markerprofiles/13-1/");
		BrapiMarkerProfile markerProfile = markerResource.get(BrapiMarkerProfile.class);
		System.out.println(markerProfile);
	}
}
