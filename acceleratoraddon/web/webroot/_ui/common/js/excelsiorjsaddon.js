// Refactored by Ricky on 2015/12/28
var require = (function() {
  var head = document.getElementsByTagName("head")[0] || document.documentElement;

  return function(url, complete) {
    var script = document.createElement("script");

    script.async = true;
    script.src = url;

    // Handle Script loading
    var done = false;

    // Attach handlers for all browsers
    script.onload = script.onreadystatechange = function() {
      if (!done && (!script.readyState || /loaded|complete/.test(script.readyState))) {
        done = true;
        // Handle memory leak in IE
        script.onload = script.onreadystatechange = null;

        if (script.parentNode) {
          script.parentNode.removeChild(script);
        }

        script = null;

        if (complete !== undefined) {
          complete();
        }
      }
    };

    // Use insertBefore instead of appendChild  to circumvent an IE6 bug.
    // This arises when a base node is used (#2709 and #4378).
    head.insertBefore(script, head.firstChild);
  };
})();

var queue = function() {
  var readyList = []
  var isReady = false;

  return {
    ready: function() {
      if (isReady) {
        return;
      }

      isReady = true;

      for (var i = 0; i < readyList.length; i++) {
        readyList[i]();
      }
    },
    push: function(fn) {
      if (typeof fn === "function") {
        if (isReady) {
          fn();
        } else {
          readyList.push(fn);
        }
      }
    }
  };
};

//DOM simple ready function
var ready = (function() {
  var readyQueue = queue();

  var doReady = function() {
    readyQueue.ready();
  };

  if (/complete|interactive/.test(document.readyState)) {
    // Handle it asynchronously to allow scripts the opportunity to delay ready
    setTimeout(doReady);
  } else if (document.addEventListener) {
    document.addEventListener("DOMContentLoaded", doReady, false);
    window.addEventListener("load", doReady, false);

  } else {
    // Ensure firing before onload, maybe late but safe also for iframes
    document.attachEvent("onreadystatechange", function() {
      if (document.readyState === "complete") {
        document.detachEvent("onreadystatechange", arguments.callee);
        doReady();
      }
    });

    // A fallback to window.onload, that will always work
    window.attachEvent("onload", doReady);
  }

  return function(fn) {
    readyQueue.push(fn);
  };
})();

var jQueryReady = (function() {
  var readyQueue = queue();
  var checkInterval = 10;

  var checkJQuery = function() {
    if (typeof jQuery != "undefined" && typeof jQuery.fn != "undefined") {
      readyQueue.ready();
    } else {
      window.setTimeout(checkJQuery, checkInterval);
    }
  };

  checkJQuery();

  return function(fn) {
    readyQueue.push(fn);
  };
})();

// init widget here
var excelsiorReady = (function() {
  //check whether excelsiorUrl is loaded?
  if (typeof excelsiorReady != "undefined") {
    return excelsiorReady;
  }

  var excelsiorUrl = $id("community.server").value + "/widget/javascript/community.ui.widget.js";
  var readyQueue = queue();

  var excelsiorInit = function() {
    var language = (document.documentElement || document.body).getAttribute("lang");
    Excelsior.init({
      networkSlug: $id("community.network").value,
      server: $id("community.server").value,
      communicationMode: "UseToken",
      checkLogin: fetchToken,
      doLogin: redirectToLogin,
      lang: language,
      productCode: ($id("addon_prod_code") || {}).value
    });
  };

  //load excelsior here
  var loadExcesior = function() {
    if (loadExcesior.called) {
      return;
    }

    loadExcesior.called = true;

    require(excelsiorUrl, function() {
      excelsiorInit();

      Excelsior.ready(function() {
        readyQueue.ready();
      });
    });
  };

  // if the page does not contain jQuery, we fall back to dom ready
  jQueryReady(loadExcesior);
  ready(loadExcesior);

  return function(fn) {
    readyQueue.push(fn);
  };
})();

function replaceNumber(originText, number) {
  if (originText.indexOf("(") < 0) {
    return originText + "(" + number + ")";
  } else {
    return originText.replace(/\(\d+\)$/, "(" + number + ")");
  }
}

function updateCount(countInfo) {
  var qaTabLabel = $("#tab_qa").contents()
    .filter(function() {
      return this.nodeType == 3;
    });
  qaTabLabel.replaceWith(replaceNumber(qaTabLabel.text(), countInfo.QuestionCount));

  var reviewTabLabel = $("#tab_review").contents()
    .filter(function() {
      return this.nodeType == 3;
    });
  reviewTabLabel.replaceWith(replaceNumber(reviewTabLabel.text(), countInfo.ReviewCount));

  var blogTabLabel = $("#tab_blog").contents()
    .filter(function() {
      return this.nodeType == 3;
    });
  blogTabLabel.replaceWith(replaceNumber(blogTabLabel.text(), countInfo.BlogCount));

  var discussionTabLabel = $("#tab_discussion").contents()
    .filter(function() {
      return this.nodeType == 3;
    });
  discussionTabLabel.replaceWith(replaceNumber(discussionTabLabel.text(), countInfo.DiscussionCount));
}

function $id(id) {
  return document.getElementById(id);
}

function $css(className, parentNode) {
  parentNode = parentNode || document;

  if (typeof parentNode.getElementsByClassName != "undefined") {
    return parentNode.getElementsByClassName(className);
  } else {
    parentNode = parentNode || document;
    var list = parentNode.getElementsByTagName("*");
    var reg = new RegExp("\\b" + className + "\\b(?!-)");
    var results = [];

    for (var i = 0; i < list.length; i++) {
      if (reg.test(list[i].className)) {
        results.push(list[i]);
      }
    }

    return results;
  }
}

/*check login function*/
function checkLogin() {
  var usr = getUserId();
  if (usr != "anonymous" && usr != undefined) {
    return true;
  } else {
    return false;
  }
}

function redirectToLogin() {
  var url = getLoginUrl();
  if (typeof(url) !== "undefined" && url !== "") {
    document.location.href = url;
  } else {
    console.log("Can not find login page!");
  }
}

function fetchToken() {
  var token;
  $.ajax({
    url: $("#hybris_contextPath").val() + "/token",
    type: "GET",
    data: {
      server: $("#community\\.server").val(),
      network: $("#community\\.network").val()
    },
    dataType: "html",
    async: false,
    success: function(html) {
      tokenS = $(html).find("#excelsior_token").val();
      if (tokenS) {
        userId = $(html).find("#hybris_userId").val();
        token = {
          Token: tokenS,
          UserId: userId
        };
      }
    }
  });
  return token;
}

function appendToken() {
  $(document).on("click", ".excelsior a", function(e) {
    var oUrl = $(this).attr("href");
    if (oUrl && oUrl != "#" && !oUrl.endsWith("communityRefinement-page")) {
      e.preventDefault();
      var token = fetchToken();
      var nUrl = oUrl;
      var lang = (document.documentElement || document.body).getAttribute("lang");
      if (token) {
        nUrl = oUrl += "?&user_id=" + encodeURIComponent(Excelsior.Base64.encode(token.UserId)) + "&access_token=" + encodeURIComponent(token.Token);
      }
      if (lang) {
        if(nUrl.indexOf('?') > 0 ){
          nUrl = nUrl + '&locale=' + lang;
        }else{
          nUrl = nUrl + '?&locale=' + lang;
        }
      }
      window.open(nUrl);
      // this.href= nUrl;
    }
  });
}

// function could be re-write in version specific JavaScript, we only add default implementation for desktop here
function getUserId() {
  return ($id("hybris_userId") || {}).value;
}

function getLoginUrl() {
  return $("#header div ul li a").first().attr("href");
}

function changeLayoutForPanels() {
  try {
    var heightLeft = document.querySelectorAll("div.productDetailsPanel > div.productImage")[0].offsetHeight;
    var heightRight = document.querySelectorAll("div.productDetailsPanel > div.productDescription")[0].offsetHeight;

    if (3 / 5 * heightLeft - 20 < heightRight) {
      var exPanelEle = document.getElementById("ex-panel");
      exPanelEle.className = exPanelEle.className + " ex-panel-moved";
      var exDetailPage = document.getElementsByClassName("excelsior-detailpage")[0];
      exDetailPage.className = exDetailPage.className + " ex-panel-moved";
      var refrenceNode = document.querySelectorAll("div.span-10.productDescription.last")[0];
      refrenceNode.parentNode.insertBefore(document.getElementById("ex-panel"), refrenceNode.nextSibling);
      refrenceNode.parentNode.insertBefore(document.getElementsByClassName("excelsior-detailpage")[0], refrenceNode.nextSibling);
    }
  } catch (e) {
    console.log(e);
  }
  var detail = $css("excelsior-detailpage")[0];
  if (detail) {
    detail.style.display = "block";
  }
}

function removeSpinner() {
  $(".excelsior-spinner").removeClass("excelsior-loading");
}

var createElementWithId = function(id, tagName) {
  var elem = document.createElement(tagName || "div");
  elem.id = id;
  elem.style.display = "none";
  document.body.insertBefore(elem, document.body.firstChild);
};

excelsiorReady(function() {
  Excelsior.updateCount(updateCount);
  Excelsior.finishRender(removeSpinner);
});

var renderWidget = (function() {
  var moveWidget = function(widgetId, elemId) {
    var widget = $id(widgetId);
    var elem = $id(elemId);

    // remove it from DOM
    widget.parentNode.removeChild(widget);

    // append to the right place
    elem.appendChild(widget);

    //show it
    widget.style.display = "";
  };

  return function(widgetOption, fnRender) {
    var originalId = widgetOption.id;
    var element = $id(originalId);

    fnRender = fnRender || function() {
      Excelsior.render(widgetOption);
    };

    if (element) {
      excelsiorReady(fnRender);
    } else {
      widgetOption.id = "prefix-" + originalId;

      //create the fake element in memory
      createElementWithId(widgetOption.id);

      excelsiorReady(fnRender);

      // move the fake one to the real one as DOM is ready
      ready(function() {
        moveWidget(widgetOption.id, originalId);
      });
    }
  };
})();

ready(appendToken);

if (typeof excelsiorWidget !== "undefined") {
  for (var i = 0; i < excelsiorWidget.length; i++) {
    renderWidget(excelsiorWidget[i]);
  }
}

// special code for product-list......
var renderListItem = function(widgetOption) {
  renderWidget(widgetOption, function() {
    var id = widgetOption.id;
    var index = id.lastIndexOf('-');
    Excelsior.renderListPageGrid(id, id.substring(index + 1));
  });
};

// set product code list if there is
excelsiorReady(function() {
  var prodCodes = $id('ex-list-prodCodes');

  if (prodCodes) {
    var list = String.prototype.split.call(prodCodes.value, ";");
    list.pop();
    Excelsior.setProductCodeList(list);
  }
});

if (typeof excelsiorListWidget !== "undefined") {
  for (var i = 0; i < excelsiorListWidget.length; i++) {
    renderListItem(excelsiorListWidget[i]);
  }
}

if ($css("excelsior-detailpage").length) {
  changeLayoutForPanels();
} else {
  ready(changeLayoutForPanels);
}