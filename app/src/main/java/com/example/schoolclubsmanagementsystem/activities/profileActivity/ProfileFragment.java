package com.example.schoolclubsmanagementsystem.activities.profileActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.schoolclubsmanagementsystem.R;
import com.example.schoolclubsmanagementsystem.models.Student;
import com.google.android.material.textfield.TextInputEditText;

public class ProfileFragment extends Fragment {

    private TextInputEditText usernameEditText, emailEditText, nameEditText, dobEditText, phoneEditText;
    private Button editButton, saveButton;
    private ProfileViewModel profileViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize ViewModel
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        // Hooks
        usernameEditText = root.findViewById(R.id.username_edit_text);
        emailEditText = root.findViewById(R.id.email_edit_text);
        nameEditText = root.findViewById(R.id.name_edit_text);
        dobEditText = root.findViewById(R.id.dob_edit_text);
        phoneEditText = root.findViewById(R.id.phone_edit_text);
        editButton = root.findViewById(R.id.edit_button);
        saveButton = root.findViewById(R.id.save_button);

        // Load user information
        loadUserInfo();

        editButton.setOnClickListener(v -> setEditMode(true));
        saveButton.setOnClickListener(v -> updateUserInfo());

        return root;
    }

    private void loadUserInfo() {
        profileViewModel.getUserInfo().observe(getViewLifecycleOwner(), student -> {
            if (student != null) {
                usernameEditText.setText(student.getUsername());
                emailEditText.setText(student.getEmail());
                nameEditText.setText(student.getName());
                dobEditText.setText(student.getDob());
                phoneEditText.setText(student.getPhone());
            }
        });

        profileViewModel.loadUserInfo();
    }

    private void setEditMode(boolean enabled) {
        usernameEditText.setEnabled(enabled);
        emailEditText.setEnabled(enabled);
        nameEditText.setEnabled(enabled);
        dobEditText.setEnabled(enabled);
        phoneEditText.setEnabled(enabled);
        editButton.setVisibility(enabled ? View.GONE : View.VISIBLE);
        saveButton.setVisibility(enabled ? View.VISIBLE : View.GONE);
    }

    private void updateUserInfo() {
        String newUsername = usernameEditText.getText().toString().trim();
        String newEmail = emailEditText.getText().toString().trim();
        String newName = nameEditText.getText().toString().trim();
        String newDob = dobEditText.getText().toString().trim();
        String newPhone = phoneEditText.getText().toString().trim();

        if (newUsername.isEmpty() || newEmail.isEmpty() || newName.isEmpty() || newDob.isEmpty() || newPhone.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        profileViewModel.updateUserInfo(newUsername, newEmail, newName, newDob, newPhone);
    }
}
