package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.Http.Request;
import play.mvc.Result;
import play.mvc.Controller;
import shortener.URLInfo;
import shortener.URLService;

public class HomeController extends Controller {

    private URLService urlService = new URLService();

    public Result index() {
        return ok(views.html.index.render());
    }

    public Result redirectShortURLToLongURL(String shortURL) {
        String longURL = urlService.getLongURLFromShortURL(shortURL);
        if (!longURL.contains("http")) {
            longURL = "https://" + longURL;
        }
        return redirect(longURL);
    }

    public Result generateShortURLFromLongURL(Request request) {
        JsonNode jsonNode = request.body().asJson();
        String longURL = jsonNode.get("url").asText();
        URLInfo urlInfo = urlService.generateShortURLFromLongURL(longURL);
        return ok(Json.toJson(urlInfo));
    }

    public Result getLogAnalysisResult(String shortURL) {
        return ok(Json.toJson(urlService.getURLLogAnalysisResult(shortURL)));
    }
}
