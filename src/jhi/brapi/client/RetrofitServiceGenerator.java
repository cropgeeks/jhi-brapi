package jhi.brapi.client;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;
import java.util.concurrent.*;

import okhttp3.*;

import retrofit2.*;
import retrofit2.converter.jackson.*;

public class RetrofitServiceGenerator
{
	private final String baseURL;
	private final String certificate;

	private OkHttpClient httpClient;

	private Retrofit retrofit;

	public RetrofitServiceGenerator(String baseURL, String certificate)
	{
		this.baseURL = baseURL;
		this.certificate = certificate;
	}

	public RetrofitService generate(String authToken)
	{
		Interceptor inter = buildInterceptor(authToken);

		// Tweak to make the timeout on Retrofit connections last longer
		httpClient = new OkHttpClient.Builder()
				.readTimeout(60, TimeUnit.SECONDS)
				.connectTimeout(60, TimeUnit.SECONDS)
				.addNetworkInterceptor(inter)
				.build();

		// If the resource has an associated certificate, ensure it is in the
		// trust manager and keystore
		try
		{
			httpClient = initCertificate(httpClient, certificate);
		}
		catch (Exception e) { e.printStackTrace(); }

		return buildService(baseURL, httpClient);
	}

	private Interceptor buildInterceptor(String authToken)
	{
		String bearer = "Bearer %s";

		Interceptor inter = chain ->
		{
			Request original = chain.request();

			// If we already have an authorization token in the header, or we
			// don't have a valid token to add, return the original request
			if (original.header("Authorization") != null || authToken == null || authToken.isEmpty())
				return chain.proceed(original);

			// Otherwise add the header and return the tweaked request
			Request next = original.newBuilder()
					.header("Authorization", String.format(bearer, authToken))
					.build();

			return chain.proceed(next);
		};

		return inter;
	}

	private OkHttpClient initCertificate(OkHttpClient client, String certificate)
			throws Exception
	{
		if (certificate == null || certificate.isEmpty())
			return client;

		// Deal with self signed certificates
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		InputStream caInput = new URL(certificate).openStream();
		Certificate ca;
		ca = cf.generateCertificate(caInput);

		String keyStoreType = KeyStore.getDefaultType();
		KeyStore keyStore = KeyStore.getInstance(keyStoreType);
		keyStore.load(null, null);
		keyStore.setCertificateEntry("ca", ca);

		String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
		tmf.init(keyStore);

		SSLContext sslContext = SSLContext.getInstance("SSL");
		sslContext.init(null, tmf.getTrustManagers(), null);

		client = client.newBuilder()
				.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager)tmf.getTrustManagers()[0])
				.hostnameVerifier((s, sslSession) -> true)
				.build();

		caInput.close();

		return client;
	}

	private RetrofitService buildService(String baseURL, OkHttpClient client)
	{
		retrofit = new Retrofit.Builder()
				.baseUrl(baseURL)
				.addConverterFactory(JacksonConverterFactory.create())
				.client(client)
				.build();

		return retrofit.create(RetrofitService.class);
	}

	public InputStream getInputStream(URI uri)
		throws Exception
	{
		Request request = new Request.Builder()
				.url(uri.toURL())
				.build();

		okhttp3.Response response = httpClient.newCall(request).execute();

		return response.body().byteStream();
	}

	Retrofit getRetrofit()
	{
		return retrofit;
	}
}