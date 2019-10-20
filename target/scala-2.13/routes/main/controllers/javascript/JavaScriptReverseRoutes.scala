// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/dxy/IdeaProjects/url-shortener/conf/routes
// @DATE:Sat Oct 19 17:56:23 PDT 2019

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:4
package controllers.javascript {

  // @LINE:4
  class ReverseHomeController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:6
    def generateShortURLFromLongURL: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.HomeController.generateShortURLFromLongURL",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "generate-short-url"})
        }
      """
    )
  
    // @LINE:7
    def getLogAnalysisResult: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.HomeController.getLogAnalysisResult",
      """
        function(shorturl0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "get-log-analysis/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("shorturl", shorturl0))})
        }
      """
    )
  
    // @LINE:4
    def index: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.HomeController.index",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + """"})
        }
      """
    )
  
    // @LINE:5
    def redirectShortURLToLongURL: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.HomeController.redirectShortURLToLongURL",
      """
        function(shorturl0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("shorturl", shorturl0))})
        }
      """
    )
  
  }

  // @LINE:10
  class ReverseAssets(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:10
    def versioned: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Assets.versioned",
      """
        function(file1) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[play.api.mvc.PathBindable[Asset]].javascriptUnbind + """)("file", file1)})
        }
      """
    )
  
  }


}
