package com.example.schoolclubsmanagementsystem.activities.feedback;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.schoolclubsmanagementsystem.firestore.Feedback;
import com.example.schoolclubsmanagementsystem.firestore.Feedback.FirestoreCallback;

public class FeedbackViewModel extends AndroidViewModel {

    private final Feedback feedbackDb;

    public FeedbackViewModel(@NonNull Application application) {
        super(application);
        feedbackDb = new Feedback();
    }

    public interface FeedbackCallback {
        void onSuccess();
        void onFailure(String error);
    }

    public void submitFeedback(String feedback, FeedbackCallback callback) {
        feedbackDb.submitFeedback(feedback, new FirestoreCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                callback.onSuccess();
            }

            @Override
            public void onFailure(Exception e) {
                callback.onFailure(e.getMessage());
            }
        });
    }
}
