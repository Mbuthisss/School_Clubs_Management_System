package com.example.schoolclubsmanagementsystem.firestore;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class Announcements {
    private FirebaseFirestore db;

    public Announcements() {
        db = FirestoreInit.getFirestoreInstance();
    }

    // Add an announcement
    public void addAnnouncement(String announcementId, String clubId, String title, String description, String date) {
        Map<String, Object> announcement = new HashMap<>();
        announcement.put("clubId", clubId);
        announcement.put("title", title);
        announcement.put("description", description);
        announcement.put("date", date);

        db.collection("announcements").document(announcementId).set(announcement)
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Announcement added successfully!");
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error adding announcement: " + e.getMessage());
                });
    }

    // Get announcement details
    public void getAnnouncement(String announcementId) {
        db.collection("announcements").document(announcementId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        System.out.println(documentSnapshot.getData());
                    } else {
                        System.out.println("No such announcement!");
                    }
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error getting announcement: " + e.getMessage());
                });
    }

    // Update announcement
    public void updateAnnouncement(String announcementId, Map<String, Object> updates) {
        db.collection("announcements").document(announcementId).update(updates)
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Announcement updated successfully!");
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error updating announcement: " + e.getMessage());
                });
    }

    // Delete announcement
    public void deleteAnnouncement(String announcementId) {
        db.collection("announcements").document(announcementId).delete()
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Announcement deleted successfully!");
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error deleting announcement: " + e.getMessage());
                });
    }
}
