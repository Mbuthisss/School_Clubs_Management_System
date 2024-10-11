package com.example.schoolclubsmanagementsystem.firestore;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class Payments {
    private FirebaseFirestore db;

    public Payments() {
        db = FirestoreInit.getFirestoreInstance();
    }

    // Add a payment
    public void addPayment(String paymentId, String studentId, String clubId, double paymentAmount, String paymentMethod) {
        Map<String, Object> payment = new HashMap<>();
        payment.put("studentId", studentId);
        payment.put("clubId", clubId);
        payment.put("paymentAmount", paymentAmount);
        payment.put("paymentMethod", paymentMethod);

        db.collection("payments").document(paymentId).set(payment)
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Payment added successfully!");
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error adding payment: " + e.getMessage());
                });
    }

    // Get payment details
    public void getPayment(String paymentId) {
        db.collection("payments").document(paymentId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        System.out.println(documentSnapshot.getData());
                    } else {
                        System.out.println("No such payment!");
                    }
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error getting payment: " + e.getMessage());
                });
    }

    // Update payment details
    public void updatePayment(String paymentId, Map<String, Object> updates) {
        db.collection("payments").document(paymentId).update(updates)
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Payment updated successfully!");
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error updating payment: " + e.getMessage());
                });
    }

    // Delete payment
    public void deletePayment(String paymentId) {
        db.collection("payments").document(paymentId).delete()
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Payment deleted successfully!");
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error deleting payment: " + e.getMessage());
                });
    }
}
