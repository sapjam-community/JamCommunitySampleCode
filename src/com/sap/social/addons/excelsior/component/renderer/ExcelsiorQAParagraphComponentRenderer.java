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
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;

import com.sap.social.addons.excelsior.entity.CommunityInfo;
import com.sap.social.addons.excelsior.model.ExcelsiorQAParagraphComponentModel;


/**
 * @author rmcotton
 *
 */
public class ExcelsiorQAParagraphComponentRenderer<C extends ExcelsiorQAParagraphComponentModel> extends
		ExcelsiorComponentBaseRender<C>
{
	private static final Logger LOG = Logger.getLogger(ExcelsiorQAParagraphComponentRenderer.class);
	private UserService userService;

	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	@Override
	protected Map<String, Object> getVariablesToExpose(final PageContext pageContext, final C component)
	{
		final Map<String, Object> params = new HashMap<String, Object>();
		final UserModel user = userService.getCurrentUser();

		final CommunityInfo communityInfo = getCommunityInfo(component);
		final ProductData productData = (ProductData) pageContext.getRequest().getAttribute("product");
		final String productCode = productData.getCode();

		final HashMap productInfo = new HashMap();
		productInfo.put("server", communityInfo.getServer().toString());
		productInfo.put("network", communityInfo.getNetwork());
		productInfo.put("productCode", productCode);
		productInfo.put("userId", user.getUid());

		params.put("productInfo", productInfo);
		return params;
	}
}