package com.example.schoolclubsmanagementsystem.models;

public class Announcement {
    private String announcementId;
    private String clubId;
    private String title;
    private String description;
    private String date;

    public Announcement() {
        // Default constructor required for calls to DataSnapshot.getValue(Announcement.class)
    }

    public Announcement(String announcementId, String clubId, String title, String description, String date) {
        this.announcementId = announcementId;
        this.clubId = clubId;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public String getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(String announcementId) {
        this.announcementId = announcementId;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
