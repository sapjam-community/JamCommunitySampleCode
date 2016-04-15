<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/mobile/nav"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/mobile/product"%>
<%@ taglib prefix="ex" tagdir="/WEB-INF/tags/addons/excelsiorjsaddon/mobile/excelsior"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<input type="hidden" id="community.network" value="${network}"></input>
<input type="hidden" id="community.server" value="${server}"></input>
<input type="hidden" id="ex-list-prodCodes" value="${prodCodes}"></input>
<input type="hidden" id="hybris_contextPath" value="${pageContext.request.contextPath}"></input>
<script type="text/javascript">
window.excelsiorListWidget = [];
</script>
<script type="text/javascript" src="${pageContext.request.contextPath.substring(0, pageContext.request.contextPath.indexOf('/', 1))}/_ui/addons/excelsiorjsaddon/common/js/excelsiorjsaddon.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath.substring(0, pageContext.request.contextPath.indexOf('/', 1))}/_ui/addons/excelsiorjsaddon/mobile/common/js/excelsiorjs-mobile.js"></script>

<c:choose>
  <c:when test="${pageContext.request.requestURI.indexOf('search') > 0}">
    <div data-role="tabs" id="ex-searchTabs">
      <div data-role="navbar">
         <ul><li><a href="#" data-ajax="false" class="ex-navbar" exindex="1"><spring:theme code="label.tab.products.mobile"/></a></li>
          <li><a href="#" data-ajax="false" class="ex-navbar" exindex="2"><spring:theme code="label.tab.community"/></a></li>
         </ul>
      </div>
      <div id="hybrisProduct" class="ui-body-d ui-content ex-content-1" >
        <c:if test="${not empty searchPageData.results}">
          <div class="sortingBar item_container_holder">
            <nav:searchTermAndSortingBar pageData="${searchPageData}" top="true" showSearchTerm="false"/>
          </div>
        </c:if>
        <nav:pagination searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.url}"/>
        <div class="productResultsList">
          <c:if test="${not empty searchPageData.results}">
            <ul data-role="listview" data-inset="true" data-theme="e" data-content-theme="e" class="mainNavigation">
              <c:forEach items="${searchPageData.results}" var="product" varStatus="status">
                <ex:productListerItem product="${product}"/>
              </c:forEach>
            </ul>
          </c:if>
        </div>
        <nav:pagination searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.url}"/>
      </div>
      <div id="communitySearch" class="ui-content ex-content-2">
        <div id="ex-searchResult"></div>
      </div>
    </div>
  </c:when>
  <c:otherwise>
    <c:if test="${not empty searchPageData.results}">
      <div class="sortingBar item_container_holder">
        <nav:searchTermAndSortingBar pageData="${searchPageData}" top="true" showSearchTerm="false"/>
      </div>
    </c:if>
    <nav:pagination searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.url}"/>
    <div class="productResultsList">
      <c:if test="${not empty searchPageData.results}">
        <ul data-role="listview" data-inset="true" data-theme="e" data-content-theme="e" class="mainNavigation">
          <c:forEach items="${searchPageData.results}" var="product" varStatus="status">
            <ex:productListerItem product="${product}"/>
          </c:forEach>
        </ul>
      </c:if>
    </div>
    <nav:pagination searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.url}"/>
  </c:otherwise>
</c:choose>
