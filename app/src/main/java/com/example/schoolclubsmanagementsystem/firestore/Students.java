package com.example.schoolclubsmanagementsystem.firestore;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class Students {
    private FirebaseFirestore db;

    public Students() {
        db = FirestoreInit.getFirestoreInstance();
    }

    // Add a new student
    public void addStudent(String studentId, String name, String email, String phone, String department) {
        Map<String, Object> student = new HashMap<>();
        student.put("studentId", studentId);
        student.put("name", name);
        student.put("email", email);
        student.put("phone", phone);
        student.put("department", department);

        db.collection("students").document(studentId).set(student)
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Student added successfully!");
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error adding student: " + e.getMessage());
                });
    }

    // Get student details
    public void getStudent(String studentId) {
        db.collection("students").document(studentId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        System.out.println(documentSnapshot.getData());
                    } else {
                        System.out.println("No such student!");
                    }
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error getting student: " + e.getMessage());
                });
    }

    // Update student details
    public void updateStudent(String studentId, Map<String, Object> updates) {
        db.collection("students").document(studentId).update(updates)
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Student updated successfully!");
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error updating student: " + e.getMessage());
                });
    }

    // Delete student
    public void deleteStudent(String studentId) {
        db.collection("students").document(studentId).delete()
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Student deleted successfully!");
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error deleting student: " + e.getMessage());
                });
    }
}
