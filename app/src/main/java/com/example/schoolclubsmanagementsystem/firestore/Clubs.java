package com.example.schoolclubsmanagementsystem.firestore;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class Clubs {
    private FirebaseFirestore db;

    public Clubs() {
        db = FirestoreInit.getFirestoreInstance();
    }

    // Add a new club
    public void addClub(String clubId, String name, String description, int capacity, String coordinator) {
        Map<String, Object> club = new HashMap<>();
        club.put("clubId", clubId);
        club.put("name", name);
        club.put("description", description);
        club.put("capacity", capacity);
        club.put("coordinator", coordinator);

        db.collection("clubs").document(clubId).set(club)
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Club added successfully!");
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error adding club: " + e.getMessage());
                });
    }

    // Get club details
    public void getClub(String clubId) {
        db.collection("clubs").document(clubId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        System.out.println(documentSnapshot.getData());
                    } else {
                        System.out.println("No such club!");
                    }
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error getting club: " + e.getMessage());
                });
    }

    // Update club details
    public void updateClub(String clubId, Map<String, Object> updates) {
        db.collection("clubs").document(clubId).update(updates)
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Club updated successfully!");
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error updating club: " + e.getMessage());
                });
    }

    // Delete club
    public void deleteClub(String clubId) {
        db.collection("clubs").document(clubId).delete()
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Club deleted successfully!");
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error deleting club: " + e.getMessage());
                });
    }
}
