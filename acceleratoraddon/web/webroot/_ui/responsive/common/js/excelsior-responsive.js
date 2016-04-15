excelsiorReady(function() {
  if ($('#ex-searchTabs').length > 0 && !$.isEmptyObject(location.href.match(/\/search/))) {
    $('#product-facet').addClass('ex-product-refinement');
    var keyword = location.search.match(/((text)|(q))=(.+?)((%3A.)|$)/)[4];
    Excelsior.setSearchKeyword(keyword);
    ready(function(){
      Excelsior.render({
        id: ['product-facet', 'ex-searchResult', '', 'communitySearch'],
        name: 'SearchCommunity'
      });
    });

    var setupAccessibleTabs = function() {
      // generate the tab 
      $('#ex-searchTabs').accessibleTabs({
        wrapperClass: 'content',
        currentClass: 'current',
        tabhead: '.ex-tabHead',
        tabbody: '.ex-tabBody',
        fx: 'show',
        fxspeed: null,
        currentInfoText: '',
        // currentInfoPosition: 'prepend',
        // currentInfoClass: 'current-info',
        autoAnchor: true
      });
    };

    Excelsior.finishRender(function() {
      $('.ex-community-refinement').addClass('col-md-3 col-lg-2 hidden-sm hidden-xs  product-facet js-product-facet hideRefinement').insertBefore('.ex-product-refinement');

      if (arguments.callee.called) return;
      arguments.callee.called = true;
      // display the hide tab
      
      if (typeof $.fn.accessibleTabs !== "undefined") {
        setupAccessibleTabs();
      } else {
        $(setupAccessibleTabs);
      }

      // product tab clicked
      $("#accessibletabsnavigation0-0").on('click', function(e) {
        $('.ex-community-refinement').hide();
        // $('.ex-community-refinement').addClass('hide');
        $('.ex-product-refinement').show();
      });

      // community tab clicked
      $("#accessibletabsnavigation0-1").on('click', function(e) {
        $('.ex-community-refinement').show();
        // $('.ex-community-refinement').removeClass('hide');
        $('.ex-product-refinement').hide();
      });
    });
  }
});

// attach excelsior widgets to ACC tabs
ready(function() {
  delete ACC.tabs;

  ACC.tabs = {
    _autoload: [
      ["bindTabs", $(".js-tabs").length > 0],
      "hideReviewBtn",
      "determineToDisplayReviews"
    ],
    bindTabs: function() {
      $e = $(".js-tabs");
      var tabs = $e.accessibleTabs({
        tabhead: '.tabhead',
        tabbody: '.tabbody',
        fx: 'show',
        fxspeed: 0,
        currentClass: 'active',
        autoAnchor: true
      });

      $e.on("click", ".tabhead", function(e) {
        e.preventDefault();

        if ($(this).hasClass("active")) {
          $(this).removeClass("active");
        } else {
          $(this).parents(".js-tabs").children(".tabs-list").find("a[href=" + "#" + $(this).attr("id") + "]").click();

          var offset = $(this).position().top;
          $("body,html").scrollTop(offset)
        }
      });

      $e.on("click", "#tabreview", function(e) {
        e.preventDefault();
        ACC.tabs.showReviewsAction("reviews");
      });

      $e.on("click", ".all-reviews-btn", function(e) {
        e.preventDefault();
        ACC.tabs.showReviewsAction("allreviews");
        ACC.tabs.hideReviewBtn(".all-reviews-btn");
        ACC.tabs.showReviewBtn(".less-reviews-btn");
      });

      $e.on("click", ".less-reviews-btn", function(e) {
        e.preventDefault();
        ACC.tabs.showReviewsAction("reviews");
        ACC.tabs.hideReviewBtn(".less-reviews-btn");
        ACC.tabs.showReviewBtn(".all-reviews-btn");
      });

      $(document).on("click", '.js-writeReviewTab', function(e) {
        e.preventDefault();
        tabs.showAccessibleTabSelector($(this).attr("href"));
        $(".js-review-write").show();
        $('#reviewForm input[name=headline]').focus();
      });

      $(document).on("click", ".js-review-write-toggle", function(e) {
        e.preventDefault();
        if ($(".js-review-write:visible").length < 1) {
          $(".js-review-write").show();

        } else {
          $(".js-review-write").hide();
        }
      });

      $(document).on("click", ".js-openTab", function() {
        tabs.showAccessibleTabSelector($(this).attr("href"));
      });

      $(document).on("click", "#e-panel-footer-read-all-questions", function() {
        
        tabs.showAccessibleTabSelector("#tab_qa");
        //todo:
        $('html, body').animate({
          scrollTop: $("#excelsior-ask-question").offset().top 
        });

      });

      $(document).on("click", "#e-panel-footer-read-all-reviews", function() {
        
        tabs.showAccessibleTabSelector("#tab_review");
        $('html, body').animate({
          scrollTop: $('#excelsior-write-review').offset().top
        });

      });
    },
    showReviewsAction: function(s) {
      $.get($("#reviews").data(s), function(result) {
        $('#reviews').html(result);
        if ($(".js-ratingCalc").length > 0) {
          ACC.ratingstars.bindRatingStars();
          ACC.tabs.showingAllReviews();
        }
      });
    },
    hideReviewBtn: function(btnClass) {
      btnClass = (btnClass == undefined) ? ".less-reviews-btn" : btnClass;
      $(btnClass).hide();
    },
    showReviewBtn: function(btnClass) {
      $(btnClass).show();
    },
    showingAllReviews: function() {
      var isShowingAllReviews = $("#showingAllReviews").data("showingallreviews");
      if (isShowingAllReviews) {
        ACC.tabs.hideReviewBtn(".all-reviews-btn");
      }
    },
    determineToDisplayReviews: function() {
      if (location.hash == "#tabreview") {
        ACC.tabs.showReviewsAction('reviews');
      }
    }
  };
});

window.getLoginUrl = function() {
  return $('.md-secondary-navigation>ul>li>a').first().attr('href');
};

var origianlUpdate = window.updateCount;

window.updateCount = function(countInfo) {
  //fix tab number on responsive
  var qaContentHead = $($('#tab_qa').attr('href') + ' a').contents();
  qaContentHead.replaceWith(replaceNumber(qaContentHead.text(), countInfo.QuestionCount));
  var reviewContentHead = $($('#tab_review').attr('href') + ' a').contents();
  reviewContentHead.replaceWith(replaceNumber(reviewContentHead.text(), countInfo.ReviewCount));
  var blogContentHead = $($('#tab_blog').attr('href') + ' a').contents();
  blogContentHead.replaceWith(replaceNumber(blogContentHead.text(), countInfo.BlogCount));
  var discussionContentHead = $($('#tab_discussion').attr('href') + ' a').contents();
  discussionContentHead.replaceWith(replaceNumber(discussionContentHead.text(), countInfo.DiscussionCount));

  origianlUpdate && origianlUpdate.apply(this, arguments);
};
