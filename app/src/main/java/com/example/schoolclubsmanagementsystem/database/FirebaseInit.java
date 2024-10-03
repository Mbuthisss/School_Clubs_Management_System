package com.example.schoolclubsmanagementsystem.database;

import android.content.Context;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseInit {

    // Initialize FirebaseApp and Firebase services
    public static void initializeFirebase(Context context) {

        // If Firebase is not initialized already
        if (FirebaseApp.getApps(context).isEmpty()) {
            FirebaseApp.initializeApp(context);
        }
    }

    // Optional: Get a Firestore instance if needed
    public static FirebaseFirestore getFirestoreInstance() {
        return FirebaseFirestore.getInstance();
    }
}
