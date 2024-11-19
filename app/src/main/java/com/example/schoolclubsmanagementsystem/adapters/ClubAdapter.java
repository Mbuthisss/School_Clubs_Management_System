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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.ClubViewHolder> {
    private Context context;
    private List<Club> clubsList;
    private String currentUserId;
    private FirebaseFirestore firestore;

    public ClubAdapter(Context context, List<Club> clubsList, String currentUserId) {
        this.context = context;
        this.clubsList = clubsList;
        this.currentUserId = currentUserId;
        this.firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ClubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_club, parent, false);
        return new ClubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubViewHolder holder, int position) {
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
                Toast.makeText(context, "Joined " + club.getName(), Toast.LENGTH_SHORT).show();
                notifyDataSetChanged(); // Update the view to reflect changes
            } else {
                Toast.makeText(context, "Club is full", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return clubsList.size();
    }

    public static class ClubViewHolder extends RecyclerView.ViewHolder {
        TextView clubNameTextView;
        TextView clubDescriptionTextView;
        TextView clubCoordinatorTextView;
        TextView clubCapacityTextView;
        TextView clubMembersTextView;
        Button joinButton;

        public ClubViewHolder(@NonNull View itemView) {
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
