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



<div class="tabhead" id="tab_qa"><a href><spring:theme code="label.tab.qa"/></a><span class="glyphicon"></span></div>
<div class="tabbody" id="tabBody_qas">
	<div id="ex-questions">
	</div>
  <script type="text/javascript">
    var option = {
        id: 'ex-questions',
        name: 'QuestionTab'
    };

    if (typeof renderWidget != "undefined") {
        renderWidget(option);
    } else if (typeof excelsiorWidget != "undefined") {
        excelsiorWidget.push(option);
    }
  </script>
</div>
