package com.example.schoolclubsmanagementsystem.activities;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.schoolclubsmanagementsystem.R;

public class ClubDashboardActivity extends AppCompatActivity {

    private String clubId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_dashboard);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Retrieve the club ID from the intent
        clubId = getIntent().getStringExtra("clubId");

        // Check if clubId is null
        if (clubId == null) {
            Toast.makeText(this, "Club ID is missing", Toast.LENGTH_SHORT).show();
            return;
        }

        // Pass clubId to fragments if needed
        Bundle bundle = new Bundle();
        bundle.putString("clubId", clubId);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_announcements, R.id.navigation_enrollments, R.id.navigation_events, R.id.navigation_payments)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_club_dashboard);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        // Ensure clubId is passed to all fragments
        navController.navigate(R.id.navigation_announcements, bundle);
    }
}
