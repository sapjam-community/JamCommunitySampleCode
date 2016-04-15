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
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;

import com.sap.social.addons.excelsior.model.ExcelsiorQATabParagraphComponentModel;


public class ExcelsiorQATabParagraphComponentRenderer<C extends ExcelsiorQATabParagraphComponentModel> extends
		ExcelsiorComponentBaseRender<C>
{
	private static final Logger LOG = Logger.getLogger(ExcelsiorQATabParagraphComponentRenderer.class);
	

	@Override
	protected Map<String, Object> getVariablesToExpose(final PageContext pageContext, final C component)
	{
		final Map<String, Object> params = new HashMap<String, Object>();

		return params;
	}
}