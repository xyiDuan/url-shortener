package shortener;

import javax.inject.Singleton;

@Singleton
public class QueryHelper {
    public String insertLongURL(String url) {
        return "INSERT INTO url_profile(long_url) VALUES('" + url + "');";
    }

    public String populateShortURLById(Long id, String shortURL) {
        return "UPDATE url_profile SET short_url = " + "'" + shortURL + "'" + " WHERE id = " + id;
    }

    public String getLongURLById(Long id) {
        return "SELECT long_url FROM url_profile WHERE id = '" + id + "';";
    }

    public String insertURLLog() {
        return "INSERT INTO url_logs(id, short_url) VALUES(?, ?)";
    }

    public String getShortURLLogFromId(Long id) {
        return "SELECT short_url, created_datetime FROM url_logs WHERE id = '" + id + "';";
    }
}
