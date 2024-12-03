package com.example.schoolclubsmanagementsystem.activities.homeActivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class HomeViewModel extends AndroidViewModel {

    private final MutableLiveData<String> appDescription;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        appDescription = new MutableLiveData<>();
        loadAppDescription();
    }

    public LiveData<String> getAppDescription() {
        return appDescription;
    }

    private void loadAppDescription() {
        // Generate app description
        String description = "Welcome to the School Clubs Management System! " +
                "Our app helps students and faculty manage school clubs efficiently. " +
                "With features for creating and joining clubs, scheduling events, managing announcements, " +
                "and handling club enrollments, our application ensures that every aspect of club management is streamlined. " +
                "Join us in enhancing your school club experience!";
        appDescription.setValue(description);
    }
}
