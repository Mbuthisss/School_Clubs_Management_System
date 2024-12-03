package com.example.schoolclubsmanagementsystem.activities.clubDashboardUi.announcements;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolclubsmanagementsystem.firestore.Announcements;
import com.example.schoolclubsmanagementsystem.models.Announcement;

import java.util.List;

public class AnnouncementsViewModel extends ViewModel {
    private final MutableLiveData<List<Announcement>> announcements;

    public AnnouncementsViewModel() {
        announcements = new MutableLiveData<>();
    }

    public LiveData<List<Announcement>> getAnnouncements() {
        return announcements;
    }

    public void loadAnnouncements(String clubId) {
        Announcements announcementsDb = new Announcements();
        announcementsDb.listAllAnnouncements(clubId, new Announcements.FirestoreCallback<List<Announcement>>() {
            @Override
            public void onSuccess(List<Announcement> result) {
                announcements.setValue(result);
            }

            @Override
            public void onFailure(Exception e) {
                // Handle error if needed
            }
        });
    }

    public void addAnnouncement(Announcement announcement) {
        Announcements announcementsDb = new Announcements();
        announcementsDb.addAnnouncement(announcement, new Announcements.FirestoreCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                // Refresh the list of announcements after adding
                loadAnnouncements(announcement.getClubId());
            }

            @Override
            public void onFailure(Exception e) {
                // Handle error if needed
            }
        });
    }
}
