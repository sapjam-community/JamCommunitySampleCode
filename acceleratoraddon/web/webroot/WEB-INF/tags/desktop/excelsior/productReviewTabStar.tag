<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ex" tagdir="/WEB-INF/tags/addons/excelsiorjsaddon/desktop/excelsior"%>



<div class="reviews productDescription">

    <div class="review-general">
        <p class="rating">${productTopic.avgRating}</p>
        <ex:reviewStars rating="${productTopic.avgRating}" picsize="24"/>
        <p class="review-count">${productTopic.ratingDistribution[5]} &nbsp;<spring:theme code="label.reviews" /></p>
        <p class="review-info"><span class="jam-icon-like"></span> ${productTopic.recommendCount} / ${productTopic.reviewCount} <span>&nbsp;<spring:theme code="label.recommendsThisProduct"/></span></p>
    </div>

    <div class="review-ratings">

            <div class="row th"><spring:theme code="tag.rating.stars.score" arguments="${productTopic.avgRating}"/></div>
            <c:forEach var="i" begin="0" end="4" step="1">
                <c:set var="num" value="${5-i}" />
                <div class="row">
                 	<div class="reviewTabColumn star"><spring:theme code="tag.rating.stars.entry" arguments="${num}"/></div>
                	<div class="reviewTabColumn progress-bar"></div>
                	<div class="reviewTabColumn percentage">${productTopic.ratingDistribution[4-i]}</div>
                	<div class="reviewTabColumn percentage" style="display:none">${productTopic.ratingDistribution[4-i] / productTopic.ratingDistribution[5] * 100}</div>
				</div>
            </c:forEach>
    </div>

</div>
