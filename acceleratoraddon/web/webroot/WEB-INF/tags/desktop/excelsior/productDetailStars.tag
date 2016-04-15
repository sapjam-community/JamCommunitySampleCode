<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="rating" required="true" type="java.lang.Float" %>
<%@ attribute name="entry1" required="true" %>
<%@ attribute name="entry2" required="true" %>
<%@ attribute name="entry3" required="true" %>
<%@ attribute name="entry4" required="true" %>
<%@ attribute name="entry5" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ex" tagdir="/WEB-INF/tags/addons/excelsiorjsaddon/desktop/excelsior"%>

<c:if test="${not empty rating}">
<c:set var="starWidth" value="16" />
<c:set var="starCount" value="5" />
<c:set var="totalCount" value="${entry1+entry2+entry3+entry4+entry5}" />
<div class="customStar productDescription">
   <%-- <div class="customStarBack" style=" width: <fmt:formatNumber maxFractionDigits="0" value="${5 * starWidth}" />px; ">
      <div class="customStarFront" style=" width: <fmt:formatNumber maxFractionDigits="0" value="${rating * starWidth}" />px; "></div>
   </div> --%>
   <div class="avgRating"><ex:reviewStars rating="${rating}" picsize="12" /></div>
   <div class="starTooltip">
   	<div>
   			<div class="row th"><spring:theme code="tag.rating.stars.score" arguments="${rating}"/></div>
   			<c:forEach var="i" begin="0" end="4" step="1">
				<c:set var="num" value="${5-i}" />
				<c:set var="entry" value="entry${num}" />
				<div class="row">
					<div class="qaColumn star"><spring:theme code="tag.rating.stars.entry" arguments="${num}"/></div>
					<div class="qaColumn progress-bar"></div>
					<div class="qaColumn star-num">${pageScope[entry]}</div>
					<div class="qaColumn percentage"><fmt:formatNumber maxFractionDigits="0" value="${totalCount==0?0:pageScope[entry]/(totalCount)*100}" />%</div>
				</div>
			</c:forEach>
   	</div>
   	<div class="row total"><spring:theme code="tag.rating.stars.total" arguments="${totalCount}"/></div>
   </div>
   <c:choose>
   	<c:when test="${totalCount gt 0}">
   		<span class="rating">(${totalCount})</span>
   	</c:when>
   	<c:otherwise>
   		<span class="rating">(<spring:theme code="tag.rating.notRated"/>)</span>
   	</c:otherwise>
   </c:choose>
</div>



</c:if>