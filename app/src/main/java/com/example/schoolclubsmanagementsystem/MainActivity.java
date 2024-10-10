package com.example.schoolclubsmanagementsystem;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.example.schoolclubsmanagementsystem.database.Authentication;

public class MainActivity extends AppCompatActivity {

    private Authentication auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialize Firebase
        FirebaseApp.initializeApp(this);

/*
        // Initialize Authentication
        auth = new Authentication();

        // Test sign-up with hardcoded credentials
        testSignUp();

        // Test sign-in with hardcoded credentials
        testSignIn();

     */
    }

/*
    // Test sign-up method with hardcoded values
    private void testSignUp() {
        String testEmail = "testuser@example.com";  // Hardcoded email
        String testPassword = "testpassword123";    // Hardcoded password

        // Call the signUp method from the Authentication class
        auth.signUp(testEmail, testPassword, this);
    }

    // Test sign-in method with hardcoded values
    private void testSignIn() {
        String testEmail = "testuser@example.com";  // Same hardcoded email as sign-up
        String testPassword = "testpassword123";    // Same hardcoded password as sign-up

        // Call the signIn method from the Authentication class
        auth.signIn(testEmail, testPassword, this);
    }

 */
}