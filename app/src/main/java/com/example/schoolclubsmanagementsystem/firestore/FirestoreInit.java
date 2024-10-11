package com.example.schoolclubsmanagementsystem.firestore;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class FirestoreInit {

    private static FirebaseFirestore firestoreInstance;

    // Private constructor to prevent instantiation
    private FirestoreInit() {
    }

    // Method to initialize and return the Firestore instance
    public static FirebaseFirestore getFirestoreInstance() {
        if (firestoreInstance == null) {
            // Initialize Firestore
            firestoreInstance = FirebaseFirestore.getInstance();

            // Set Firestore settings, like enabling offline persistence
            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true) // Enable offline data
                    .build();

            firestoreInstance.setFirestoreSettings(settings);
        }
        return firestoreInstance;
    }
}