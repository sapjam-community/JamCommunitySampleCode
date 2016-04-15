<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="rating" required="true" type="java.lang.Float"%>
<%@ attribute name="displayRating" required="false" %>
<%@ attribute name="recommended" required="false" %>
<%@ attribute name="breakRecommend" required="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty rating}">
<div class="starRatingBar">
		<fieldset class="starrating">
			<c:forEach var="i" begin="0" end="4" step="1">
				<c:choose>
					<c:when test="${rating eq (5-i)}">
						<input type="radio" checked="true"/>
					</c:when>
					<c:otherwise>
						<input type="radio" />
					</c:otherwise>
				</c:choose>
				<label class = "full"></label>
    			<c:choose>
					<c:when test="${rating gt (4-i)}">
						<input type="radio" checked="true"/>
					</c:when>
					<c:otherwise>
						<input type="radio" />
					</c:otherwise>
				</c:choose>
				<label class="half"></label>
			</c:forEach>
		</fieldset>
	<c:if test="${displayRating}">
   		<span class="rating">${rating}</span>
   	</c:if>
   	<c:if test="${recommended}">
   		<c:if test="${breakRecommend}">
   			<br><br>
   		</c:if>
   		<span class="recommended icon jam-icon-like">  <spring:theme code="label.recommended"/></span>
   	</c:if>
 </div>
</c:if>