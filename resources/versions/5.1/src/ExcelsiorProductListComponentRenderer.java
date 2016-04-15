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

import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;

import com.sap.social.addons.excelsior.model.ExcelsiorProductListComponentModel;


/**
 *
 */
public class ExcelsiorProductListComponentRenderer<C extends ExcelsiorProductListComponentModel> extends
		ExcelsiorComponentBaseRender<C>
{
	private static final Logger LOG = Logger.getLogger(ExcelsiorProductListComponentRenderer.class);
	private UserService userService;
	private ProductService productService;
	private CustomerFacade customerFacade;

	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	public void setCustomerFacade(final CustomerFacade customerFacade)
	{
		this.customerFacade = customerFacade;
	}

	public void setProductService(final ProductService productService)
	{
		this.productService = productService;
	}

	@Override
	protected Map<String, Object> getVariablesToExpose(final PageContext pageContext, final C component)
	{
		final Map<String, Object> params = new HashMap<String, Object>();

		final String server = component.getServer();
		final String network = component.getNetwork();
		final SearchPageData searchPageData = (SearchPageData) pageContext.getRequest().getAttribute("searchPageData");
		final Boolean isShowPageAllowed = (Boolean) pageContext.getRequest().getAttribute("isShowPageAllowed");
		final List<ProductData> products = searchPageData.getResults();
		String prodCodes = "";
		for(ProductData pd : products){
			prodCodes += (pd.getCode()+";");
		}
		
		params.put("prodCodes", prodCodes);
		params.put("searchPageData", searchPageData);
		params.put("isShowPageAllowed", isShowPageAllowed);
		//params.put("actions", component.getActions());
		params.put("server",server);
		params.put("network",network);

		return params;
	}

}