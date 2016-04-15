/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2015 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 *
 *
 */
package de.hybris.platform.yacceleratorfacades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.order.data.ConsignmentData;
import de.hybris.platform.commercefacades.order.data.ConsignmentEntryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.enums.CustomerType;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.ordersplitting.model.ConsignmentProcessModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.util.Config;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Properties;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Required;

import sun.misc.BASE64Encoder;


/**
 * Velocity context for a Ready For Pickup notification email.
 */
public class DeliverySentEmailContext extends AbstractEmailContext<ConsignmentProcessModel>
{
	private Converter<ConsignmentModel, ConsignmentData> consignmentConverter;
	private ConsignmentData consignmentData;
	private String orderCode;
	private String orderGuid;
	private boolean guest;

	@Override
	public void init(final ConsignmentProcessModel consignmentProcessModel, final EmailPageModel emailPageModel)
	{
		super.init(consignmentProcessModel, emailPageModel);
		orderCode = consignmentProcessModel.getConsignment().getOrder().getCode();
		orderGuid = consignmentProcessModel.getConsignment().getOrder().getGuid();
		consignmentData = getConsignmentConverter().convert(consignmentProcessModel.getConsignment());
		final String siteName = this.getSite(consignmentProcessModel).getUid();
		System.out.println("******** siteName : " + siteName);
		// fetch the configuration which in the project.properties in excelsiorjsaddon folder
		final String network = getCommunityNetworkBySite(siteName);
		final String server = getCommunityUrlBySite(siteName);
		// fetch community token
		final String token = getCommunityToken(server, network);
		// fetch current user id which should be already mapped at community side
		final String userId = consignmentProcessModel.getConsignment().getOrder().getUser().getUid();
		// fetch and set the community url for product
		for (final ConsignmentEntryData entryData : consignmentData.getEntries())
		{
			final ProductData pd = entryData.getOrderEntry().getProduct();
			String url = getCommunityUrl(server, network, pd);
			if (!"".equals(token))
			{
				url += "&user_id=" + encodeUid(userId) + "&access_token=" + token;
			}
			pd.setCommunityUrl(url);
		}
		guest = CustomerType.GUEST.equals(getCustomer(consignmentProcessModel).getType());
	}

	@Override
	protected BaseSiteModel getSite(final ConsignmentProcessModel consignmentProcessModel)
	{
		return consignmentProcessModel.getConsignment().getOrder().getSite();
	}

	@Override
	protected CustomerModel getCustomer(final ConsignmentProcessModel consignmentProcessModel)
	{
		return (CustomerModel) consignmentProcessModel.getConsignment().getOrder().getUser();
	}

	protected Converter<ConsignmentModel, ConsignmentData> getConsignmentConverter()
	{
		return consignmentConverter;
	}

	@Required
	public void setConsignmentConverter(final Converter<ConsignmentModel, ConsignmentData> consignmentConverter)
	{
		this.consignmentConverter = consignmentConverter;
	}

	public ConsignmentData getConsignment()
	{
		return consignmentData;
	}

	public String getOrderCode()
	{
		return orderCode;
	}

	public String getOrderGuid()
	{
		return orderGuid;
	}

	public boolean isGuest()
	{
		return guest;
	}

	@Override
	protected LanguageModel getEmailLanguage(final ConsignmentProcessModel consignmentProcessModel)
	{
		if (consignmentProcessModel.getConsignment().getOrder() instanceof OrderModel)
		{
			return ((OrderModel) consignmentProcessModel.getConsignment().getOrder()).getLanguage();
		}

		return null;
	}

  private String encodeUid(final String userId)
  {
    try 
    {
      BASE64Encoder encoder = new sun.misc.BASE64Encoder();
      return URLEncoder.encode(encoder.encode(userId.getBytes("utf-8")), "utf-8");
    } 
    catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

	/** get community token. */
	private String getCommunityToken(final String server, final String network)
	{
		final String tokenUser = Config.getParameter("community." + network + ".token.username");
		final String tokenPassword = Config.getParameter("community." + network + ".token.password");
		final String applicationId = Config.getParameter("community." + network + ".application.id");
		final String secretId = Config.getParameter("community." + network + ".secret.id");

		String token = "";
		final String dest = server + "/oauth/token";
		final String urlParameters = "grant_type=password&client_id=" + applicationId + "&client_secret=" + secretId + "&username="
				+ tokenUser + "&password=" + tokenPassword;

		final HttpURLConnection httpCon = establishHttpUrlConnection(dest);
		try
		{
			final String result = getResponseResult(httpCon, "POST", urlParameters);
			System.out.println(result);

			final ObjectMapper mapper = new ObjectMapper();
			final JsonNode rootNode = mapper.readTree(result);
			final JsonNode tokenNode = rootNode.path("access_token");
			token = tokenNode.getTextValue();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}

		return token;
	}

	public String getCommunityNetworkBySite(final String site)
	{
		final String network = Config.getParameter(site + ".community.network");
		System.out.println("getCommunityNetworkBySite... site: " + site + " network : " + network);
		return network;
	}

	public String getCommunityUrlBySite(final String site)
	{
		final String network = getCommunityNetworkBySite(site);
		final String url = Config.getParameter("community." + network + ".url");
		System.out.println("getCommunityUrlBySite ... site: " + site + " url:" + url);
		return url;
	}

	/** get community url. */
	private String getCommunityUrl(final String server, final String network, final ProductData productData)
	{
		final String dest = server + "/api/v1/OData/Networks('" + network + "')/FilterProducts?codes=" + productData.getCode()
				+ "&$expand=Topic&$format=json";
		final String pHash = generateProductCodeHash(productData.getCode());
		final HttpURLConnection httpCon = establishHttpUrlConnection(dest);

		try
		{
			final String result = getResponseResult(httpCon, "GET", null);
			System.out.println(result);

			final ObjectMapper mapper = new ObjectMapper();
			final JsonNode rootNode = mapper.readTree(result);
			final JsonNode dNode = rootNode.path("d");
			final JsonNode resultsNode = dNode.path("results");
			final Iterator<JsonNode> results = resultsNode.getElements();
			while (results.hasNext())
			{
				final JsonNode resultNode = results.next();
				final JsonNode topicNode = resultNode.path("Topic");
				final JsonNode idNode = topicNode.path("Id");
				final String id = idNode.getTextValue();
				final String productName = productData.getName().replaceAll(" |/", "-");

				return server + "/topic/" + id + "/" + network + "/" + productName + "/new?type=review&purchased=true&phash=" + pHash;
			}
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}

		return server + "/n/" + network + "/home";
	}

	private String getResponseResult(final HttpURLConnection httpCon, final String requestMethod, final String urlParams)
			throws IOException
	{
		httpCon.setDoOutput(true);
		httpCon.setRequestMethod(requestMethod);

		if (urlParams != null && !"".equals(urlParams))
		{
			final DataOutputStream wr = new DataOutputStream(httpCon.getOutputStream());
			wr.writeBytes(urlParams);
			wr.flush();
			wr.close();
		}

		final int responseCode = httpCon.getResponseCode();
		System.out.println("*****responseCode : " + responseCode);
		if (responseCode == 200)
		{
			final BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
			String inputLine;
			final StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null)
			{
				response.append(inputLine);
			}
			in.close();
			return response.toString();
		}
		else
		{
			return "";
		}
	}

	private HttpURLConnection establishHttpUrlConnection(final String dest)
	{

		System.out.println("**** dest :" + dest);
		final String httphost = Config.getParameter("http.host");
		final String httpport = Config.getParameter("http.port");
		final String httpshost = Config.getParameter("https.host");
		final String httpsport = Config.getParameter("https.port");

		final Properties systemProperties = System.getProperties();
		if (httphost != null && httpport != null)
		{
			systemProperties.setProperty("http.proxyHost", httphost);
			systemProperties.setProperty("http.proxyPort", httpport);
			System.out.println("using http proxy..." + httphost);
		}
		if (httpshost != null && httpsport != null)
		{
			systemProperties.setProperty("https.proxyHost", httpshost);
			systemProperties.setProperty("https.proxyPort", httpsport);
			System.out.println("using https proxy..." + httpshost);
		}

		URL url = null;
		try
		{
			url = new URL(dest);
		}
		catch (final MalformedURLException e)
		{
			// YTODO Auto-generated catch block
			System.out.println("******** ERROR: " + e.getMessage());
		}

		HttpURLConnection httpCon = null;
		try
		{
			if (dest.startsWith("https"))
			{
				//https request
				final SSLContext ctx = SSLContext.getInstance("TLS");
				ctx.init(new KeyManager[0], new TrustManager[]
				{ new DefaultTrustManager() }, new SecureRandom());
				SSLContext.setDefault(ctx);

				httpCon = (HttpsURLConnection) url.openConnection();
				((HttpsURLConnection) httpCon).setHostnameVerifier(new HostnameVerifier()
				{
					@Override
					public boolean verify(final String arg0, final SSLSession arg1)
					{
						return true;
					}
				});
			}
			else
			//http request
			{
				httpCon = (HttpURLConnection) url.openConnection();
			}
		}
		catch (final NoSuchAlgorithmException e)
		{
			System.out.println("******** ERROR : " + e.getMessage());
		}
		catch (final KeyManagementException e)
		{
			System.out.println("******** ERROR : " + e.getMessage());
		}
		catch (final IOException e)
		{
			System.out.println("******** ERROR : " + e.getMessage());
		}

		return httpCon;
	}

	/**
	 * Code to generated product code hash which will append to community url to prevent url hack
	 *
	 * @param productCode
	 *           product code
	 * @return product code hash
	 */
	private String generateProductCodeHash(final String productCode)
	{
		try
		{
			final String secret = "p-c-s-e-c-r-e-t"; // community should have the same secret as this to decode
			final String message = productCode;

			final Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			final SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
			sha256_HMAC.init(secret_key);
			final BASE64Encoder encoder = new sun.misc.BASE64Encoder();

			final String hash = encoder.encode(sha256_HMAC.doFinal(message.getBytes()));
			System.out.println("******** Generated Hash is " + hash);
			return URLEncoder.encode(hash, "utf-8");
		}
		catch (final Exception e)
		{
			System.out.println("******** Error to generate product code token, " + e.getMessage());
		}
		return "";
	}

	private static class DefaultTrustManager implements X509TrustManager
	{

		@Override
		public void checkClientTrusted(final X509Certificate[] arg0, final String arg1) throws CertificateException
		{
			//
		}

		@Override
		public void checkServerTrusted(final X509Certificate[] arg0, final String arg1) throws CertificateException
		{
			//
		}

		@Override
		public X509Certificate[] getAcceptedIssuers()
		{
			return null;
		}
	}
}
