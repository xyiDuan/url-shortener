package shortener;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class URLInfo {
    private String longURL;
    private String shortURL;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss")
    private LocalDateTime createdDateTime;

    public URLInfo(String longURL, String shortURL, LocalDateTime createdDateTime) {
        this.longURL = longURL;
        this.shortURL = shortURL;
        this.createdDateTime = createdDateTime;
    }

    public URLInfo(String shortURL, LocalDateTime createdDateTime) {
        this.shortURL = shortURL;
        this.createdDateTime = createdDateTime;
    }

    public String getLongURL() {
        return longURL;
    }

    public String getShortURL() {
        return shortURL;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    @Override
    public String toString() {
        return "URLInfo: {shortURL: " + shortURL +
                ", longURL: " + longURL +
                ", createdDateTime: " + createdDateTime.toString() + "}";
    }
}
