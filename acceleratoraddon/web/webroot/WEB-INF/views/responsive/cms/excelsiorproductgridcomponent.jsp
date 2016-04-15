<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ex" tagdir="/WEB-INF/tags/addons/excelsiorjsaddon/responsive/excelsior" %>


<input type="hidden" id="community.network" value="${network}"></input>
<input type="hidden" id="community.server" value="${server}"></input>
<input type="hidden" id="ex-list-prodCodes" value="${prodCodes}"></input>
<script type="text/javascript">
window.excelsiorListWidget = [];
</script>

<script type="text/javascript" src="${pageContext.request.contextPath.substring(0, pageContext.request.contextPath.indexOf('/', 1))}/_ui/addons/excelsiorjsaddon/common/js/excelsiorjsaddon.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath.substring(0, pageContext.request.contextPath.indexOf('/', 1))}/_ui/addons/excelsiorjsaddon/responsive/common/js/excelsior-responsive.js"></script>
<div class="col-md-9 col-lg-10">
	<nav:pagination top="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.url}" numberPagesShown="${numberPagesShown}"/>

	<ul class="product-listing product-grid">
		<c:forEach items="${searchPageData.results}" var="product" varStatus="status">
			<ex:productListerGridItem product="${product}" />
		</c:forEach>
	</ul>

	<div id="addToCartTitle" style="display:none">
		<div class="add-to-cart-header">
			<div class="headline">
				<span class="headline-text"><spring:theme code="basket.added.to.basket"/></span>
			</div>
		</div>
	</div>

	<nav:pagination top="false"  supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"  searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.url}"  numberPagesShown="${numberPagesShown}"/>
</div>