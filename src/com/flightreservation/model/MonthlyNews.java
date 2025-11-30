package com.flightreservation.model;

public class MonthlyNews {
    private int id;
    private String title;
    private String content;
    private String publishDate;

    public MonthlyNews(int id, String title, String content, String publishDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.publishDate = publishDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getPublishDate() {
        return publishDate;
    }
}
