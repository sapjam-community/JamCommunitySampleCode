<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ex" tagdir="/WEB-INF/tags/addons/excelsiorjsaddon/responsive/excelsior" %>

<template:page pageTitle="${pageTitle}">
	<ex:productDetailsPanel />
	<ex:productPageTabs />
	<cms:pageSlot position="UpSelling" var="comp">
		<cms:component component="${comp}"/>
	</cms:pageSlot>
</template:page>