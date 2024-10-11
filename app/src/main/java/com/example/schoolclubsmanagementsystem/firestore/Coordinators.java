package com.example.schoolclubsmanagementsystem.firestore;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class Coordinators {
    private FirebaseFirestore db;

    public Coordinators() {
        db = FirestoreInit.getFirestoreInstance();
    }

    // Add a coordinator
    public void addCoordinator(String coordinatorId, String name, String email, String phoneNumber, String clubId) {
        Map<String, Object> coordinator = new HashMap<>();
        coordinator.put("name", name);
        coordinator.put("email", email);
        coordinator.put("phoneNumber", phoneNumber);
        coordinator.put("clubId", clubId);

        db.collection("coordinators").document(coordinatorId).set(coordinator)
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Coordinator added successfully!");
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error adding coordinator: " + e.getMessage());
                });
    }

    // Get coordinator details
    public void getCoordinator(String coordinatorId) {
        db.collection("coordinators").document(coordinatorId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        System.out.println(documentSnapshot.getData());
                    } else {
                        System.out.println("No such coordinator!");
                    }
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error getting coordinator: " + e.getMessage());
                });
    }

    // Update coordinator details
    public void updateCoordinator(String coordinatorId, Map<String, Object> updates) {
        db.collection("coordinators").document(coordinatorId).update(updates)
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Coordinator updated successfully!");
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error updating coordinator: " + e.getMessage());
                });
    }

    // Delete coordinator
    public void deleteCoordinator(String coordinatorId) {
        db.collection("coordinators").document(coordinatorId).delete()
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Coordinator deleted successfully!");
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error deleting coordinator: " + e.getMessage());
                });
    }
}
