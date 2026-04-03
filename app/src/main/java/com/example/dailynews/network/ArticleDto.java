package com.example.dailynews.network;

public class ArticleDto {
    public SourceDto source;
    public String author;
    public String title;
    public String description;
    public String url;
    public String urlToImage;
    public String publishedAt; // ISO 8601
    public String content;

    public static class SourceDto {
        public String id;
        public String name;
    }
}
