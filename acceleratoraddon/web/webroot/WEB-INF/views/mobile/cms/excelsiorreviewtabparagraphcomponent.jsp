<%@ page trimDirectiveWhitespaces="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="ex" tagdir="/WEB-INF/tags/addons/excelsioraddon/mobile/excelsior"%>

<div data-role="collapsible" data-theme="e" data-content-theme="c" data-collapsed="true" data-icon="arrow-u" id="review_expand">
	<h3 id="tab_review"><spring:theme code="label.tab.review"/></h3>
	<p id="tabBody_reviews">
	<div id="ex-reviews">
	</div>
  <script type="text/javascript">
    var option = {
        id: 'ex-reviews',
        name: 'ReviewTab'
    };

    if (typeof renderWidget != "undefined") {
        renderWidget(option);
    } else if (typeof excelsiorWidget != "undefined") {
        excelsiorWidget.push(option);
    }
  </script>
	</p>
</div>
