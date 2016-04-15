/**
 *
 */
package com.sap.social.addons.excelsior.customLib;

import de.hybris.platform.util.Config;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;

import com.sap.social.addons.excelsior.constants.ExcelsiorjsaddonConstants;


/**
 * @author i319715
 *
 */
public class ExcelsiorHelper
{
	private static final Logger LOG = Logger.getLogger(ExcelsiorHelper.class);

	public static String getSiteByResourcePath(final String inputsiteResourcePath)
	{
		final String[] results = Pattern.compile("site-").split(inputsiteResourcePath);
		if (results.length > 0)
		{
			return results[(results.length - 1)];
		}
		return "";
	}

	public static String getCommunityNetworkBySite(final String site)
	{
		final String network = Config.getParameter(site + "." + ExcelsiorjsaddonConstants.EXCELSIOR_COMMUNITY_NETWORK);
		LOG.info("getCommunityNetworkBySite... site: " + site + " network : " + network);
		return network;
	}

	public static String getCommunityUrlBySite(final String site)
	{
		final String network = getCommunityNetworkBySite(site);
		final String url = Config.getParameter(ExcelsiorjsaddonConstants.EXCELSIOR_COMMUNITY + "." + network + "."
				+ ExcelsiorjsaddonConstants.EXCELSIOR_URL);
		LOG.info("getCommunityUrlBySite ... site: " + site + " url:" + url);
		return url;
	}

	public static String getCommunityNetworkByResourcePath(final String inputsiteResourcePath)
	{
		final String siteUid = getSiteByResourcePath(inputsiteResourcePath);
		final String network = getCommunityNetworkBySite(siteUid);
		LOG.info("getCommunityNetwork...inputsiteResourcePath : " + inputsiteResourcePath + " network: " + network);
		return network;
	}

	public static String getCommunityUrlByResourcePath(final String inputsiteResourcePath)
	{
		final String siteUid = getSiteByResourcePath(inputsiteResourcePath);
		final String url = getCommunityUrlBySite(siteUid);
		LOG.info("getCommunityUrlByResourcePath... inputsiteResourcePath : " + inputsiteResourcePath + " url : " + url);
		return url;
	}

	public static HttpURLConnection getURLConnection(final URL url) throws NoSuchAlgorithmException, KeyManagementException,
			IOException
	{
		HttpURLConnection httpCon;

		if (url.toString().startsWith("https"))
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

		return httpCon;
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
