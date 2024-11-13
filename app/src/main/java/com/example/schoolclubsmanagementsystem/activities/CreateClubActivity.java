package com.example.schoolclubsmanagementsystem.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.schoolclubsmanagementsystem.R;
import com.example.schoolclubsmanagementsystem.firestore.Clubs;
import com.example.schoolclubsmanagementsystem.models.Club;
import com.example.schoolclubsmanagementsystem.user.Authentication;

public class CreateClubActivity extends AppCompatActivity {

    private EditText clubNameEditText, clubDescriptionEditText, clubCapacityEditText;
    private Button saveClubButton;
    private Clubs clubsDb;
    private Authentication authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_club);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        clubNameEditText = findViewById(R.id.club_name);
        clubDescriptionEditText = findViewById(R.id.club_description);
        clubCapacityEditText = findViewById(R.id.club_capacity);
        saveClubButton = findViewById(R.id.save_club_button);

        clubsDb = new Clubs();
        authentication = new Authentication();

        saveClubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createClub();
            }
        });
    }

    private void createClub() {
        String clubName = clubNameEditText.getText().toString();
        String clubDescription = clubDescriptionEditText.getText().toString();
        int clubCapacity = Integer.parseInt(clubCapacityEditText.getText().toString());

        // Get current user's ID
        String currentStudentId = authentication.getCurrentUser().getUid();

        // Create new club
        Club newClub = new Club();
        newClub.setClubId(clubsDb.getDb().collection("clubs").document().getId()); // Auto-generate ID
        newClub.setName(clubName);
        newClub.setDescription(clubDescription);
        newClub.setCapacity(clubCapacity);
        newClub.setCoordinator(currentStudentId);

        // Add club to Firestore
        clubsDb.addClub(newClub, new Clubs.FirestoreCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                Toast.makeText(CreateClubActivity.this, "Club created successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(CreateClubActivity.this, "Error creating club: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

