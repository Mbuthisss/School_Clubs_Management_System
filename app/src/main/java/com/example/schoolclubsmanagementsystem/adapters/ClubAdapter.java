package com.example.schoolclubsmanagementsystem.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolclubsmanagementsystem.R;
import com.example.schoolclubsmanagementsystem.models.Club;

import java.util.List;

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.ClubViewHolder> {
    private Context context;
    private List<Club> clubsList;

    public ClubAdapter(Context context, List<Club> clubsList) {
        this.context = context;
        this.clubsList = clubsList;
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
        holder.clubCapacityTextView.setText(String.valueOf(club.getCapacity()));
    }

    @Override
    public int getItemCount() {
        return clubsList.size();
    }

    public static class ClubViewHolder extends RecyclerView.ViewHolder {
        TextView clubNameTextView;
        TextView clubDescriptionTextView;
        TextView clubCapacityTextView;

        public ClubViewHolder(@NonNull View itemView) {
            super(itemView);
            clubNameTextView = itemView.findViewById(R.id.club_name);
            clubDescriptionTextView = itemView.findViewById(R.id.club_description);
            clubCapacityTextView = itemView.findViewById(R.id.club_capacity);
        }
    }
}
