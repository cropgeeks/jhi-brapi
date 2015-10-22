package jhi.brapi.server;

import java.util.*;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

import org.junit.*;
import org.junit.runner.*;

import org.mockito.*;
import org.mockito.runners.*;

import org.restlet.*;
import org.restlet.Request;
import org.restlet.data.*;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

/**
 * Created by gs40939 on 13/05/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class GermplasmListServerResourceTest
{
	@Mock
	GermplasmDAO germplasmDAO;

	@InjectMocks
	GermplasmListServerResource resource;

	@Before
	public void setUp()
	{
		resource = new GermplasmListServerResource();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testComponent() throws Exception
	{
		germplasmDAO = mock(GermplasmDAOImpl.class);

		GermplasmList germplasmList = new GermplasmList();
		List<Germplasm> list = new ArrayList<>();
		Germplasm g = new Germplasm();
		g.setGermplasmName("Blah");
		g.setGermplasmId(1);
		list.add(g);
		germplasmList.setGermplasm(list);

		when(germplasmDAO.getAll()).thenReturn(germplasmList);

		Request request = new Request(Method.GET, "/brapi/germplasm");
		Response response = new Response(request);
		resource.init(new Context(), request, response);
		resource.handle();

		assertTrue(response.getStatus().isSuccess());
		assertNotNull(response.getEntityAsText());
	}
}