package com.example.schoolclubsmanagementsystem.activities.helpActivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class HelpViewModel extends AndroidViewModel {

    private final MutableLiveData<String> helpContent;

    public HelpViewModel(@NonNull Application application) {
        super(application);
        helpContent = new MutableLiveData<>();
        loadHelpContent();
    }

    public LiveData<String> getHelpContent() {
        return helpContent;
    }

    private void loadHelpContent() {
        // Load help content here (hardcoded for simplicity, but can be from a database or API)
        String content = "Welcome to the Help page! Here you will find answers to frequently asked questions and guides to help you navigate the app.\n\n"
                + "Frequently Asked Questions\n\n"
                + "1. How do I join a club?\n"
                + "To join a club, go to the Clubs List page, select a club you are interested in, and click on the 'Join' button.\n\n"
                + "2. How do I add an event?\n"
                + "As a club coordinator, you can add an event by navigating to the Events page and clicking on the floating action button (FAB) at the bottom right corner.\n\n"
                + "3. How do I view my enrolled clubs?\n"
                + "Go to the My Clubs page to view all the clubs you are currently enrolled in.\n\n"
                + "4. How do I make a payment?\n"
                + "To make a payment, navigate to the Payments page, select the club and the event for which you want to make a payment, and follow the instructions.\n\n"
                + "Still have questions?\n"
                + "If you have any other questions or need further assistance, please contact our support team at support@example.com.";
        helpContent.setValue(content);
    }
}
