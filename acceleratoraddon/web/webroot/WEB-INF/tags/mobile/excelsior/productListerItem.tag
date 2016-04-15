<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/mobile/product"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="ex" tagdir="/WEB-INF/tags/addons/excelsiorjsaddon/mobile/excelsior"%>

<spring:theme code="text.addToCart" var="addToCartText"/>
<c:url value="${product.url}" var="productUrl"/>
<li class="mlist-listItem" data-theme="d">
	<a href="${productUrl}">
		<div class="ui-grid-a">
			<div class="ui-block-a">
				<product:productPrimaryImage product="${product}" format="thumbnail" zoomable="false"/>
			</div>
			<div class="ui-block-b">
				<h2>${product.name}</h2>
				<span class="mlist-price"><format:price priceData="${product.price}"/></span>
				<c:choose>
            <c:when test="${product.stock.stockLevelStatus.code eq 'outOfStock' }">
                 <span class='listProductOutOfStock mlist-stock'><spring:theme code="product.variants.out.of.stock"/></span>
            </c:when>
            <c:when test="${product.stock.stockLevelStatus.code eq 'lowStock' }">
           		<span class='listProductLowStock mlist-stock'><spring:theme code="product.variants.only.left" arguments="${product.stock.stockLevel}"/></span>
            </c:when>
        </c:choose>
        <div class="details">
					<div class="ex-prodListPage" id="ex-prodListPage-${product.code}">
					</div>
         <script type="text/javascript">
          var option = {
              id: 'ex-prodListPage-${product.code}'
          };

          if (typeof renderListItem != "undefined") {
              renderListItem(option);
          } else if (typeof excelsiorListWidget != "undefined") {
              excelsiorListWidget.push(option);
          }
        </script>
				</div>
			</div>
		</div>
	</a>
</li>
