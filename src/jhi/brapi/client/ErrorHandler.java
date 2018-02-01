package jhi.brapi.client;

import java.io.*;
import java.lang.annotation.*;

import jhi.brapi.api.*;

import okhttp3.*;

import retrofit2.*;
import retrofit2.Response;

public class ErrorHandler
{
	public static BrapiErrorResource handle(RetrofitServiceGenerator generator, Response<?> response)
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
}
