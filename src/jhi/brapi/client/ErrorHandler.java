package jhi.brapi.client;

import java.io.*;
import java.lang.annotation.*;
import java.util.*;
import java.util.stream.*;

import jhi.brapi.api.*;

import okhttp3.*;

import retrofit2.*;
import retrofit2.Response;

public class ErrorHandler
{
	private static BrapiErrorResource convertResponse(RetrofitServiceGenerator generator, Response<?> response)
	{
		Retrofit retrofit = generator.getRetrofit();

		Converter<ResponseBody, BrapiErrorResource> converter = retrofit.responseBodyConverter(BrapiErrorResource.class, new Annotation[0]);

		BrapiErrorResource error;

		try
		{
			error = converter.convert(response.errorBody());
		}
		catch (IOException e)
		{
			return new BrapiErrorResource();
		}

		return error;
	}

	public static String getMessage(RetrofitServiceGenerator generator, Response<?> response)
	{
		BrapiErrorResource errorResource = ErrorHandler.convertResponse(generator, response);
		List<Status> statuses = errorResource.getMetadata().getStatus();

		String errorMessage = statuses.stream().map(Status::toString).collect(Collectors.joining(", "));
		if (errorMessage.isEmpty())
			errorMessage += "No BrAPI status messages found, returning HTTP code and message instead: " + response.code() + " - " + response.message();

		return errorMessage;
	}
}
