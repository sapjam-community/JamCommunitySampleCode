/**
 *
 */
package com.sap.social.addons.excelsior.token.bean;

import de.hybris.platform.util.Config;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.sap.social.addons.excelsior.constants.ExcelsiorjsaddonConstants;
import com.sap.social.addons.excelsior.customLib.ExcelsiorHelper;


/**
 * @author i319715 It is used to cache token fetched from community
 */
public class ExcelsiorTokenBean
{
	private static final Logger LOG = Logger.getLogger(ExcelsiorTokenBean.class);
	private final Map<String, String> excelsiorToken = new HashMap<String, String>();//<network, token>

	//private Map<String, String> expireLogger;  <token, timeStamp>

	public String getExcelsiorToken(String server, final String network)
	{
		if (network == null)
		{
			return null;
		}
		server = server == null ? Config.getParameter(ExcelsiorjsaddonConstants.EXCELSIOR_COMMUNITY + "." + network + "."
				+ ExcelsiorjsaddonConstants.EXCELSIOR_URL) : server;
		if (server == null)
		{
			return null;
		}
		String token = excelsiorToken.get(network);
		if (token == null || isExpired(token))
		{
			final String tokenUser = Config.getParameter(ExcelsiorjsaddonConstants.EXCELSIOR_COMMUNITY + "." + network + "."
					+ ExcelsiorjsaddonConstants.EXCELSIOR_TOKEN_USERNAME);
			final String tokenPassword = Config.getParameter(ExcelsiorjsaddonConstants.EXCELSIOR_COMMUNITY + "." + network + "."
					+ ExcelsiorjsaddonConstants.EXCELSIOR_TOKEN_PASSWORD);
			final String application_id = Config.getParameter(ExcelsiorjsaddonConstants.EXCELSIOR_COMMUNITY + "." + network + "."
					+ ExcelsiorjsaddonConstants.EXCELSIOR_APPLICATION_ID);
			final String secret_id = Config.getParameter(ExcelsiorjsaddonConstants.EXCELSIOR_COMMUNITY + "." + network + "."
					+ ExcelsiorjsaddonConstants.EXCELSIOR_SECRET_ID);
			try
			{
				token = fetchToken(server, tokenUser, tokenPassword, application_id, secret_id);
				excelsiorToken.put(network, token);
			}
			catch (KeyManagementException | NoSuchAlgorithmException | IOException e)
			{
				// YTODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return token;
	}

	/**
	 * @return
	 */
	private boolean isExpired(final String token)
	{
		// YTODO Auto-generated method stub
		return false;
	}

	/**
	 * @param server
	 * @param tokenUser
	 * @param tokenPassword
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws IOException
	 */
	private String fetchToken(final String server, final String tokenUser, final String tokenPassword, final String applicationId,
			final String secretId) throws NoSuchAlgorithmException, KeyManagementException, IOException
	{
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


		final String dest = server + "/oauth/token";
		final URL url = new URL(dest);
		final HttpURLConnection httpCon = ExcelsiorHelper.getURLConnection(url);
		httpCon.setDoOutput(true);
		httpCon.setRequestMethod("POST");
		final String urlParameters = "grant_type=password&client_id=" + applicationId + "&client_secret=" + secretId + "&username="
				+ tokenUser + "&password=" + tokenPassword;

		LOG.info("Request url : " + url.toString() + "\n parameters : " + urlParameters);
		final DataOutputStream wr = new DataOutputStream(httpCon.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		final int responseCode = httpCon.getResponseCode();

		final BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
		String inputLine;
		final StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null)
		{
			response.append(inputLine);
		}
		in.close();
		final String result = response.toString();
		LOG.info(result);
		if (200 == responseCode)
		{
			//get token successfully
			try
			{
				final JSONObject obj = new JSONObject(result);
				final Object t = obj.get("access_token");
				LOG.info("token : " + t.toString());
				return t.toString();
			}
			catch (final Exception e)
			{
				e.printStackTrace();
				return "";
			}
		}
		return "";
	}

}
