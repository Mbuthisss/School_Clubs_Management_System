package com.example.schoolclubsmanagementsystem.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolclubsmanagementsystem.MainActivity;
import com.example.schoolclubsmanagementsystem.R;
import com.example.schoolclubsmanagementsystem.adapters.ClubListAdapter;
import com.example.schoolclubsmanagementsystem.firestore.Clubs;
import com.example.schoolclubsmanagementsystem.models.Club;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ClubListActivity extends MainActivity {

    private RecyclerView clubRecyclerView;
    private ClubListAdapter clubListAdapter;
    private List<Club> clubsList;
    private Clubs clubsDb;
    private DrawerLayout drawerLayout;
    private ImageView createClubButton;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_club_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        drawerLayout = findViewById(R.id.main);
        createClubButton = findViewById(R.id.create_club_button);
        buttonDrawerToggle = findViewById(R.id.buttonDrawerToggle);

        buttonDrawerToggle.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));

        createClubButton.setOnClickListener(view -> {
            Intent intent = new Intent(ClubListActivity.this, CreateClubActivity.class);
            startActivity(intent);
        });

        // Initialize RecyclerView
        clubRecyclerView = findViewById(R.id.club_recycler_view);
        clubRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize database and data list
        clubsDb = new Clubs();
        clubsList = new ArrayList<>();
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Set up adapter
        clubListAdapter = new ClubListAdapter(this, clubsList, currentUserId);
        clubRecyclerView.setAdapter(clubListAdapter);

        // Load clubs from Firestore
        loadClubs();
    }

    private void loadClubs() {
        clubsDb.getClubs(new Clubs.FirestoreCallback<List<Club>>() {
            @Override
            public void onSuccess(List<Club> result) {
                clubsList.clear();
                clubsList.addAll(result);
                clubListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception e) {
                // Handle failure
                e.printStackTrace();
            }
        });
    }
}
