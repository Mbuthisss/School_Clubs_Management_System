package com.example.schoolclubsmanagementsystem.firestore;

import android.util.Log;

import com.example.schoolclubsmanagementsystem.models.Club;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Clubs {
    private static final String TAG = "Clubs";
    private FirebaseFirestore db;

    public Clubs() {
        db = FirestoreInit.getFirestoreInstance();
    }

    public interface FirestoreCallback<T> {
        void onSuccess(T result);
        void onFailure(Exception e);
    }

    public FirebaseFirestore getDb() {
        return db;
    }

    // Add a new club
    public void addClub(Club club, FirestoreCallback<Void> callback) {
        db.collection("clubs").document(club.getClubId()).set(club)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Club added successfully!");
                    callback.onSuccess(null);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error adding club: " + e.getMessage());
                    callback.onFailure(e);
                });
    }

    // Get club details
    public void getClub(String clubId, FirestoreCallback<Club> callback) {
        db.collection("clubs").document(clubId).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Club club = documentSnapshot.toObject(Club.class);
                callback.onSuccess(club);
            } else {
                Log.d(TAG, "No such club!");
                callback.onFailure(new Exception("No such club"));
            }
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Error getting club: " + e.getMessage());
            callback.onFailure(e);
        });
    }

    // Get clubs details
    public void getClubs(final FirestoreCallback<List<Club>> callback) {
        db.collection("clubs").get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<Club> clubs = new ArrayList<>();
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                Club club = document.toObject(Club.class);
                clubs.add(club);
            }
            callback.onSuccess(clubs);
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Error getting clubs: " + e.getMessage());
            callback.onFailure(e);
        });
    }


    // List all clubs
    public void listAllClubs(FirestoreCallback<List<Club>> callback) {
        db.collection("clubs").get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<Club> clubsList = new ArrayList<>();
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                Club club = document.toObject(Club.class);
                clubsList.add(club);
            }
            callback.onSuccess(clubsList);
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Error listing clubs: " + e.getMessage());
            callback.onFailure(e);
        });
    }

    // Search clubs by coordinator
    public void searchClubsByCoordinator(String coordinatorId, FirestoreCallback<List<Club>> callback) {
        db.collection("clubs").whereEqualTo("coordinator", coordinatorId).get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<Club> clubsList = new ArrayList<>();
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                Club club = document.toObject(Club.class);
                clubsList.add(club);
            }
            callback.onSuccess(clubsList);
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Error searching clubs by coordinator: " + e.getMessage());
            callback.onFailure(e);
        });
    }

    // Update club details
    public void updateClub(String clubId, Map<String, Object> updates, FirestoreCallback<Void> callback) {
        db.collection("clubs").document(clubId).update(updates)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Club updated successfully!");
                    callback.onSuccess(null);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error updating club: " + e.getMessage());
                    callback.onFailure(e);
                });
    }

    // Delete club
    public void deleteClub(String clubId, FirestoreCallback<Void> callback) {
        db.collection("clubs").document(clubId).delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Club deleted successfully!");
                    callback.onSuccess(null);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error deleting club: " + e.getMessage());
                    callback.onFailure(e);
                });
    }

    // List all members of a club
    public void listAllMembers(String clubId, FirestoreCallback<List<String>> callback) {
        getClub(clubId, new FirestoreCallback<Club>() {
            @Override
            public void onSuccess(Club club) {
                callback.onSuccess(club.getMembers());
            }

            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, "Error getting club members: " + e.getMessage());
                callback.onFailure(e);
            }
        });
    }

    // Add member to a club
    public void addMemberToClub(String clubId, String memberId, FirestoreCallback<Void> callback) {
        getClub(clubId, new FirestoreCallback<Club>() {
            @Override
            public void onSuccess(Club club) {
                List<String> members = club.getMembers();
                if (!members.contains(memberId)) {
                    members.add(memberId);
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("members", members);
                    updates.put("currentMembers", members.size());
                    updateClub(clubId, updates, callback);
                } else {
                    callback.onFailure(new Exception("Member already in the club"));
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, "Error adding member to club: " + e.getMessage());
                callback.onFailure(e);
            }
        });
    }
}
