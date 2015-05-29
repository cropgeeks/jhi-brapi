package hutton.brapi.server;

import hutton.brapi.data.*;
import hutton.brapi.resource.*;

import com.fasterxml.jackson.databind.*;

import com.google.inject.*;

import org.restlet.ext.guice.*;
import org.restlet.ext.jackson.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

/**
 * Created by gs40939 on 27/05/2015.
 */
public class MapByChromServerResource extends SelfInjectingServerResource
{
	@Inject
	private MapDAO mapDAO;

	private String id;
	private String chrom;

	public void doInit()
	{
		super.doInit();
		this.id = (String)getRequestAttributes().get("id");
		this.chrom = (String)getRequestAttributes().get("chrom");
	}

	@Get
	public Representation retrieve()
	{
		MapDetail mapDetail = mapDAO.getByIdAndChromosome(Integer.parseInt(id), chrom);

		if (mapDetail != null)
		{
			JacksonRepresentation<MapDetail> rep = new JacksonRepresentation(mapDetail);
			rep.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

			return rep;
		}

		throw new ResourceException(404);
	}
}
