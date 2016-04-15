/**
 *
 */
package com.sap.social.addons.excelsior.entity;

import java.net.URL;


/**
 * @author i319715
 *
 */
public class CommunityInfo
{
	private String network;
	private URL server;

	public String getNetwork()
	{
		return network;
	}

	public void setNetwork(final String network)
	{
		this.network = network;
	}

	public URL getServer()
	{
		return server;
	}

	public void setServer(final URL server)
	{
		this.server = server;
	}

}
