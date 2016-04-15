// created by Ricky on 2016/01/20

// override updateCount as the selector is different in mobile version
window.updateCount = function(countInfo) {
  var qaTabLabel = $('#tab_qa .ui-btn-text').contents().filter(function() {
    return this.nodeType == 3;
  });
  qaTabLabel.replaceWith(replaceNumber(qaTabLabel.text(), countInfo.QuestionCount));

  var reviewTabLabel = $('#tab_review .ui-btn-text').contents().filter(function() {
    return this.nodeType == 3;
  });
  reviewTabLabel.replaceWith(replaceNumber(reviewTabLabel.text(), countInfo.ReviewCount));

  var blogTabLabel = $('#tab_blog .ui-btn-text').contents().filter(function() {
    return this.nodeType == 3;
  });
  blogTabLabel.replaceWith(replaceNumber(blogTabLabel.text(), countInfo.BlogCount));

  var discussionTabLabel = $('#tab_discussion .ui-btn-text').contents().filter(function() {
    return this.nodeType == 3;
  });
  discussionTabLabel.replaceWith(replaceNumber(discussionTabLabel.text(), countInfo.DiscussionCount));
};

// overider redirect to login
window.getLoginUrl = function() {
  var loginUrl;
  var rlogin = /login/i;

  // loop user account menu list to retrive hybirs login url
  // added by Ricky on 2015/11/5 to fix mobile login issue
  $("#userSettings .ui-btn-text a").each(function() {
    var url = $(this).attr("href");

    if (rlogin.test(url)) {
      loginUrl = url;
      return false;
    }
  });

  return loginUrl;
};

// set search widget here
excelsiorReady(function() {
  if ($('#ex-searchTabs').length > 0 && !$.isEmptyObject(location.href.match(/\/search/))) {
    var keyword = location.search.match(/((text)|(q))=(.+?)((%3A.)|$)/)[4];
    Excelsior.setSearchKeyword(keyword);
    ready(function(){
      Excelsior.render({
        id: ['', 'ex-searchResult', 'communitySearch'],
        name: 'SearchCommunity'
      });
    });
   };
});

// attach event here
ready(function() {
  $(document).on('click', '.ex-navbar', function(e) {
    var exindex = $(e.currentTarget).attr('exindex');
    $('#ex-searchTabs .ui-content').hide();
    $('.ex-content-' + exindex).show();
  });

  // read all reviews
  $(document).on('click', '#e-panel-footer-read-all-reviews', function() {
    $('html, body').animate({
      scrollTop: $("#tab_review").offset().top
    });
    $("#review_expand").trigger('expand');
  });

  // read all questions
  $(document).on('click', '#e-panel-footer-read-all-questions', function() {
    $('html, body').animate({
      scrollTop: $("#tab_qa").offset().top
    });
    $("#qa_expand").trigger('expand');
  });

  // remove loading spinner image
  $(document).on('click', '.excelsior a', function() {
    $('.ui-loading .ui-loader').css('display', 'none');
  });
});
