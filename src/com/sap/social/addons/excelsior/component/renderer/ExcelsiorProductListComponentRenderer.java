/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2013 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 *
 *
 */
package com.sap.social.addons.excelsior.component.renderer;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;

import com.sap.social.addons.excelsior.customLib.ExcelsiorHelper;
import com.sap.social.addons.excelsior.model.ExcelsiorProductListComponentModel;


/**
 *
 */
public class ExcelsiorProductListComponentRenderer<C extends ExcelsiorProductListComponentModel> extends
		ExcelsiorComponentBaseRender<C>
{
	private static final Logger LOG = Logger.getLogger(ExcelsiorProductListComponentRenderer.class);

	@Override
	protected Map<String, Object> getVariablesToExpose(final PageContext pageContext, final C component)
	{
		final Map<String, Object> params = new HashMap<String, Object>();


		final String site = getCmsSiteService().getCurrentSite().getUid();
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


		final SearchPageData searchPageData = (SearchPageData) pageContext.getRequest().getAttribute("searchPageData");
		final Boolean isShowPageAllowed = (Boolean) pageContext.getRequest().getAttribute("isShowPageAllowed");
		final List<ProductData> products = searchPageData.getResults();
		String prodCodes = "";
		for (final ProductData pd : products)
		{
			prodCodes += (pd.getCode() + ";");
		}

		params.put("prodCodes", prodCodes);
		params.put("searchPageData", searchPageData);
		params.put("isShowPageAllowed", isShowPageAllowed);
		params.put("actions", component.getActions());
		params.put("server", server);
		params.put("network", network);

		return params;
	}

}