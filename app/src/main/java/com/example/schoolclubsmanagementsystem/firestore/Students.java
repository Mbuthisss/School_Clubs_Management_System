package com.example.schoolclubsmanagementsystem.firestore;

import android.util.Log;

import com.example.schoolclubsmanagementsystem.models.Student;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;
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

    // List all students
    public void listAllStudents(FirestoreCallback<List<Student>> callback) {
        db.collection("students").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Student> studentsList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Student student = document.toObject(Student.class);
                        studentsList.add(student);
                    }
                    callback.onSuccess(studentsList);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error listing students: " + e.getMessage());
                    callback.onFailure(e);
                });
    }

    // Search students by name
    public void searchStudentsByName(String name, FirestoreCallback<List<Student>> callback) {
        db.collection("students").whereEqualTo("name", name).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Student> studentsList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Student student = document.toObject(Student.class);
                        studentsList.add(student);
                    }
                    callback.onSuccess(studentsList);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error searching students by name: " + e.getMessage());
                    callback.onFailure(e);
                });
    }

    // Search students by email
    public void searchStudentsByEmail(String email, FirestoreCallback<List<Student>> callback) {
        db.collection("students").whereEqualTo("email", email).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Student> studentsList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Student student = document.toObject(Student.class);
                        studentsList.add(student);
                    }
                    callback.onSuccess(studentsList);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error searching students by email: " + e.getMessage());
                    callback.onFailure(e);
                });
    }

    // Batch update students
    public void batchUpdateStudents(Map<String, Map<String, Object>> updates, FirestoreCallback<Void> callback) {
        WriteBatch batch = db.batch();
        for (Map.Entry<String, Map<String, Object>> entry : updates.entrySet()) {
            String studentId = entry.getKey();
            Map<String, Object> updateFields = entry.getValue();
            DocumentReference studentRef = db.collection("students").document(studentId);
            batch.update(studentRef, updateFields);
        }

        batch.commit()
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Batch update successful!");
                    callback.onSuccess(null);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Batch update failed: " + e.getMessage());
                    callback.onFailure(e);
                });
    }

    // Get clubs a student has joined
    public void getStudentClubs(String studentId, FirestoreCallback<List<String>> callback) {
        db.collection("students").document(studentId).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                List<String> clubs = (List<String>) documentSnapshot.get("clubs");
                callback.onSuccess(clubs);
            } else {
                Log.d(TAG, "No such student!");
                callback.onFailure(new Exception("No such student"));
            }
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Error getting student clubs: " + e.getMessage());
            callback.onFailure(e);
        });
    }

    // Get clubs a student coordinates
    public void getCoordinatedClubs(String studentId, FirestoreCallback<List<String>> callback) {
        db.collection("clubs").whereEqualTo("coordinator", studentId).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<String> coordinatedClubIds = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        coordinatedClubIds.add(document.getId());
                    }
                    callback.onSuccess(coordinatedClubIds);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error getting coordinated clubs: " + e.getMessage());
                    callback.onFailure(e);
                });
    }

    // Get students enrolled in a club
    public void getEnrolledStudents(String clubId, FirestoreCallback<List<Student>> callback) {
        db.collection("students").whereArrayContains("clubs", clubId).get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<Student> studentsList = new ArrayList<>();
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                Student student = document.toObject(Student.class);
                studentsList.add(student);
            }
            callback.onSuccess(studentsList);
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Error getting enrolled students: " + e.getMessage());
            callback.onFailure(e);
        });
    }
}
