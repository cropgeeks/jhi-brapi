package hutton.brapi.server;

import com.fasterxml.jackson.databind.*;
import com.google.inject.*;

import org.restlet.ext.guice.*;
import org.restlet.ext.jackson.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

import hutton.brapi.data.*;
import hutton.brapi.resource.*;

/**
 * Queries the database for the Germplasm pedigree with the given ID then returns a JSON (Jackson) representation of the Germplasm pedigree for API
 * clients to consume.
 */
public class GermplasmPedigreeServerResource extends SelfInjectingServerResource
{
	@Inject
	private GermplasmDAO germplasmDAO;

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

	@Get
	public Representation retrieve()
	{
		Pedigree pedigree = germplasmDAO.getPedigreeById(Integer.parseInt(id));

		if (pedigree != null)
		{
			JacksonRepresentation<Pedigree> rep = new JacksonRepresentation<>(pedigree);
			rep.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

			return rep;
		}

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