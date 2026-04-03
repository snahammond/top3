package com.example.dailynews.model;

public class NewsItem {
    private final String title;
    private final String source;
    private final String publishedAt; // ISO 8601 timestamp, e.g. 2026-04-03T20:30:00Z
    private final String imageUrl;
    private final String url;

    public NewsItem(String title, String source, String publishedAt, String imageUrl, String url) {
        this.title = title;
        this.source = source;
        this.publishedAt = publishedAt;
        this.imageUrl = imageUrl;
        this.url = url;
    }

    // Convenience constructor if you don't have a URL yet
    public NewsItem(String title, String source, String publishedAt, String imageUrl) {
        this(title, source, publishedAt, imageUrl, "");
    }

    public String getTitle() { return title; }
    public String getSource() { return source; }
    public String getPublishedAt() { return publishedAt; }
    public String getImageUrl() { return imageUrl; }
    public String getUrl() { return url; }
}
