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

<div class="scroller" >
    <div id="tableHeader" class="title">
        <spring:theme code="label.explore.community"/>
    </div>
    <div class="excelsior-homepage">
        <div class="excelsior-spinner excelsior-loading"></div>
        <div class="excelsior-spinner excelsior-loading"></div>
    </div>
    <input type="hidden" id="addon_prod_code" value="${productInfo.productCode}"></input>
    <input type="hidden" id="community.network" value="${productInfo.network}"></input>
    <input type="hidden" id="community.server" value="${productInfo.server}"></input>
    <input type="hidden" id="hybris_contextPath" value="${pageContext.request.contextPath}"></input>
    <div id="homepage-widget-addon">
        <div id="homepage-widget-addon-question"></div>
        <div id="homepage-widget-addon-review"></div>
    </div>
    <script type="text/javascript">
    window.excelsiorWidget = [{
        id: 'homepage-widget-addon-question',
        name: 'HomePageQuestion'
    }, {
        id: 'homepage-widget-addon-review',
        name: 'HomePageReview'
    }];
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath.substring(0, pageContext.request.contextPath.indexOf('/', 1))}/_ui/addons/excelsiorjsaddon/common/js/excelsiorjsaddon.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath.substring(0, pageContext.request.contextPath.indexOf('/', 1))}/_ui/addons/excelsiorjsaddon/mobile/common/js/excelsiorjs-mobile.js"></script>
</div>
