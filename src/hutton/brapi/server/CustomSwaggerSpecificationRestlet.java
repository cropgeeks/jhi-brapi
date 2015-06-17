package hutton.brapi.server;

import org.restlet.Context;
import org.restlet.ext.apispark.internal.conversion.swagger.v1_2.model.ApiDeclaration;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.ext.swagger.SwaggerSpecificationRestlet;
import org.restlet.representation.Representation;

import java.io.IOException;

/**
 * Created by gs40939 on 09/06/2015.
 */
public class CustomSwaggerSpecificationRestlet extends SwaggerSpecificationRestlet
{
	public CustomSwaggerSpecificationRestlet(Context context)
	{
		super(context);
	}

	@Override
	public Representation getApiDeclaration(String category)
	{
		try
		{
			ApiDeclaration apiDeclaration = new JacksonRepresentation<>(super.getApiDeclaration(category), ApiDeclaration.class).getObject();
			// manipulate the API declaration object as you wish
			apiDeclaration.setBasePath("http://localhost:8080/brapi");

			return new JacksonRepresentation<ApiDeclaration>(apiDeclaration);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
}
