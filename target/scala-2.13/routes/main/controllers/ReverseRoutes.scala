// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/dxy/IdeaProjects/url-shortener/conf/routes
// @DATE:Sat Oct 19 17:56:23 PDT 2019

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:4
package controllers {

  // @LINE:4
  class ReverseHomeController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:6
    def generateShortURLFromLongURL(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "generate-short-url")
    }
  
    // @LINE:7
    def getLogAnalysisResult(shorturl:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "get-log-analysis/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("shorturl", shorturl)))
    }
  
    // @LINE:4
    def index(): Call = {
      
      Call("GET", _prefix)
    }
  
    // @LINE:5
    def redirectShortURLToLongURL(shorturl:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("shorturl", shorturl)))
    }
  
  }

  // @LINE:10
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:10
    def versioned(file:Asset): Call = {
      implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public"))); _rrc
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
    }
  
  }


}
