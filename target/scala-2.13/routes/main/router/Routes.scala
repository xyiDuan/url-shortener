// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/dxy/IdeaProjects/url-shortener/conf/routes
// @DATE:Sat Oct 19 17:56:23 PDT 2019

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:4
  HomeController_1: controllers.HomeController,
  // @LINE:10
  Assets_0: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:4
    HomeController_1: controllers.HomeController,
    // @LINE:10
    Assets_0: controllers.Assets
  ) = this(errorHandler, HomeController_1, Assets_0, "/")

  def withPrefix(addPrefix: String): Routes = {
    val prefix = play.api.routing.Router.concatPrefix(addPrefix, this.prefix)
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, HomeController_1, Assets_0, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.HomeController.index"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """""" + "$" + """shorturl<[^/]+>""", """controllers.HomeController.redirectShortURLToLongURL(shorturl:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """generate-short-url""", """controllers.HomeController.generateShortURLFromLongURL(request:Request)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """get-log-analysis/""" + "$" + """shorturl<[^/]+>""", """controllers.HomeController.getLogAnalysisResult(shorturl:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.versioned(path:String = "/public", file:Asset)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:4
  private[this] lazy val controllers_HomeController_index0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_HomeController_index0_invoker = createInvoker(
    HomeController_1.index,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "index",
      Nil,
      "GET",
      this.prefix + """""",
      """ get the home page""",
      Seq()
    )
  )

  // @LINE:5
  private[this] lazy val controllers_HomeController_redirectShortURLToLongURL1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), DynamicPart("shorturl", """[^/]+""",true)))
  )
  private[this] lazy val controllers_HomeController_redirectShortURLToLongURL1_invoker = createInvoker(
    HomeController_1.redirectShortURLToLongURL(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "redirectShortURLToLongURL",
      Seq(classOf[String]),
      "GET",
      this.prefix + """""" + "$" + """shorturl<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:6
  private[this] lazy val controllers_HomeController_generateShortURLFromLongURL2_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("generate-short-url")))
  )
  private[this] lazy val controllers_HomeController_generateShortURLFromLongURL2_invoker = createInvoker(
    
    (req:play.mvc.Http.Request) =>
      HomeController_1.generateShortURLFromLongURL(fakeValue[play.mvc.Http.Request]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "generateShortURLFromLongURL",
      Seq(classOf[play.mvc.Http.Request]),
      "POST",
      this.prefix + """generate-short-url""",
      """""",
      Seq()
    )
  )

  // @LINE:7
  private[this] lazy val controllers_HomeController_getLogAnalysisResult3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("get-log-analysis/"), DynamicPart("shorturl", """[^/]+""",true)))
  )
  private[this] lazy val controllers_HomeController_getLogAnalysisResult3_invoker = createInvoker(
    HomeController_1.getLogAnalysisResult(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "getLogAnalysisResult",
      Seq(classOf[String]),
      "GET",
      this.prefix + """get-log-analysis/""" + "$" + """shorturl<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:10
  private[this] lazy val controllers_Assets_versioned4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned4_invoker = createInvoker(
    Assets_0.versioned(fakeValue[String], fakeValue[Asset]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "versioned",
      Seq(classOf[String], classOf[Asset]),
      "GET",
      this.prefix + """assets/""" + "$" + """file<.+>""",
      """ Map static resources from the /public folder to the /assets URL path""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:4
    case controllers_HomeController_index0_route(params@_) =>
      call { 
        controllers_HomeController_index0_invoker.call(HomeController_1.index)
      }
  
    // @LINE:5
    case controllers_HomeController_redirectShortURLToLongURL1_route(params@_) =>
      call(params.fromPath[String]("shorturl", None)) { (shorturl) =>
        controllers_HomeController_redirectShortURLToLongURL1_invoker.call(HomeController_1.redirectShortURLToLongURL(shorturl))
      }
  
    // @LINE:6
    case controllers_HomeController_generateShortURLFromLongURL2_route(params@_) =>
      call { 
        controllers_HomeController_generateShortURLFromLongURL2_invoker.call(
          req => HomeController_1.generateShortURLFromLongURL(req))
      }
  
    // @LINE:7
    case controllers_HomeController_getLogAnalysisResult3_route(params@_) =>
      call(params.fromPath[String]("shorturl", None)) { (shorturl) =>
        controllers_HomeController_getLogAnalysisResult3_invoker.call(HomeController_1.getLogAnalysisResult(shorturl))
      }
  
    // @LINE:10
    case controllers_Assets_versioned4_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned4_invoker.call(Assets_0.versioned(path, file))
      }
  }
}
