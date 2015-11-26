package jhi.brapi.server;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

import com.fasterxml.jackson.databind.*;

import org.restlet.ext.jackson.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

/**
 * Queries the database for the Germplasm (germinatebase?) with the given ID then returns a JSON (Jackson) representation of the Germplasm for API
 * clients to consume.
 */
public class GermplasmListServerResource extends ServerResource
{
	private static final String PARAM_NAME            = "name";
	private static final String PARAM_MATCHING_METHOD = "matchMethod";
	private static final String PARAM_PAGE            = "page";
	private static final String PARAM_PAGE_SIZE       = "pageSize";

	private GermplasmDAO germplasmDAO = new GermplasmDAOImpl();

	private String         name;
	private MatchingMethod matchingMethod;

	private int pageSize = 1000;
	private int page = 1;

	@Override
	public void doInit()
	{
		super.doInit();
		this.name = getQueryValue(PARAM_NAME);
		this.matchingMethod = MatchingMethod.getValue(getQueryValue(PARAM_MATCHING_METHOD));

		// Try to parse the page and pageSize as integers
		try
		{
			this.page = Integer.parseInt(getQueryValue(PARAM_PAGE));
		}
		catch(Exception e)
		{
		}

		try
		{
			this.pageSize = Integer.parseInt(getQueryValue(PARAM_PAGE_SIZE));
		}
		catch(Exception e)
		{
		}
	}

	@Get
	public Representation retrieve()
	{
		if (name != null)
		{
			GermplasmSearchListPagination germplasmSearchListPagination = germplasmDAO.getByName(name, matchingMethod, page, pageSize);

			JacksonRepresentation<GermplasmSearchListPagination> rep = new JacksonRepresentation<>(germplasmSearchListPagination);
			rep.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

			return rep;
		}
		else
		{
			GermplasmList germplasmList = germplasmDAO.getAll();

			JacksonRepresentation<GermplasmList> rep = new JacksonRepresentation<>(germplasmList);
			rep.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

			return rep;
		}
	}

	@Put
	public void store(Representation germplasm)
	{
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public enum MatchingMethod
	{
		EXACT,
		WILDCARD;

		public static MatchingMethod getValue(String input)
		{
			if(input == null || input.equals(""))
			{
				return EXACT;
			}
			else
			{
				try
				{
					return MatchingMethod.valueOf(input.toUpperCase());
				}
				catch (Exception e)
				{
					// TODO: Return a 501 HTTP error code
					return EXACT;
				}
			}
		}
	}
}