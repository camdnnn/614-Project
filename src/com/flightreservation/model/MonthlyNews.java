package com.flightreservation.model;

import java.util.Date;

public class MonthlyNews {
    private int id;
    private String title;
    private String content;
    private Date publishDate;

    public MonthlyNews(int id, String title, String content, Date publishDate) {
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

    public Date getPublishDate() {
        return publishDate;
    }
}
