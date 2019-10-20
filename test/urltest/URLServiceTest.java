package urltest;

import org.junit.Test;
import shortener.URLInfo;
import shortener.URLMetricInfo;
import shortener.URLService;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class URLServiceTest {
    private URLService urlService = new URLService();

    private String TEST_URL = "https://www.cloudflare.com/";

    @Test
    public void testGenerateShortURL() {
        URLInfo urlInfo = urlService.generateShortURLFromLongURL(TEST_URL);
        assertEquals(TEST_URL, urlInfo.getLongURL());
        assertNotNull(urlInfo.getShortURL());
        assertTrue(LocalDate.now().equals(urlInfo.getCreatedDateTime().toLocalDate()));
    }

    @Test
    public void testGetLongURLFromShortURL() {
        URLInfo urlInfo = urlService.generateShortURLFromLongURL(TEST_URL);
        String shortURLKey = urlInfo.getShortURL().split("/")[3];
        String longURL = urlService.getLongURLFromShortURL(shortURLKey);
        assertEquals(TEST_URL, longURL);
    }

    @Test
    public void testGetLogAnalysisResult() {
        URLInfo urlInfo = urlService.generateShortURLFromLongURL(TEST_URL);
        String shortURLKey = urlInfo.getShortURL().split("/")[3];
        URLMetricInfo urlMetricInfo = urlService.getURLLogAnalysisResult(shortURLKey);
        assertEquals(0, urlMetricInfo.getTimesWithinAllTime());
        urlService.getLongURLFromShortURL(shortURLKey);
        urlMetricInfo = urlService.getURLLogAnalysisResult(shortURLKey);
        assertEquals(1, urlMetricInfo.getTimesWithinAllTime());
        assertEquals(1, urlMetricInfo.getTimesWithin7Days());
        assertEquals(1, urlMetricInfo.getTimesWithin24Hours());
    }
}
