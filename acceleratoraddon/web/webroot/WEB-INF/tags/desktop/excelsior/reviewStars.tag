<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="rating" required="true" type="java.lang.Float"%>
<%@ attribute name="displayRating" required="false" %>
<%@ attribute name="recommended" required="false" %>
<%@ attribute name="dot" required="false" %>
<%@ attribute name="breakRecommend" required="false" %>
<%@ attribute name="picsize" required="false" type="java.lang.Integer" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty rating}">
<div class="starRatingBar">
	<fieldset class="starrating" rating="${rating}" recommended="${recommended}" picsize="${picsize}"/></fieldset>
	<c:if test="${displayRating}">
   		<span class="rating">${rating}</span>
   	</c:if>
   	<c:if test="${recommended}">
   		<c:if test="${breakRecommend}">
   			<br><br>
   		</c:if>
   		<c:if test="${dot}">
   			<span>&nbsp; &nbsp; &middot; </span>
   		</c:if>
   		<span class="recommended icon jam-icon-like">  <spring:theme code="label.recommended"/></span>
   	</c:if>
 </div>
</c:if>