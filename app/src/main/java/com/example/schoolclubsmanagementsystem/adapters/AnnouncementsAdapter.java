package com.example.schoolclubsmanagementsystem.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolclubsmanagementsystem.R;
import com.example.schoolclubsmanagementsystem.models.Announcement;

import java.util.List;

public class AnnouncementsAdapter extends RecyclerView.Adapter<AnnouncementsAdapter.AnnouncementViewHolder> {
    private Context context;
    private List<Announcement> announcementsList;

    public AnnouncementsAdapter(Context context, List<Announcement> announcementsList) {
        this.context = context;
        this.announcementsList = announcementsList;
    }

    @NonNull
    @Override
    public AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_announcement, parent, false);
        return new AnnouncementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementViewHolder holder, int position) {
        Announcement announcement = announcementsList.get(position);
        holder.titleTextView.setText(announcement.getTitle());
        holder.descriptionTextView.setText(announcement.getDescription());
        holder.dateTextView.setText(announcement.getDate());
    }

    @Override
    public int getItemCount() {
        return announcementsList.size();
    }

    public static class AnnouncementViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView dateTextView;

        public AnnouncementViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.text_view_announcement_title);
            descriptionTextView = itemView.findViewById(R.id.text_view_announcement_description);
            dateTextView = itemView.findViewById(R.id.text_view_announcement_date);
        }
    }
}
