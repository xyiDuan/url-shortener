package shortener;

import com.typesafe.config.ConfigFactory;
import play.Logger;

import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class URLService {

    private URLDao urlDao = new URLDao();

    private String domainName = ConfigFactory.load().getString("application.baseUrl") + "/";

    // randomize the order for privacy
    private final char[] base62Map = "LXabFIcdPQRef12ghMyNOijVWDkmKYGHn56l78oAps34tuvSTUwCxzBEqrJZ09".toCharArray();

    private Logger.ALogger logger = Logger.of(URLService.class);

    /**
     * GET short url for a long url
     * @param longURL the original url
     * @return short url info
     */
    public URLInfo generateShortURLFromLongURL(String longURL) {
        long id = urlDao.getIdByInsertLongURL(longURL);
        LocalDateTime createdDateTime = LocalDateTime.now();
        String shortURL = generateURLFromId(id, createdDateTime);
        urlDao.populateShortURL(id, shortURL);
        return new URLInfo(longURL, domainName + shortURL, createdDateTime);
    }

    /**
     * Have the long url from short url, will log it in the mean time
     * @param shortURL short url
     * @return long url
     */
    public String getLongURLFromShortURL(String shortURL) {
        Long id = decodeURLStringToId(shortURL);
        String longURL = urlDao.getLongURLById(id);
        if (longURL != null && !longURL.isEmpty()) {
            logger.debug("getLongURLFromShortURL: create new log record for shortURL {}.", shortURL);
            urlDao.logURLInsertion(id, shortURL);
        }
        return longURL;
    }

    /**
     * Get the log analysis for a short url, if short url is not valid, will return empty string for long url mapping
     * The result will contain the number of times a short url has been accessed in the last 24 hours,
     * past week and all time.
     * @param shortURL short url
     * @return metric info
     */
    public URLMetricInfo getURLLogAnalysisResult(String shortURL) {
        long id = decodeURLStringToId(shortURL);
        String longURL = urlDao.getLongURLById(id);
        List<URLInfo> urlInfoList = urlDao.getAllLogInfoFromId(id);
        LocalDateTime nowMinus24Hours = LocalDateTime.now().minusHours(24);
        LocalDateTime nowMinus7Days = LocalDateTime.now().minusDays(7);
        int total = 0;
        int totalWithin24Hours = 0;
        int totalWithinOneWeek = 0;
        for (URLInfo urlLog : urlInfoList) {
            if (urlLog.getCreatedDateTime().isAfter(nowMinus24Hours)) {
                totalWithin24Hours ++;
                totalWithinOneWeek ++;
            } else if (urlLog.getCreatedDateTime().isAfter(nowMinus7Days)) {
                totalWithinOneWeek ++;
            }
            total ++;
        }
        return new URLMetricInfo(domainName + shortURL, longURL, totalWithin24Hours, totalWithinOneWeek, total);
    }

    private String generateURLFromId(long id, LocalDateTime createdDateTime) {
        // get the short url based on Id + created datetime
        String head = getEncodedStringFromTimestamp(createdDateTime);
        return head + encodeNumbersToStringByBase62(id);
    }

    /**
     * Largest possible sum from ts is 1231 + 2359 = 3590 < 62^2
     * @param ts timestamp
     * @return encoded string, based on the largest possible sum, the length will always be smaller than 3
     */
    private String getEncodedStringFromTimestamp(LocalDateTime ts) {
        int val = ts.getMonthValue() * 100 + ts.getDayOfMonth() + ts.getHour() + ts.getSecond();
        String encodedTs =  encodeNumbersToStringByBase62((long) val);
        return encodedTs.length() == 1 ? base62Map[0] + encodedTs : encodedTs;
    }

    private String encodeNumbersToStringByBase62(long idNum) {
        StringBuilder encodedStr = new StringBuilder();
        while (idNum != 0) {
            encodedStr.append(base62Map[(int) idNum % 62]);
            idNum /= 62;
        }
        return encodedStr.toString();
    }

    private long decodeURLStringToId(String shortURL) {
        logger.debug("Start decode shortURL {} to id.", shortURL);
        shortURL = shortURL.substring(2);
        char[] urlArray = shortURL.toCharArray();
        Map<Character, Integer> decodeStrMap = getDecodeStringMap();
        long res = 0;
        for (int i = 0; i < urlArray.length; i ++) {
            res = res * 62 + decodeStrMap.get(urlArray[i]);
        }
        return res;
    }

    private Map<Character, Integer> getDecodeStringMap() {
        Map<Character, Integer> decodeStrMap = new HashMap<>();
        for (int i = 0; i < base62Map.length; i ++) {
            decodeStrMap.put(base62Map[i], i);
        }
        return decodeStrMap;
    }
}
