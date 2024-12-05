package com.example.schoolclubsmanagementsystem.firestore;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Feedback {
    private FirebaseFirestore db;

    public Feedback() {
        db = FirestoreInit.getFirestoreInstance();
    }

    public interface FirestoreCallback<T> {
        void onSuccess(T result);
        void onFailure(Exception e);
    }

    public void submitFeedback(String feedback, FirestoreCallback<Void> callback) {
        Map<String, Object> feedbackData = new HashMap<>();
        feedbackData.put("feedback", feedback);

        db.collection("feedback").add(feedbackData)
                .addOnSuccessListener(documentReference -> {
                    callback.onSuccess(null);
                    System.out.println("Feedback submitted successfully!");
                })
                .addOnFailureListener(e -> {
                    callback.onFailure(e);
                    System.out.println("Error submitting feedback: " + e.getMessage());
                });
    }
}
