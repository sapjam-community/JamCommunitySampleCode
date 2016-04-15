// created by Ricky on 2016/01/20
ready(function() {
  $(document).on('click', '#e-panel-footer-read-all-reviews', function(e) {
    $('html, body').animate({
      scrollTop: $("#productTabs").offset().top
    });

    ACC.productTabs.productTabs.showAccessibleTabSelector('#tab_review');
  });

  $(document).on('click', '#e-panel-footer-read-all-questions', function(e) {
    $('html, body').animate({
      scrollTop: $("#productTabs").offset().top
    });

    ACC.productTabs.productTabs.showAccessibleTabSelector('#tab_qa');
  });
});

// set search widget here
excelsiorReady(function() {
  if ($("#ex-searchTabs").length > 0 && !$.isEmptyObject(location.href.match(/\/search/))) {
    // set new id to the product refinement page.
    $(".facetNavigation").parents("div:eq(0)").attr("id", "ex-refinement");
    $(".facetNavigation").addClass("ex-product-refinement");
    var keyword = location.search.match(/((text)|(q))=(.+?)((%3A.)|$)/)[4];

    Excelsior.setSearchKeyword(keyword);
    ready(function(){
      Excelsior.render({
        id: [".facetNavigation", "ex-searchResult"],
        name: "SearchCommunity"
      });
    }); 

    var setupAccessibleTabs = function() {
      // generate the tab
      $("#ex-searchTabs").accessibleTabs({
        wrapperClass: "content",
        currentClass: "current",
        tabhead: ".ex-tabHead",
        tabbody: ".ex-tabBody",
        fx: "show",
        fxspeed: null,
        currentInfoText: "current tab: ",
        currentInfoPosition: "prepend",
        currentInfoClass: "current-info",
        autoAnchor: true
      });
    };

    Excelsior.finishRender(function() {
      $(".ex-community-refinement").addClass("span-6 facetNavigation hideRefinement").insertBefore(".ex-product-refinement");

      if (arguments.callee.called) return;
      arguments.callee.called = true;
      // display the hide tab
      
      if (typeof $.fn.accessibleTabs !== "undefined") {
        setupAccessibleTabs();
      } else {
        $(setupAccessibleTabs);
      }

      // product tab clicked
      $("#accessibletabsnavigation0-0").on("click", function(e) {
        $(".ex-community-refinement").hide();
        $(".ex-product-refinement").show();
      });

      // community tab clicked
      $("#accessibletabsnavigation0-1").on("click", function(e) {
        $(".ex-community-refinement").show();
        $(".ex-product-refinement").hide();
      });
    });
  }
});
