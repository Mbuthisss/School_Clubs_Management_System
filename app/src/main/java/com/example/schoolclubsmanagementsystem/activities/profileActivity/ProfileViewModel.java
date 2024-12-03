package com.example.schoolclubsmanagementsystem.activities.profileActivity;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.schoolclubsmanagementsystem.firestore.Students;
import com.example.schoolclubsmanagementsystem.models.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class ProfileViewModel extends AndroidViewModel {

    private final MutableLiveData<Student> userInfo = new MutableLiveData<>();
    private final Students studentsDb;
    private final FirebaseUser currentUser;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        studentsDb = new Students();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
    }

    public LiveData<Student> getUserInfo() {
        return userInfo;
    }

    public void loadUserInfo() {
        if (currentUser != null) {
            String studentId = currentUser.getUid();

            studentsDb.getStudent(studentId, new Students.FirestoreCallback<Student>() {
                @Override
                public void onSuccess(Student student) {
                    userInfo.setValue(student);
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(getApplication(), "Failed to load user info: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void updateUserInfo(String newUsername, String newEmail, String newName, String newDob, String newPhone) {
        if (currentUser != null) {
            String studentId = currentUser.getUid();

            // Prepare fields to update
            Map<String, Object> updates = new HashMap<>();
            updates.put("username", newUsername);
            updates.put("email", newEmail);
            updates.put("name", newName);
            updates.put("dob", newDob);
            updates.put("phone", newPhone);

            studentsDb.updateStudent(studentId, updates, new Students.FirestoreCallback<Void>() {
                @Override
                public void onSuccess(Void result) {
                    loadUserInfo();
                    Toast.makeText(getApplication(), "User info updated", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(getApplication(), "Failed to update user info: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
