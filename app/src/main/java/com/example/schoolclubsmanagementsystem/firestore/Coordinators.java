package com.example.schoolclubsmanagementsystem.firestore;

import android.util.Log;

import com.example.schoolclubsmanagementsystem.models.Coordinator;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class Coordinators {
    private static final String TAG = "Coordinators";
    private FirebaseFirestore db;

    public Coordinators() {
        db = FirestoreInit.getFirestoreInstance();
    }

    public interface FirestoreCallback<T> {
        void onSuccess(T result);

        void onFailure(Exception e);
    }

    // Add a coordinator
    public void addCoordinator(Coordinator coordinator, FirestoreCallback<Void> callback) {
        db.collection("coordinators").document(coordinator.getCoordinatorId()).set(coordinator)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Coordinator added successfully!");
                    callback.onSuccess(null);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error adding coordinator: " + e.getMessage());
                    callback.onFailure(e);
                });
    }

    // Get coordinator details
    public void getCoordinator(String coordinatorId, FirestoreCallback<Coordinator> callback) {
        db.collection("coordinators").document(coordinatorId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Coordinator coordinator = documentSnapshot.toObject(Coordinator.class);
                        callback.onSuccess(coordinator);
                    } else {
                        Log.d(TAG, "No such coordinator!");
                        callback.onFailure(new Exception("No such coordinator"));
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error getting coordinator: " + e.getMessage());
                    callback.onFailure(e);
                });
    }

    // Update coordinator details
    public void updateCoordinator(String coordinatorId, Map<String, Object> updates, FirestoreCallback<Void> callback) {
        db.collection("coordinators").document(coordinatorId).update(updates)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Coordinator updated successfully!")
                    ;
                    callback.onSuccess(null);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error updating coordinator: " + e.getMessage());
                    callback.onFailure(e);
                });
    }

    // Delete coordinator
    public void deleteCoordinator(String coordinatorId, FirestoreCallback<Void> callback) {
        db.collection("coordinators").document(coordinatorId).delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Coordinator deleted successfully!");
                    callback.onSuccess(null);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error deleting coordinator: " + e.getMessage());
                    callback.onFailure(e);
                });
    }
}
