package com.example.schoolclubsmanagementsystem.models;

public class Payment {
    private String paymentId;
    private String clubId;
    private String userId;
    private double amount;
    private String date;
    private String description;

    // Default constructor required for calls to DataSnapshot.getValue(Payment.class)
    public Payment() {
    }

    public Payment(String paymentId, String clubId, String userId, double amount, String date, String description) {
        this.paymentId = paymentId;
        this.clubId = clubId;
        this.userId = userId;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    // Getters and Setters
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
