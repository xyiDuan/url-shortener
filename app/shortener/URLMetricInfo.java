package shortener;

/**
 * Data Analysis Metric
 * Will provide the number of times a short url has been accessed in the last 24 hours, past week and all time
 */
public class URLMetricInfo {
    private String shortURL;
    private String longURL;
    private int timesWithin24Hours;
    private int timesWithin7Days;
    private int timesWithinAllTime;

    public URLMetricInfo(String shortURL, String longURL,
                         int timesWithin24Hours, int timesWithin7Days, int timesWithinAllTime) {
        this.shortURL = shortURL;
        this.longURL = longURL;
        this.timesWithin24Hours = timesWithin24Hours;
        this.timesWithin7Days = timesWithin7Days;
        this.timesWithinAllTime = timesWithinAllTime;
    }

    public String getShortURL() {
        return shortURL;
    }

    public String getLongURL() {
        return longURL;
    }

    public int getTimesWithin24Hours() {
        return timesWithin24Hours;
    }

    public int getTimesWithin7Days() {
        return timesWithin7Days;
    }

    public int getTimesWithinAllTime() {
        return timesWithinAllTime;
    }

    @Override
    public String toString() {
        return "URLMetricInfo: {" + shortURL +
                ", " + timesWithin24Hours +
                ", " + timesWithin7Days +
                ", " + timesWithinAllTime + "}";
    }
}
