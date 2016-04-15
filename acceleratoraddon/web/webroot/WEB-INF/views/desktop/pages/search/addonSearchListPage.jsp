<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="excelsiorHelper" uri="/WEB-INF/tags/addons/excelsiorjsaddon/customLib.tld" %>

<input type="hidden" id="community.network" value="${excelsiorHelper:getCommunityNetworkByResourcePath(siteResourcePath)}" />
<input type="hidden" id="community.server" value="${excelsiorHelper:getCommunityUrlByResourcePath(siteResourcePath)}" />
<input type="hidden" id="hybris_contextPath" value="${pageContext.request.contextPath}"></input>

<script type="text/javascript">
window.excelsiorListWidget = [];
</script>
<script type="text/javascript" src="${pageContext.request.contextPath.substring(0, pageContext.request.contextPath.indexOf('/', 1))}/_ui/addons/excelsiorjsaddon/common/js/excelsiorjsaddon.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath.substring(0, pageContext.request.contextPath.indexOf('/', 1))}/_ui/addons/excelsiorjsaddon/desktop/common/js/excelsiorjs-desktop.js"></script>

<template:page pageTitle="${pageTitle}">
	<jsp:attribute name="pageScripts">
		<script type="text/javascript" src="${commonResourcePath}/js/acc.productlisting.js"></script>
	</jsp:attribute>
	<jsp:body>

	<div id="breadcrumb" class="breadcrumb">
		<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
	</div>
		
	<div id="globalMessages">
		<common:globalMessages/>
	</div>
	<div class="span-6 facetNavigation">
		<nav:facetNavAppliedFilters pageData="${searchPageData}"/>
		<nav:facetNavRefinements pageData="${searchPageData}"/>
		<cms:pageSlot position="Section4" var="feature">
			<cms:component component="${feature}" />
		</cms:pageSlot>
	</div>

	<div class="span-18 last">
		
		<cms:pageSlot position="Section2" var="feature" element="div" >
			<cms:component component="${feature}" />
		</cms:pageSlot>

		<div class="results">
			<h1><spring:theme code="search.page.searchText" arguments="${searchPageData.freeTextSearch}"/></h1>
		</div>
		
		<nav:searchSpellingSuggestion spellingSuggestion="${searchPageData.spellingSuggestion}" />
	  <div id="ex-searchTabs">	
      <div class="ex-tabHead hideHead"><spring:theme code="label.tab.products" arguments="${searchPageData.pagination.totalNumberOfResults}"/></div>
      <div class="ex-tabBody">
		    <nav:pagination top="true"  supportShowPaged="false" 
		    							 supportShowAll="false" 
		    							 searchPageData="${searchPageData}" 
		    							 searchUrl="${searchPageData.currentQuery.url}" 
		    							 numberPagesShown="${numberPagesShown}"/>
		    
		    <product:productLister pageData="${searchPageData}" path="/search"/>

		    <nav:pagination top="false"  supportShowPaged="false" 
		    							 supportShowAll="false" 
		    							 searchPageData="${searchPageData}" 
		    							 searchUrl="${searchPageData.currentQuery.url}" 
		    							 numberPagesShown="${numberPagesShown}"/>
      </div>
      <div class="ex-tabHead hideHead" tabindex="-1"><spring:theme code="label.tab.community"/><span id="ex-searchResult-count"/></div>
      <div class="ex-tabBody">
        <div id="ex-searchResult"></div>
      </div>

    </div>
		

	</div>
</jsp:body>

</template:page>
