package com.example.schoolclubsmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.schoolclubsmanagementsystem.activities.LoginActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.example.schoolclubsmanagementsystem.user.Authentication;

public class MainActivity extends AppCompatActivity {

    private Authentication auth;
    DrawerLayout drawerLayout;
    ImageButton buttonDrawerToggle;
    NavigationView navigationView;

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

        drawerLayout = findViewById(R.id.main);
        buttonDrawerToggle = findViewById(R.id.buttonDrawerToggle);
        navigationView = findViewById(R.id.navigationView);

        buttonDrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.home){
                    Toast.makeText(MainActivity.this, "Home Clicked", Toast.LENGTH_SHORT).show();

                } else if (itemId == R.id.clubs_list) {
                    Toast.makeText(MainActivity.this, "Clubs List Clicked", Toast.LENGTH_SHORT).show();

                } else if (itemId == R.id.my_clubs) {
                    Toast.makeText(MainActivity.this, "My Clubs Clicked", Toast.LENGTH_SHORT).show();

                } else if (itemId == R.id.profile) {
                    Toast.makeText(MainActivity.this, "Profile Clicked", Toast.LENGTH_SHORT).show();

                } else if (itemId == R.id.member_app) {
                    Toast.makeText(MainActivity.this, "Member Applications Clicked", Toast.LENGTH_SHORT).show();

                } else if (itemId == R.id.nots) {
                    Toast.makeText(MainActivity.this, "Notifications Clicked", Toast.LENGTH_SHORT).show();

                } else if (itemId == R.id.payment) {
                    Toast.makeText(MainActivity.this, "Payments Clicked", Toast.LENGTH_SHORT).show();

                } else if (itemId == R.id.help) {
                    Toast.makeText(MainActivity.this, "Help Clicked", Toast.LENGTH_SHORT).show();

                } else if (itemId == R.id.feedback) {
                    Toast.makeText(MainActivity.this, "Feedback Clicked", Toast.LENGTH_SHORT).show();

                } else if (itemId == R.id.logout) {
                    Toast.makeText(MainActivity.this, "Logout Clicked", Toast.LENGTH_SHORT).show();
                }

                drawerLayout.close();

                return false;
            }
        });


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