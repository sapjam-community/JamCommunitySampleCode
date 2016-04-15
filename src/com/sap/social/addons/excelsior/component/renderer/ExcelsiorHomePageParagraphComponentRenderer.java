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

import de.hybris.platform.util.Config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;

import com.sap.social.addons.excelsior.constants.ExcelsiorjsaddonConstants;
import com.sap.social.addons.excelsior.entity.CommunityInfo;
import com.sap.social.addons.excelsior.model.ExcelsiorHomePageParagraphComponentModel;


/**
 * Homepage addon.
 */
public class ExcelsiorHomePageParagraphComponentRenderer<C extends ExcelsiorHomePageParagraphComponentModel> extends
		ExcelsiorComponentBaseRender<C>
{
	private static final Logger LOG = Logger.getLogger(ExcelsiorHomePageParagraphComponentRenderer.class);

	@Override
	protected Map<String, Object> getVariablesToExpose(final PageContext pageContext, final C component)
	{

		final Map<String, Object> params = new HashMap<String, Object>();
		final CommunityInfo communityInfo = getCommunityInfo(component);

		// parse product code configured by admin
		String prodCode = component.getProdCode();
		if (isEmpty(prodCode))
		{
			LOG.info("No specific configuration found, read product code from project.properties");
			prodCode = Config.getParameter(getSiteUid() + "." + ExcelsiorjsaddonConstants.HYBRIS_HOMEPAGE_COMPONENT_PRODUCTCODE);
		}

		final HashMap productInfo = new HashMap();
		productInfo.put("server", communityInfo.getServer().toString());
		productInfo.put("network", communityInfo.getNetwork());
		productInfo.put("productCode", prodCode);

		params.put("productInfo", productInfo);
		return params;
	}
}