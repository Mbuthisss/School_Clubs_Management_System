package com.example.schoolclubsmanagementsystem.firestore;

import com.example.schoolclubsmanagementsystem.models.Payment;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Payments {
    private FirebaseFirestore db;

    public Payments() {
        db = FirestoreInit.getFirestoreInstance();
    }

    public interface FirestoreCallback<T> {
        void onSuccess(T result);
        void onFailure(Exception e);
    }

    // Add a payment
    public void addPayment(String paymentId, String clubId, String userId, double amount, String date, String description, FirestoreCallback<Void> callback) {
        Map<String, Object> payment = new HashMap<>();
        payment.put("clubId", clubId);
        payment.put("userId", userId);
        payment.put("amount", amount);
        payment.put("date", date);
        payment.put("description", description);

        db.collection("payments").document(paymentId).set(payment)
                .addOnSuccessListener(aVoid -> {
                    callback.onSuccess(null);
                    System.out.println("Payment added successfully!");
                })
                .addOnFailureListener(e -> {
                    callback.onFailure(e);
                    System.out.println("Error adding payment: " + e.getMessage());
                });
    }

    // Get payment details
    public void getPayment(String paymentId, FirestoreCallback<Payment> callback) {
        db.collection("payments").document(paymentId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Payment payment = documentSnapshot.toObject(Payment.class);
                        callback.onSuccess(payment);
                    } else {
                        callback.onFailure(new Exception("No such payment!"));
                        System.out.println("No such payment!");
                    }
                })
                .addOnFailureListener(e -> {
                    callback.onFailure(e);
                    System.out.println("Error getting payment: " + e.getMessage());
                });
    }

    // Update payment
    public void updatePayment(String paymentId, Map<String, Object> updates, FirestoreCallback<Void> callback) {
        db.collection("payments").document(paymentId).update(updates)
                .addOnSuccessListener(aVoid -> {
                    callback.onSuccess(null);
                    System.out.println("Payment updated successfully!");
                })
                .addOnFailureListener(e -> {
                    callback.onFailure(e);
                    System.out.println("Error updating payment: " + e.getMessage());
                });
    }

    // Delete payment
    public void deletePayment(String paymentId, FirestoreCallback<Void> callback) {
        db.collection("payments").document(paymentId).delete()
                .addOnSuccessListener(aVoid -> {
                    callback.onSuccess(null);
                    System.out.println("Payment deleted successfully!");
                })
                .addOnFailureListener(e -> {
                    callback.onFailure(e);
                    System.out.println("Error deleting payment: " + e.getMessage());
                });
    }

    // Get payments for a specific club
    public void getPayments(String clubId, FirestoreCallback<List<Payment>> callback) {
        db.collection("payments").whereEqualTo("clubId", clubId).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Payment> paymentsList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Payment payment = document.toObject(Payment.class);
                        paymentsList.add(payment);
                    }
                    callback.onSuccess(paymentsList);
                })
                .addOnFailureListener(e -> {
                    callback.onFailure(e);
                    System.out.println("Error getting payments: " + e.getMessage());
                });
    }
}
