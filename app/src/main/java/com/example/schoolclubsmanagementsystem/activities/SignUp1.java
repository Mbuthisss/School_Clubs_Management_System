package com.example.schoolclubsmanagementsystem.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.schoolclubsmanagementsystem.R;
import com.example.schoolclubsmanagementsystem.user.Authentication;
import com.google.android.material.textfield.TextInputEditText;

public class SignUp1 extends AppCompatActivity {

    private ImageView backBtn;
    private Button next, login;
    private TextView titleText;
    private TextInputEditText emailInput, passwordInput;
    private Authentication auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Hooks
        backBtn = findViewById(R.id.signup_back_button);
        next = findViewById(R.id.signup_next_button);
        login = findViewById(R.id.signup_login_button);
        titleText = findViewById(R.id.signup_title_text);
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);

        auth = new Authentication();

        login.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });
    }

    public void callNextSignupScreen(View view) {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signUp(email, password, this, new Authentication.AuthCallback() {
            @Override
            public void onSuccess() {
                Intent intent = new Intent(getApplicationContext(), SignUp2.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);

                // Add Transition
                Pair[] pairs = new Pair[4];
                pairs[0] = new Pair<View, String>(backBtn, "transition_back_arrow_btn");
                pairs[1] = new Pair<View, String>(next, "transition_next_btn");
                pairs[2] = new Pair<View, String>(login, "transition_login_btn");
                pairs[3] = new Pair<View, String>(titleText, "transition_title_text");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp1.this, pairs);
                startActivity(intent, options.toBundle());
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(SignUp1.this, "Sign up failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
