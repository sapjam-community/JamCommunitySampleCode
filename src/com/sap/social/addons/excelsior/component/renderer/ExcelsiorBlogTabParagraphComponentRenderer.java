/**
 *
 */
package com.sap.social.addons.excelsior.component.renderer;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;

import com.sap.social.addons.excelsior.model.ExcelsiorBlogTabParagraphComponentModel;


/**
 * @author i319715
 *
 */
public class ExcelsiorBlogTabParagraphComponentRenderer<C extends ExcelsiorBlogTabParagraphComponentModel> extends
		ExcelsiorComponentBaseRender<C>

{
	private static final Logger LOG = Logger.getLogger(ExcelsiorBlogTabParagraphComponentRenderer.class);

	@Override
	protected Map<String, Object> getVariablesToExpose(final PageContext pageContext, final C component)
	{
		final Map<String, Object> params = new HashMap<String, Object>();

		return params;
	}
}
