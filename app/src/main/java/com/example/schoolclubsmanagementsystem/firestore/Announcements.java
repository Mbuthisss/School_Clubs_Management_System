package com.example.schoolclubsmanagementsystem.firestore;

import android.util.Log;

import com.example.schoolclubsmanagementsystem.models.Announcement;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class Announcements {
    private static final String TAG = "Announcements";
    private FirebaseFirestore db;

    public Announcements() {
        db = FirebaseFirestore.getInstance();
    }

    public interface FirestoreCallback<T> {
        void onSuccess(T result);
        void onFailure(Exception e);
    }

    // Add an announcement
    public void addAnnouncement(Announcement announcement, FirestoreCallback<Void> callback) {
        Map<String, Object> announcementData = new HashMap<>();
        announcementData.put("clubId", announcement.getClubId());
        announcementData.put("title", announcement.getTitle());
        announcementData.put("description", announcement.getDescription());
        announcementData.put("date", announcement.getDate());

        db.collection("announcements").document(announcement.getAnnouncementId()).set(announcementData)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Announcement added successfully!");
                    callback.onSuccess(null);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error adding announcement: " + e.getMessage());
                    callback.onFailure(e);
                });
    }

    // Get announcement details
    public void getAnnouncement(String announcementId, FirestoreCallback<Announcement> callback) {
        db.collection("announcements").document(announcementId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Announcement announcement = documentSnapshot.toObject(Announcement.class);
                        callback.onSuccess(announcement);
                    } else {
                        Log.d(TAG, "No such announcement!");
                        callback.onFailure(new Exception("No such announcement"));
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error getting announcement: " + e.getMessage());
                    callback.onFailure(e);
                });
    }

    // Update announcement
    public void updateAnnouncement(String announcementId, Map<String, Object> updates, FirestoreCallback<Void> callback) {
        db.collection("announcements").document(announcementId).update(updates)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Announcement updated successfully!");
                    callback.onSuccess(null);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error updating announcement: " + e.getMessage());
                    callback.onFailure(e);
                });
    }

    // Delete announcement
    public void deleteAnnouncement(String announcementId, FirestoreCallback<Void> callback) {
        db.collection("announcements").document(announcementId).delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Announcement deleted successfully!");
                    callback.onSuccess(null);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error deleting announcement: " + e.getMessage());
                    callback.onFailure(e);
                });
    }

    // List all announcements for a club
    public void listAllAnnouncements(String clubId, FirestoreCallback<List<Announcement>> callback) {
        db.collection("announcements").whereEqualTo("clubId", clubId).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Announcement> announcementsList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Announcement announcement = document.toObject(Announcement.class);
                        announcementsList.add(announcement);
                    }
                    callback.onSuccess(announcementsList);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error listing announcements: " + e.getMessage());
                    callback.onFailure(e);
                });
    }
}
