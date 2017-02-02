package jhi.brapi.util;

import jhi.brapi.*;
import org.restlet.*;
import org.restlet.data.*;
import org.restlet.security.*;

/**
 *	An implementation of Verifier which uses our token based authentication
 *	to verify if client sending a request is allowed to query the given
 *	resource. This code is run for all requests on authenticated routes within
 *	our application
 */
public class TokenBasedVerifier implements Verifier
{
	@Override
	public int verify(Request request, Response response)
	{
		ChallengeResponse cr = request.getChallengeResponse();

		// Check if our token bearer scheme is being used by the client
		if (ChallengeScheme.HTTP_OAUTH_BEARER.equals(cr.getScheme()))
		{
			// If there's no token return result_missing
			final String bearer = cr.getRawValue();
			if (bearer == null || bearer.isEmpty())
				return RESULT_MISSING;

			// Otherwise check to see if we have the given token in our sessions
			// hash
			if (Brapi.getSessions().isValid(bearer))
				return RESULT_VALID;

			// Otherwise we have an invalid token
			return RESULT_INVALID;
		}

		// The scheme being used by the client is not supported
		return RESULT_UNSUPPORTED;
	}
}