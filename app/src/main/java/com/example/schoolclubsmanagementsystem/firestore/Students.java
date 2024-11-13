package com.example.schoolclubsmanagementsystem.firestore;

import android.util.Log;

import com.example.schoolclubsmanagementsystem.models.Student;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class Students {
    private static final String TAG = "Students";
    private FirebaseFirestore db;

    public Students() {
        db = FirestoreInit.getFirestoreInstance();
    }

    public interface FirestoreCallback<T> {
        void onSuccess(T result);

        void onFailure(Exception e);
    }

    // Add a new student
    public void addStudent(Student student, FirestoreCallback<Void> callback) {

        db.collection("students").document(student.getStudentId()).set(student)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Student added successfully!");
                    callback.onSuccess(null);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error adding student: " + e.getMessage());
                    callback.onFailure(e);
                });
    }

    // Get student details
    public void getStudent(String studentId, FirestoreCallback<Student> callback) {
        db.collection("students").document(studentId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Student student = documentSnapshot.toObject(Student.class);
                        callback.onSuccess(student);
                    } else {
                        Log.d(TAG, "No such student!");
                        callback.onFailure(new Exception("No such student"));
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error getting student: " + e.getMessage());
                    callback.onFailure(e);
                });
    }

    // Update student details
    public void updateStudent(String studentId, Map<String, Object> updates, FirestoreCallback<Void> callback) {
        db.collection("students").document(studentId).update(updates)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Student updated successfully!");
                    callback.onSuccess(null);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error updating student: " + e.getMessage());
                    callback.onFailure(e);
                });
    }

    // Delete student
    public void deleteStudent(String studentId, FirestoreCallback<Void> callback) {
        db.collection("students").document(studentId).delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Student deleted successfully!");
                    callback.onSuccess(null);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error deleting student: " + e.getMessage());
                    callback.onFailure(e);
                });
    }
}
