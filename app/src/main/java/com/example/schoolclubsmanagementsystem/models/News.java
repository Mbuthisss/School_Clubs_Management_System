package com.example.schoolclubsmanagementsystem.models;

public class News {
    private String title;
    private String date;
    private String description;

    // Constructor
    public News(String title, String date, String description) {
        this.title = title;
        this.date = date;
        this.description = description;
    }

    // Getter methods
    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    // Optionally, you can add setters if you need them
}
