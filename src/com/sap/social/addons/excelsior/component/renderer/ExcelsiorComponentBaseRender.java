/**
 *
 */
package com.sap.social.addons.excelsior.component.renderer;

import de.hybris.platform.addonsupport.renderer.impl.DefaultAddOnCMSComponentRenderer;
import de.hybris.platform.cms2.model.contents.components.AbstractCMSComponentModel;
import de.hybris.platform.cms2.servicelayer.services.CMSSiteService;
import de.hybris.platform.util.Config;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

import com.sap.social.addons.excelsior.constants.ExcelsiorjsaddonConstants;
import com.sap.social.addons.excelsior.customLib.ExcelsiorHelper;
import com.sap.social.addons.excelsior.entity.CommunityInfo;
import com.sap.social.addons.excelsior.model.ExcelsiorAbstractParagraphComponentModel;


/**
 * @author i319715
 *
 */
public class ExcelsiorComponentBaseRender<C extends AbstractCMSComponentModel> extends DefaultAddOnCMSComponentRenderer<C>
{
	private static final Logger LOG = Logger.getLogger(ExcelsiorComponentBaseRender.class);
	private CMSSiteService cmsSiteService;

	public void setCmsSiteService(final CMSSiteService cmsSiteService)
	{
		this.cmsSiteService = cmsSiteService;
	}

	public CMSSiteService getCmsSiteService()
	{
		return cmsSiteService;
	}

	@Override
	protected String getView(final C component)
	{
		final String responsive = Config.getParameter(ExcelsiorjsaddonConstants.RESPONSIVE);
		if (ExcelsiorjsaddonConstants.ON.equals(responsive))
		{
			return "/WEB-INF/views/addons/" + getAddonUiExtensionName(component) + "/" + "responsive" + "/"
					+ getCmsComponentFolder() + "/" + getViewResourceName(component) + ".jsp";
		}
		else
		{
			return super.getView(component);
		}
	}

	protected boolean isEmpty(final String input)
	{
		if (input == null || input.trim().equals(""))
		{
			return true;
		}
		return false;
	}

	protected CommunityInfo getCommunityInfo(final ExcelsiorAbstractParagraphComponentModel component)
	{
		final String site = cmsSiteService.getCurrentSite().getUid();
		final CommunityInfo communityInfo = new CommunityInfo();
		String server = component.getServer();
		String network = component.getNetwork();
		if (isEmpty(server) || isEmpty(network))
		{
			LOG.info("No specific config for component, read from project.properties file.");
			network = ExcelsiorHelper.getCommunityNetworkBySite(site);
			server = ExcelsiorHelper.getCommunityUrlBySite(site);
			if (isEmpty(network) || isEmpty(server))
			{
				LOG.error("Error in network or server configuration!");
			}
		}
		else
		{
			LOG.info("Specific config for component found." + "\n network :" + network + "\n server :" + server);
		}
		communityInfo.setNetwork(network);
		URL url;
		try
		{
			url = new URL(server);
			communityInfo.setServer(url);
		}
		catch (final MalformedURLException e)
		{
			LOG.error("Malformation of url!");
		}

		return communityInfo;
	}

	public String getSiteUid()
	{
		return cmsSiteService.getCurrentSite().getUid();
	}
}
