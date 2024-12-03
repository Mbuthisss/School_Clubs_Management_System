package com.example.schoolclubsmanagementsystem.activities.myClubsActivity;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.schoolclubsmanagementsystem.firestore.Clubs;
import com.example.schoolclubsmanagementsystem.firestore.Students;
import com.example.schoolclubsmanagementsystem.models.Club;
import com.example.schoolclubsmanagementsystem.user.Authentication;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyClubsViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Club>> clubs = new MutableLiveData<>();
    private final Students studentsDb;
    private final Authentication auth;

    public MyClubsViewModel(@NonNull Application application) {
        super(application);
        studentsDb = new Students();
        auth = new Authentication();
    }

    public LiveData<List<Club>> getClubs() {
        return clubs;
    }

    public void loadStudentClubs(FirebaseUser currentUser) {
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
                    Toast.makeText(getApplication(), "Failed to load clubs: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplication(), "Failed to load coordinated clubs: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void fetchClubsDetails(Set<String> clubIds) {
        List<Club> clubsList = new ArrayList<>();
        for (String clubId : clubIds) {
            // Assuming Clubs class has a method to fetch club details by club ID
            new Clubs().getClub(clubId, new Clubs.FirestoreCallback<Club>() {
                @Override
                public void onSuccess(Club club) {
                    if (club != null) {
                        clubsList.add(club);
                        clubs.setValue(clubsList);
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(getApplication(), "Failed to load club details: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
