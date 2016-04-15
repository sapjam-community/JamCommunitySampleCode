<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/mobile/nav"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/mobile/product"%>
<%@ taglib prefix="ex" tagdir="/WEB-INF/tags/addons/excelsiorjsaddon/mobile/excelsior"%>


<input type="hidden" id="community.network" value="${network}"></input>
<input type="hidden" id="community.server" value="${server}"></input>
<input type="hidden" id="ex-list-prodCodes" value="${prodCodes}"></input>
<script type="text/javascript">
window.excelsiorListWidget = [];
</script>
<script type="text/javascript" src="${pageContext.request.contextPath.substring(0, pageContext.request.contextPath.indexOf('/', 1))}/_ui/addons/excelsiorjsaddon/common/js/excelsiorjsaddon.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath.substring(0, pageContext.request.contextPath.indexOf('/', 1))}/_ui/addons/excelsiorjsaddon/mobile/common/js/excelsiorjs-mobile.js"></script>
<c:if test="${not empty searchPageData.results}">
	<div class="sortingBar item_container_holder">
		<nav:searchTermAndSortingBar pageData="${searchPageData}" top="true" showSearchTerm="false"/>
	</div>
</c:if>
<nav:pagination searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.url}"/>
<div class="span-24 productResultsGrid">
	<c:forEach items="${searchPageData.results}" var="product" varStatus="status">
		<c:choose>
			<c:when test="${status.first}">
				<div class="ui-grid-a">
					<div class='ui-block-a left'>
						<ex:productListerGridItem product="${product}" />
					</div>
					<c:if test="${status.last}">
				</div>
				</c:if>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${(status.count % 2) == 0}">
						<div class='ui-block-b right'>
							<ex:productListerGridItem product="${product}" />
						</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="ui-grid-a">
							<div class='ui-block-a left'>
								<ex:productListerGridItem product="${product}" />
							</div>
						<c:if test="${status.last}">
						</div></c:if>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</c:forEach>
</div>
<nav:pagination searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.url}"/>
