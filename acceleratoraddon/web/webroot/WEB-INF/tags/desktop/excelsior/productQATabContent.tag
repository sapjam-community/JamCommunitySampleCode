<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="voteUp" 	scope="page"><spring:theme code="tooltip.vote.up"/></c:set>
<c:set var="voteDown" scope="page"><spring:theme code="tooltip.vote.down"/></c:set>
<div class="hiddenMsg">
	<div id="msg_nopermission"><spring:theme code="msg.vote.nopermission"/></div>
	<div id="msg_votefail"><spring:theme code="msg.vote.fail"/></div>
	<div id="msg_votetwice"><spring:theme code="msg.vote.twice"/></div>
</div>
<c:forEach items="${productTopic.questions}" var="question"  varStatus="status">
			<div class="qaTab_questionBody">
				<span class="vote">
					<a id="vote_up_${question.questionID}" title="${voteUp}" href="#" onclick="javascript:vote(this, 'up'); return false;"><span class="icon icon-up"></span></a></br>
					<span id="vote_count_${question.questionID}" class="vote_count">${question.voteCount}</span></br>
					<a id="vote_dn_${question.questionID}" title="${voteDown}" href="#" onclick="javascript:vote(this, 'down'); return false;"><span class="icon icon-down"></a></span>
				</span>
				<span>
					<a href="${question.metaData.server}${question.metaData.userUrl}" target="_blank">
					<img class="qaTab_img" src="${productTopic.metaData.server}${question.author.photoUrl}"/>
					</a>
				</span>
				<div class="content_r_tab">
					<a href="${question.metaData.server}${question.metaData.questionUrl}" target="_blank">
					<div class="question_title"><c:out value="${question.title}"/></div>
					<div class="question_content_tab">${question.content}</div>
					</a>
					<div class="question_meta"><a href="${question.metaData.server}${question.metaData.userUrl}" target="_blank"><span class="name">${question.author.name} &middot; </span></a>
					<span class="date"><spring:theme code="label.date" arguments="${question.dateFormatter.monthName},${question.dateFormatter.day},${question.dateFormatter.year},${question.dateFormatter.month}"/></span></div>
				</div>
				<c:if test="${not empty question.featuredAnswers}">
					<div class="space_tab"></div>
				</c:if>
				<div class="answerBody">
					<c:forEach items="${question.featuredAnswers}" var="answer" varStatus="name">
						<div class="bestAnswer">
						<img class="qaTab_img" src="${productTopic.metaData.server}${answer.author.photoUrl}"/>
						<div class="qaTab_answer">${answer.content}</div>
						<div class="answer_meta"><span class="icon-sys-enter-2"></span><span class="tag">&nbsp;<spring:theme code="label.best.answer"/>  &middot; </span><a href="${question.metaData.server}${answer.metaData.userUrl}" target="_blank"><span class="name">${answer.author.name} &middot; </span></a>
						<span class="date"><spring:theme code="label.date" arguments="${answer.dateFormatter.monthName},${answer.dateFormatter.day},${answer.dateFormatter.year},${answer.dateFormatter.month}"/></span></div>
						</div>
					</c:forEach>
				</div>
				<c:if test="${question.answersCount ge 1}">
					<c:choose>
						<c:when test="${not empty question.featuredAnswers}">
						<div class="answerBody_bottom">
							<a class="showMoreAnswers">
								<span class="icon-post"></span>
										<span>&nbsp;<spring:theme code="label.show.more.answers"/> (</span>
								<span>${fn:length(question.featuredAnswers)}</span>
								<span>/ ${question.answersCount} )</span>
							</a>
							<input type="hidden" value="${question.questionID}"/>
							<input type="hidden" value="0"/>
						</div>
						</c:when>
						<c:otherwise>
						<div class="answerBody_bottom_no_feature">
							<a class="showMoreAnswers">
								<span class="icon-post"></span>
										<span>&nbsp;<spring:theme code="label.show.answers"/> (</span>
								<span>${fn:length(question.featuredAnswers)}</span>
								<span>/ ${question.answersCount} )</span>
							</a>
							<input type="hidden" value="${question.questionID}"/>
							<input type="hidden" value="0"/>
						</div>
						</c:otherwise>
					</c:choose>
				</c:if>
				
			</div>
			<c:if test="${status.index lt (fn:length(productTopic.questions)-1)}">
				<hr/>
			</c:if>
	</c:forEach>
