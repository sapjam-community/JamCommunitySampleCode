<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>


<div class="product-details">
	<ycommerce:testId code="productDetails_productNamePrice_label_${product.code}">
		<div class="name">${product.name} <span class="sku">ID ${product.code}</span></div>
	</ycommerce:testId>
</div>
<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
	<div class="col-md-6 col-lg-4">
		<product:productImagePanel galleryImages="${galleryImages}"
			product="${product}" />
	</div>
	<div class="col-md-6 col-lg-8">
		<div class="row">
			<div class="col-lg-6">
				<div class="product-details">
					<product:productPromotionSection product="${product}"/>

					<ycommerce:testId
						code="productDetails_productNamePrice_label_${product.code}">
						<product:productPricePanel product="${product}" />
					</ycommerce:testId>

					<div class="description">${product.summary}</div>
				</div>
			</div>

			<div class="col-lg-6">

				<cms:pageSlot position="VariantSelector" var="component">
					<cms:component component="${component}" />
				</cms:pageSlot>

				<cms:pageSlot position="AddToCart" var="component">
					<cms:component component="${component}" />
				</cms:pageSlot>
			</div>
			<cms:pageSlot position="Section2" var="feature" element="div" class="col-lg-12 col-md-12 col-sm-12 col-sm-12 section2 cms_disp-img_slot last" id="reviewcomponentslot">
					<cms:component component="${feature}"/>
				</cms:pageSlot>
		</div>
	</div>
</div>

