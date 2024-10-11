package com.example.schoolclubsmanagementsystem.firestore;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class Events {
    private FirebaseFirestore db;

    public Events() {
        db = FirestoreInit.getFirestoreInstance();
    }

    // Add an event
    public void addEvent(String eventId, String clubId, String eventName, String location, String description) {
        Map<String, Object> event = new HashMap<>();
        event.put("clubId", clubId);
        event.put("eventName", eventName);
        event.put("location", location);
        event.put("description", description);

        db.collection("events").document(eventId).set(event)
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Event added successfully!");
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error adding event: " + e.getMessage());
                });
    }

    // Get event details
    public void getEvent(String eventId) {
        db.collection("events").document(eventId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        System.out.println(documentSnapshot.getData());
                    } else {
                        System.out.println("No such event!");
                    }
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error getting event: " + e.getMessage());
                });
    }

    // Update event
    public void updateEvent(String eventId, Map<String, Object> updates) {
        db.collection("events").document(eventId).update(updates)
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Event updated successfully!");
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error updating event: " + e.getMessage());
                });
    }

    // Delete event
    public void deleteEvent(String eventId) {
        db.collection("events").document(eventId).delete()
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Event deleted successfully!");
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error deleting event: " + e.getMessage());
                });
    }
}
