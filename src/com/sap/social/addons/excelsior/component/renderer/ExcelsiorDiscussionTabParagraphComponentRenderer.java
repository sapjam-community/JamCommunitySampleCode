/**
 *
 */
package com.sap.social.addons.excelsior.component.renderer;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;

import com.sap.social.addons.excelsior.model.ExcelsiorDiscussionTabParagraphComponentModel;


/**
 * @author i319715
 *
 */
public class ExcelsiorDiscussionTabParagraphComponentRenderer<C extends ExcelsiorDiscussionTabParagraphComponentModel> extends
		ExcelsiorComponentBaseRender<C>
{
	private static final Logger LOG = Logger.getLogger(ExcelsiorDiscussionTabParagraphComponentRenderer.class);

	@Override
	protected Map<String, Object> getVariablesToExpose(final PageContext pageContext, final C component)
	{
		final Map<String, Object> params = new HashMap<String, Object>();

		return params;
	}
}
