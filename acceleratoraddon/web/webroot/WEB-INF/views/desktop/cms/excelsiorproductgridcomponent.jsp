<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/desktop/storepickup" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ex" tagdir="/WEB-INF/tags/addons/excelsiorjsaddon/desktop/excelsior" %>


<input type="hidden" id="community.network" value="${network}"></input>
<input type="hidden" id="community.server" value="${server}"></input>
<input type="hidden" id="ex-list-prodCodes" value="${prodCodes}"></input>
<script type="text/javascript">
window.excelsiorListWidget = [];
</script>
<script type="text/javascript" src="${pageContext.request.contextPath.substring(0, pageContext.request.contextPath.indexOf('/', 1))}/_ui/addons/excelsiorjsaddon/common/js/excelsiorjsaddon.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath.substring(0, pageContext.request.contextPath.indexOf('/', 1))}/_ui/addons/excelsiorjsaddon/desktop/common/js/excelsiorjs-desktop.js"></script>
<c:if test="${not empty searchPageData.freeTextSearch}">
	<div class="results">
		<h1><spring:theme code="search.page.searchText" arguments="${searchPageData.freeTextSearch}"/></h1>
	</div>
</c:if>

<nav:searchSpellingSuggestion spellingSuggestion="${searchPageData.spellingSuggestion}" />
<div id="ex-searchTabs">
	<input type="hidden" id="hybris_contextPath" value="${pageContext.request.contextPath}"/>
	<div class="ex-tabHead hideHead"><spring:theme code="label.tab.products" arguments="${searchPageData.pagination.totalNumberOfResults}"/></div>
	<div class="ex-tabBody">
    <nav:pagination top="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="false" searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.url}    " numberPagesShown="${numberPagesShown}"/>
    
    <div class="productGrid">
    	<c:forEach items="${searchPageData.results}" var="product" varStatus="status">
    		<div class="span-6 ${(status.index+1)%3 == 0 ? ' last' : ''}${(status.index)%3 == 0 ? '     first clear' : ''}">
    			<ex:productListerGridItem product="${product}" />
    		</div>
    	</c:forEach>
    </div>
    <nav:pagination top="false"  supportShowPaged="${isShowPageAllowed}" supportShowAll="false"  searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.    url}"  numberPagesShown="${numberPagesShown}"/>
    <storepickup:pickupStorePopup />
	</div>
	<div class="ex-tabHead hideHead" tabindex="-1"><spring:theme code="label.tab.community"/><span id="ex-searchResult-count"/></div>
  <div class="ex-tabBody">
    <div id="ex-searchResult"></div>
  </div>
</div>
