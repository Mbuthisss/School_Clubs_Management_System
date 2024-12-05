package com.example.schoolclubsmanagementsystem.firestore;

import android.util.Log;

import com.example.schoolclubsmanagementsystem.models.Event;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class Events {
    private static final String TAG = "Events";
    private FirebaseFirestore db;

    public Events() {
        db = FirebaseFirestore.getInstance();
    }

    public interface FirestoreCallback<T> {
        void onSuccess(T result);
        void onFailure(Exception e);
    }

    // Add an event
    public void addEvent(Event event, FirestoreCallback<Void> callback) {
        Map<String, Object> eventData = new HashMap<>();
        eventData.put("clubId", event.getClubId());
        eventData.put("name", event.getName());
        eventData.put("location", event.getLocation());
        eventData.put("description", event.getDescription());
        eventData.put("date", event.getDate());

        db.collection("events").document(event.getEventId()).set(eventData)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Event added successfully!");
                    callback.onSuccess(null);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error adding event: " + e.getMessage());
                    callback.onFailure(e);
                });
    }

    // Get event details
    public void getEvent(String eventId, FirestoreCallback<Event> callback) {
        db.collection("events").document(eventId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Event event = documentSnapshot.toObject(Event.class);
                        callback.onSuccess(event);
                    } else {
                        Log.d(TAG, "No such event!");
                        callback.onFailure(new Exception("No such event"));
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error getting event: " + e.getMessage());
                    callback.onFailure(e);
                });
    }

    // Update event
    public void updateEvent(String eventId, Map<String, Object> updates, FirestoreCallback<Void> callback) {
        db.collection("events").document(eventId).update(updates)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Event updated successfully!");
                    callback.onSuccess(null);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error updating event: " + e.getMessage());
                    callback.onFailure(e);
                });
    }

    // Delete event
    public void deleteEvent(String eventId, FirestoreCallback<Void> callback) {
        db.collection("events").document(eventId).delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Event deleted successfully!");
                    callback.onSuccess(null);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error deleting event: " + e.getMessage());
                    callback.onFailure(e);
                });
    }

    // List all events for a club
    public void getEvents(String clubId, FirestoreCallback<List<Event>> callback) {
        db.collection("events").whereEqualTo("clubId", clubId).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Event> eventsList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Event event = document.toObject(Event.class);
                        eventsList.add(event);
                    }
                    callback.onSuccess(eventsList);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error getting events: " + e.getMessage());
                    callback.onFailure(e);
                });
    }
}
