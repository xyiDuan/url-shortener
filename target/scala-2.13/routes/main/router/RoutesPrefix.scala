// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/dxy/IdeaProjects/url-shortener/conf/routes
// @DATE:Sat Oct 19 17:56:23 PDT 2019


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
