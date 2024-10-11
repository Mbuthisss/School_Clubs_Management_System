package com.example.schoolclubsmanagementsystem.user;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;

public class Authentication {
    private FirebaseAuth mAuth;

    public Authentication() {
        mAuth = FirebaseAuth.getInstance();
    }

    // Method for user sign-up
    public void signUp(String email, String password, Activity activity) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("Auth", "createUserWithEmail:success");
                            Toast.makeText(activity, "Sign up successful!", Toast.LENGTH_SHORT).show();
                        } else {
                            // Sign up failed
                            Log.w("Auth", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(activity, "Sign up failed: " + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    // Method for user login
    public void signIn(String email, String password, Activity activity) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("Auth", "signInWithEmail:success");
                            Toast.makeText(activity, "Login successful!", Toast.LENGTH_SHORT).show();
                        } else {
                            // Sign in failed
                            Log.w("Auth", "signInWithEmail:failure", task.getException());
                            Toast.makeText(activity, "Login failed: " + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    // Method for getting current user
    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    // Method for user sign-out
    public void signOut() {
        mAuth.signOut();
    }
}
