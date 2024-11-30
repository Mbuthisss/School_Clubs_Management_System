package com.example.schoolclubsmanagementsystem.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolclubsmanagementsystem.R;
import com.example.schoolclubsmanagementsystem.activities.ClubDashboardActivity;
import com.example.schoolclubsmanagementsystem.models.Club;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyClubsAdapter extends RecyclerView.Adapter<MyClubsAdapter.MyClubsViewHolder> {
    private Context context;
    private List<Club> clubsList;
    private FirebaseFirestore firestore;

    public MyClubsAdapter(Context context, List<Club> clubsList) {
        this.context = context;
        this.clubsList = clubsList;
        this.firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public MyClubsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_club, parent, false);
        return new MyClubsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyClubsViewHolder holder, int position) {
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

        // Hide join button
        holder.joinButton.setVisibility(View.GONE);

        // Navigate to club dashboard on item click
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ClubDashboardActivity.class); // Placeholder for Club Dashboard activity
            intent.putExtra("clubId", club.getClubId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return clubsList.size();
    }

    public static class MyClubsViewHolder extends RecyclerView.ViewHolder {
        TextView clubNameTextView;
        TextView clubDescriptionTextView;
        TextView clubCoordinatorTextView;
        TextView clubCapacityTextView;
        TextView clubMembersTextView;
        Button joinButton;

        public MyClubsViewHolder(@NonNull View itemView) {
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
