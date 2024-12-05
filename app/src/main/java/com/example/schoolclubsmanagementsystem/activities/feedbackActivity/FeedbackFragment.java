package com.example.schoolclubsmanagementsystem.activities.feedbackActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.schoolclubsmanagementsystem.R;

public class FeedbackFragment extends Fragment {

    private EditText feedbackEditText;
    private Button submitFeedbackButton;
    private com.example.schoolclubsmanagementsystem.activities.feedback.FeedbackViewModel feedbackViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_feedback, container, false);

        feedbackEditText = root.findViewById(R.id.edit_text_feedback);
        submitFeedbackButton = root.findViewById(R.id.button_submit_feedback);

        feedbackViewModel = new ViewModelProvider(this).get(com.example.schoolclubsmanagementsystem.activities.feedback.FeedbackViewModel.class);

        submitFeedbackButton.setOnClickListener(v -> submitFeedback());

        return root;
    }

    private void submitFeedback() {
        String feedback = feedbackEditText.getText().toString().trim();
        if (feedback.isEmpty()) {
            Toast.makeText(getContext(), "Please enter your feedback", Toast.LENGTH_SHORT).show();
            return;
        }

        feedbackViewModel.submitFeedback(feedback, new com.example.schoolclubsmanagementsystem.activities.feedback.FeedbackViewModel.FeedbackCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(getContext(), "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
                feedbackEditText.setText("");
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(getContext(), "Failed to submit feedback: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
