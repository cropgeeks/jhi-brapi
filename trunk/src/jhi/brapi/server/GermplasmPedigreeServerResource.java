package jhi.brapi.server;

import jhi.brapi.data.*;
import jhi.brapi.resource.*;

import org.restlet.representation.*;
import org.restlet.resource.*;

/**
 * Queries the database for the Germplasm pedigree with the given ID then returns a JSON (Jackson) representation of the Germplasm pedigree for API
 * clients to consume.
 */
public class GermplasmPedigreeServerResource extends BaseBrapiServerResource
{
	private GermplasmDAO germplasmDAO = new GermplasmDAO();

	// The ID from the URI (need to get this in overridden doInit method)
	private String id;

	// The notation used to generate the pedigree string
	private PedigreeNotation notation;

	@Override
	public void doInit()
	{
		super.doInit();
		this.id = (String) getRequestAttributes().get("id");
		this.notation = PedigreeNotation.getValue(getQueryValue("notation"));
	}

	@Get("json")
	public Pedigree getJson()
	{
		Pedigree pedigree = germplasmDAO.getPedigreeById(Integer.parseInt(id));

		if (pedigree != null)
			return pedigree;

		throw new ResourceException(404);
	}

	@Put
	public void store(Representation germplasm)
	{
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public enum PedigreeNotation
	{
		PURDY,
		LAMACRAFT;

		public static PedigreeNotation getValue(String input)
		{
			if (input == null || input.equals(""))
			{
				return PURDY;
			}
			else
			{
				try
				{
					return PedigreeNotation.valueOf(input.toUpperCase());
				}
				catch (Exception e)
				{
					// TODO: Return a 501 HTTP error code
					return PURDY;
				}
			}
		}
	}
}