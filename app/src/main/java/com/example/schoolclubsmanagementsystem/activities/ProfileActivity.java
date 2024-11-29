package com.example.schoolclubsmanagementsystem.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolclubsmanagementsystem.R;
import com.example.schoolclubsmanagementsystem.firestore.Students;
import com.example.schoolclubsmanagementsystem.models.Student;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private TextInputEditText usernameEditText, emailEditText, nameEditText, dobEditText, phoneEditText;
    private Button editButton, saveButton;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private Students studentsDb; // Reference to Students class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize Firebase Auth and Students class
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        studentsDb = new Students();

        // Hooks
        usernameEditText = findViewById(R.id.username_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        nameEditText = findViewById(R.id.name_edit_text);
        dobEditText = findViewById(R.id.dob_edit_text);
        phoneEditText = findViewById(R.id.phone_edit_text);
        editButton = findViewById(R.id.edit_button);
        saveButton = findViewById(R.id.save_button);

        // Load user information
        loadUserInfo();

        editButton.setOnClickListener(v -> setEditMode(true));
        saveButton.setOnClickListener(v -> updateUserInfo());
    }

    private void loadUserInfo() {
        if (currentUser != null) {
            String studentId = currentUser.getUid();

            // Fetch user info using the Students class
            studentsDb.getStudent(studentId, new Students.FirestoreCallback<Student>() {
                @Override
                public void onSuccess(Student student) {
                    if (student != null) {
                        usernameEditText.setText(student.getUsername());
                        emailEditText.setText(student.getEmail());
                        nameEditText.setText(student.getName());
                        dobEditText.setText(student.getDob());
                        phoneEditText.setText(student.getPhone());
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(ProfileActivity.this, "Failed to load user info: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
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
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentUser != null) {
            String studentId = currentUser.getUid();

            // Prepare fields to update
            Map<String, Object> updates = new HashMap<>();
            updates.put("username", newUsername);
            updates.put("email", newEmail);
            updates.put("name", newName);
            updates.put("dob", newDob);
            updates.put("phone", newPhone);

            // Use Students class to update student info
            studentsDb.updateStudent(studentId, updates, new Students.FirestoreCallback<Void>() {
                @Override
                public void onSuccess(Void result) {
                    Toast.makeText(ProfileActivity.this, "User info updated", Toast.LENGTH_SHORT).show();
                    setEditMode(false);
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(ProfileActivity.this, "Failed to update user info: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
