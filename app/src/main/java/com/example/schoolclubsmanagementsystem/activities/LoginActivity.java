package com.example.schoolclubsmanagementsystem.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolclubsmanagementsystem.MainActivity;
import com.example.schoolclubsmanagementsystem.R;
import com.example.schoolclubsmanagementsystem.user.Authentication;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput, passwordInput;
    private Button loginButton, signUpButton;
    private Authentication auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Hooks
        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_btn);
        signUpButton = findViewById(R.id.sign_up_btn);
        auth = new Authentication();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp1.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser() {
        String email = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signIn(email, password, this, new Authentication.AuthCallback() {
            @Override
            public void onSuccess() {
                // Sign in success, update UI with the signed-in user's information
                FirebaseUser user = auth.getCurrentUser();
                if (user != null) {
                    // Navigate to MainActivity
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,
                            new Pair<View, String>(loginButton, "transition_login_btn"));
                    startActivity(intent, options.toBundle());
                    finish();
                }
            }

            @Override
            public void onFailure(Exception e) {
                // If sign in fails, display a message to the user.
                Toast.makeText(LoginActivity.this, "Authentication failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
