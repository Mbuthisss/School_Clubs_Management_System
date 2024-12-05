package com.example.schoolclubsmanagementsystem.activities.clubDashboardUi.enrollments;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.schoolclubsmanagementsystem.firestore.Clubs;
import com.example.schoolclubsmanagementsystem.models.Student;
import com.example.schoolclubsmanagementsystem.firestore.Students;

import java.util.List;

public class EnrollmentsViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Student>> enrolledStudents = new MutableLiveData<>();
    private final Students studentsDb;

    public EnrollmentsViewModel(@NonNull Application application) {
        super(application);
        studentsDb = new Students();
    }

    public LiveData<List<Student>> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void loadEnrolledStudents(String clubId) {
        if (clubId != null && !clubId.isEmpty()) {
            studentsDb.getEnrolledStudents(clubId, new Students.FirestoreCallback<List<Student>>() {
                @Override
                public void onSuccess(List<Student> students) {
                    enrolledStudents.setValue(students);
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(getApplication(), "Failed to load enrolled students: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
