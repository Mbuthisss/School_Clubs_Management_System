package com.example.schoolclubsmanagementsystem.activities.clubListActivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.schoolclubsmanagementsystem.firestore.Clubs;
import com.example.schoolclubsmanagementsystem.models.Club;

import java.util.List;

public class ClubListViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Club>> clubs;
    private final Clubs clubsDb;

    public ClubListViewModel(@NonNull Application application) {
        super(application);
        clubs = new MutableLiveData<>();
        clubsDb = new Clubs();
    }

    public LiveData<List<Club>> getClubs() {
        return clubs;
    }

    public void loadClubs() {
        clubsDb.getClubs(new Clubs.FirestoreCallback<List<Club>>() {
            @Override
            public void onSuccess(List<Club> result) {
                clubs.setValue(result);
            }

            @Override
            public void onFailure(Exception e) {
                // Handle failure
                e.printStackTrace();
            }
        });
    }
}
