<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ex" tagdir="/WEB-INF/tags/addons/excelsioraddon/desktop/excelsior"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

	<c:forEach items="${productTopic.reviews}" var="review"  varStatus="status">
		<div class="reviewTabContent">
		<div class="reviewTab_left">
			<div class="reviewTab_left_in"><a href="${review.metaData.server}${review.metaData.userUrl}" target="_blank"><img id="reviewTab_img" src="${productTopic.metaData.server}${review.author.photoUrl}"/></a></div>
			<div class="reviewTab_meta"><span class="name">${review.author.name} &middot; </span>
			<span class="date"><spring:theme code="label.date" arguments="${review.dateFormatter.monthName},${review.dateFormatter.day},${review.dateFormatter.year },${review.dateFormatter.month}"/></span></div>
			<div class="reviewTab_left_rating"><ex:reviewStars picsize="12" rating="${review.rating}" recommended="${review.isRecommended}" breakRecommend="true"/></div>
		</div>
		<div class="reviewTab_right">
			<a href="${review.metaData.server}${review.metaData.reviewUrl}" target="_blank">
			<div class="tab_review_title"><c:out value="${review.title}"/></div>
			<div class="tab_review_content">${review.content}</div> 
			</a>
			<div class="feedback" style="display:none"><span class="icon-sys-enter-2"></span> &nbsp;<spring:theme code="label.thanksForFeedback"/></div>
			<div class="review_helpful_widget">
					<div class="review_helpful"><span><spring:theme code="label.review.helpful"/></span></div>
					<div class="review_helpful_yes"><button type="button"><spring:theme code="label.yes"></spring:theme></button></div>
					<div class="review_helpful_no"><button  type="button"><spring:theme code="label.no"></spring:theme></button></div>
			</div>
		</div>
		</div>
		<c:if test="${status.index lt (fn:length(productTopic.reviews)-1)}">
				<hr/>
		</c:if>
	</c:forEach>
