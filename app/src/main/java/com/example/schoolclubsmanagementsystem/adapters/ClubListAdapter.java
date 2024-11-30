package com.example.schoolclubsmanagementsystem.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolclubsmanagementsystem.R;
import com.example.schoolclubsmanagementsystem.models.Club;
import com.example.schoolclubsmanagementsystem.models.Student;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClubListAdapter extends RecyclerView.Adapter<ClubListAdapter.ClubListViewHolder> {
    private Context context;
    private List<Club> clubsList;
    private String currentUserId;
    private FirebaseFirestore firestore;

    public ClubListAdapter(Context context, List<Club> clubsList, String currentUserId) {
        this.context = context;
        this.clubsList = clubsList;
        this.currentUserId = currentUserId;
        this.firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ClubListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_club, parent, false);
        return new ClubListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubListViewHolder holder, int position) {
        Club club = clubsList.get(position);
        holder.clubNameTextView.setText(club.getName());
        holder.clubDescriptionTextView.setText(club.getDescription());

        // Fetch and display coordinator name from "students" collection
        firestore.collection("students").document(club.getCoordinator()).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String coordinatorName = documentSnapshot.getString("name");
                        holder.clubCoordinatorTextView.setText("Coordinator: " + coordinatorName);
                    }
                });

        holder.clubCapacityTextView.setText("Capacity: " + club.getCapacity());
        holder.clubMembersTextView.setText("Members: " + club.getCurrentMembers()); // Show actual current members

        // Check if the user is already a member
        if (club.getMembers().contains(currentUserId) || club.getCoordinator().equals(currentUserId)) {
            holder.joinButton.setVisibility(View.GONE);
        } else {
            holder.joinButton.setVisibility(View.VISIBLE);
            holder.joinButton.setOnClickListener(v -> {
                if (club.getClubId() == null) {
                    Toast.makeText(context, "Error: Club ID is null", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (club.getCoordinator().equals(currentUserId)) {
                    Toast.makeText(context, "You cannot join your own club", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (club.getMembers().contains(currentUserId)) {
                    Toast.makeText(context, "You have already joined this club", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (club.getCurrentMembers() < club.getCapacity()) {
                    // Add current user to members list
                    club.addMember(currentUserId);
                    club.setCurrentMembers(club.getCurrentMembers() + 1);

                    // Update Firestore
                    Map<String, Object> clubUpdates = new HashMap<>();
                    clubUpdates.put("members", club.getMembers());
                    clubUpdates.put("currentMembers", club.getCurrentMembers());
                    firestore.collection("clubs").document(club.getClubId()).update(clubUpdates);

                    // Add club to student's club list
                    firestore.collection("students").document(currentUserId).get().addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            Student student = documentSnapshot.toObject(Student.class);
                            List<String> studentClubs = student.getClubs();
                            if (studentClubs == null) {
                                studentClubs = new ArrayList<>();
                            }
                            if (!studentClubs.contains(club.getClubId())) {
                                studentClubs.add(club.getClubId());
                                Map<String, Object> studentUpdates = new HashMap<>();
                                studentUpdates.put("clubs", studentClubs);
                                firestore.collection("students").document(currentUserId).update(studentUpdates);
                            }
                        }
                    });

                    Toast.makeText(context, "Joined " + club.getName(), Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged(); // Update the view to reflect changes
                } else {
                    Toast.makeText(context, "Club is full", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return clubsList.size();
    }

    public static class ClubListViewHolder extends RecyclerView.ViewHolder {
        TextView clubNameTextView;
        TextView clubDescriptionTextView;
        TextView clubCoordinatorTextView;
        TextView clubCapacityTextView;
        TextView clubMembersTextView;
        Button joinButton;

        public ClubListViewHolder(@NonNull View itemView) {
            super(itemView);
            clubNameTextView = itemView.findViewById(R.id.club_name_text_view);
            clubDescriptionTextView = itemView.findViewById(R.id.club_description_text_view);
            clubCoordinatorTextView = itemView.findViewById(R.id.club_coordinator_text_view);
            clubCapacityTextView = itemView.findViewById(R.id.club_capacity_text_view);
            clubMembersTextView = itemView.findViewById(R.id.club_members_text_view);
            joinButton = itemView.findViewById(R.id.join_button);
        }
    }
}
