package com.sap.social.addons.excelsior.controllers;

import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.Config;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sap.social.addons.excelsior.constants.ExcelsiorjsaddonConstants;
import com.sap.social.addons.excelsior.token.bean.ExcelsiorTokenBean;



@Controller
public class ExcelsiorjsaddonController
{
	@Autowired
	private UserService userService;
	@Autowired
	private ExcelsiorTokenBean excelsiorTokenBean;

	private static final Logger LOG = Logger.getLogger(ExcelsiorjsaddonController.class);

	@RequestMapping(value = "/token", method = RequestMethod.GET)
	public String getToken(@RequestParam("server") final String server, @RequestParam("network") final String network,
			final Model model, final HttpServletResponse response)
	{
		String userId = userService.getCurrentUser().getUid();
		final String token = excelsiorTokenBean.getExcelsiorToken(server, network);
		if ("anonymous".equals(userId))
		{
			userId = "";
		}
		model.addAttribute("token", token);
		model.addAttribute("userid", userId);

		response.setHeader("cache-control", "no-cache");
		response.setHeader("pragma", "no-cache");
		response.setHeader("expires", "Thu, 01 Dec 1983 20:00:00 GMT");
		return "addon:/excelsiorjsaddon/fragments/token";
	}

}
