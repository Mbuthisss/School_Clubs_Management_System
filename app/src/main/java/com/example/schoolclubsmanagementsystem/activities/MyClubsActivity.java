package com.example.schoolclubsmanagementsystem.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolclubsmanagementsystem.R;
import com.example.schoolclubsmanagementsystem.adapters.MyClubsAdapter;
import com.example.schoolclubsmanagementsystem.firestore.Clubs;
import com.example.schoolclubsmanagementsystem.firestore.Students;
import com.example.schoolclubsmanagementsystem.models.Club;
import com.example.schoolclubsmanagementsystem.user.Authentication;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyClubsActivity extends AppCompatActivity {

    private RecyclerView clubsRecyclerView;
    private MyClubsAdapter myClubsAdapter;
    private List<Club> clubsList;
    private Students studentsDb;
    private Authentication auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_clubs); // Use activity_my_clubs.xml

        // Initialize instances
        auth = new Authentication();
        studentsDb = new Students();
        FirebaseUser currentUser = auth.getCurrentUser();

        // Initialize RecyclerView
        clubsRecyclerView = findViewById(R.id.clubs_recycler_view);
        clubsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        clubsList = new ArrayList<>();
        myClubsAdapter = new MyClubsAdapter(this, clubsList);
        clubsRecyclerView.setAdapter(myClubsAdapter);

        // Load clubs the student has joined and coordinates
        loadStudentClubs(currentUser);
    }

    private void loadStudentClubs(FirebaseUser currentUser) {
        if (currentUser != null) {
            Set<String> allClubIds = new HashSet<>();

            studentsDb.getStudentClubs(currentUser.getUid(), new Students.FirestoreCallback<List<String>>() {
                @Override
                public void onSuccess(List<String> clubIds) {
                    if (clubIds != null) {
                        allClubIds.addAll(clubIds);
                        fetchClubsDetails(allClubIds);
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(MyClubsActivity.this, "Failed to load clubs: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            studentsDb.getCoordinatedClubs(currentUser.getUid(), new Students.FirestoreCallback<List<String>>() {
                @Override
                public void onSuccess(List<String> coordinatedClubIds) {
                    if (coordinatedClubIds != null) {
                        allClubIds.addAll(coordinatedClubIds);
                        fetchClubsDetails(allClubIds);
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(MyClubsActivity.this, "Failed to load coordinated clubs: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void fetchClubsDetails(Set<String> clubIds) {
        clubsList.clear();
        for (String clubId : clubIds) {
            // Assuming Clubs class has a method to fetch club details by club ID
            new Clubs().getClub(clubId, new Clubs.FirestoreCallback<Club>() {
                @Override
                public void onSuccess(Club club) {
                    if (club != null) {
                        clubsList.add(club);
                        myClubsAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(MyClubsActivity.this, "Failed to load club details: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
