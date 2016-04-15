/**
 *
 */
package com.sap.social.addons.excelsior.versionNotifier;

import de.hybris.platform.util.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.sap.social.addons.excelsior.constants.ExcelsiorjsaddonConstants;
import com.sap.social.addons.excelsior.customLib.ExcelsiorHelper;
import com.sap.social.addons.excelsior.token.bean.ExcelsiorTokenBean;


/**
 * @author i319715
 *
 */
public class VersionNotifier
{
	private static final Logger LOG = Logger.getLogger(VersionNotifier.class);
	private ExcelsiorTokenBean excelsiorTokenBean;

	public void setExcelsiorTokenBean(final ExcelsiorTokenBean excelsiorTokenBean)
	{
		this.excelsiorTokenBean = excelsiorTokenBean;
	}

	public void init()
	{

		LOG.info("entering init method ... ");
		final Map<String, String> params = Config.getAllParameters();
		for (final String key : params.keySet())
		{
			if (key.endsWith(ExcelsiorjsaddonConstants.EXCELSIOR_COMMUNITY_NETWORK))
			{
				final String network = params.get(key);
				final String urlKey = ExcelsiorjsaddonConstants.EXCELSIOR_COMMUNITY + "." + network + "."
						+ ExcelsiorjsaddonConstants.EXCELSIOR_URL;
				final String server = params.get(urlKey);
				final String token = excelsiorTokenBean.getExcelsiorToken(server, network);
				if (token != null)
				{
					sendVersionNotification(server, network, token);
				}
				else
				{
					LOG.info("error trying to get token, can not proceed...");
				}
				LOG.info("send version info to community ... " + server);
			}
		}
	}

	public void destroy()
	{
		//things that need to be done
	}

	private void sendVersionNotification(final String server, final String network, final String token)
	{
		final String dest = server + "/api/v1/OData/Networks('" + network + "')/UpdateHybrisAddonVersion()?access_token=" + token
				+ "&$format=json";
		LOG.info("sending version notification to : " + dest);
		try
		{
			final URL url = new URL(dest);
			final HttpURLConnection httpCon = ExcelsiorHelper.getURLConnection(url);
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			final JSONObject jo = new JSONObject();
			jo.put("Version", ExcelsiorjsaddonConstants.VERSION_NUM);
			final JSONObject parent = new JSONObject();
			parent.put("d", jo);
			final OutputStream wr = httpCon.getOutputStream();
			wr.write(parent.toString().getBytes("UTF-8"));
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
			httpCon.disconnect();
			LOG.info("notification responsecode : " + responseCode + "\n response : " + response);
		}
		catch (final KeyManagementException | NoSuchAlgorithmException | IOException e)
		{
			// YTODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
