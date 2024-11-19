package com.example.schoolclubsmanagementsystem.models;

import java.util.ArrayList;
import java.util.List;

public class Club {
    private String clubId;
    private String name;
    private String description;
    private String coordinator;
    private int capacity;
    private int currentMembers;
    private List<String> members;

    public Club() {
        this.members = new ArrayList<>();
        this.currentMembers = 0; // Initialize with 0 for the coordinator
    }

    // Getters and setters for all fields
    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(String coordinator) {
        this.coordinator = coordinator;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrentMembers() {
        return currentMembers;
    }

    public void setCurrentMembers(int currentMembers) {
        this.currentMembers = currentMembers;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public void addMember(String memberId) {
        if (!members.contains(memberId)) {
            members.add(memberId);
        }
    }
}
