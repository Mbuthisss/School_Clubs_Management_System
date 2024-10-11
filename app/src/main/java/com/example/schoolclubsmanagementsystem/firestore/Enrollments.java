package com.example.schoolclubsmanagementsystem.firestore;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class Enrollments {
    private FirebaseFirestore db;

    public Enrollments() {
        db = FirestoreInit.getFirestoreInstance();
    }

    // Add an enrollment
    public void enrollStudent(String enrollmentId, String studentId, String clubId, String status) {
        Map<String, Object> enrollment = new HashMap<>();
        enrollment.put("studentId", studentId);
        enrollment.put("clubId", clubId);
        enrollment.put("status", status);

        db.collection("enrollments").document(enrollmentId).set(enrollment)
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Enrollment added successfully!");
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error adding enrollment: " + e.getMessage());
                });
    }

    // Get enrollment details
    public void getEnrollment(String enrollmentId) {
        db.collection("enrollments").document(enrollmentId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        System.out.println(documentSnapshot.getData());
                    } else {
                        System.out.println("No such enrollment!");
                    }
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error getting enrollment: " + e.getMessage());
                });
    }

    // Update enrollment
    public void updateEnrollment(String enrollmentId, Map<String, Object> updates) {
        db.collection("enrollments").document(enrollmentId).update(updates)
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Enrollment updated successfully!");
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error updating enrollment: " + e.getMessage());
                });
    }

    // Delete enrollment
    public void deleteEnrollment(String enrollmentId) {
        db.collection("enrollments").document(enrollmentId).delete()
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Enrollment deleted successfully!");
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error deleting enrollment: " + e.getMessage());
                });
    }
}
