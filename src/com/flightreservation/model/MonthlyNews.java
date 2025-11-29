package com.flightreservation.model;

import java.util.Date;

public class MonthlyNews {
    private int id;
    private String title;
    private String content;
    private Date date;

    public MonthlyNews() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
}

