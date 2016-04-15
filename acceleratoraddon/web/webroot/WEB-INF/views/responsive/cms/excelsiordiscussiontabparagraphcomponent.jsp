<%@ page trimDirectiveWhitespaces="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="ex" tagdir="/WEB-INF/tags/addons/excelsioraddon/responsive/excelsior"%>


<div class="tabhead" id="tab_discussion"><a href><spring:theme code="label.tab.discussion"/></a><span class="glyphicon"></span></div>
<div class="tabbody" id="tabBody_discussions">
	<div id="ex-discussions">
	</div>
  <script type="text/javascript">
    var option = {
        id: 'ex-discussions',
        name: 'DiscussionTab'
    };

    if (typeof renderWidget != "undefined") {
        renderWidget(option);
    } else if (typeof excelsiorWidget != "undefined") {
        excelsiorWidget.push(option);
    }
  </script>
</div>
