package com.example.schoolclubsmanagementsystem.activities.clubDashboardUi.announcements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolclubsmanagementsystem.R;
import com.example.schoolclubsmanagementsystem.adapters.AnnouncementsAdapter;
import com.example.schoolclubsmanagementsystem.firestore.Announcements;
import com.example.schoolclubsmanagementsystem.firestore.Clubs;
import com.example.schoolclubsmanagementsystem.models.Announcement;
import com.example.schoolclubsmanagementsystem.models.Club;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AnnouncementsFragment extends Fragment {

    private AnnouncementsViewModel announcementsViewModel;
    private RecyclerView recyclerView;
    private AnnouncementsAdapter adapter;
    private List<Announcement> announcementsList;
    private String clubId;
    private FloatingActionButton fab;
    private boolean isCoordinator = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        announcementsViewModel = new ViewModelProvider(this).get(AnnouncementsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_announcements, container, false);
        setHasOptionsMenu(true); // Enable options menu in fragment

        // Retrieve the club ID from the bundle
        if (getArguments() != null) {
            clubId = getArguments().getString("clubId");
        }

        // Verify clubId is not null
        if (clubId == null) {
            Toast.makeText(getContext(), "Club ID is missing", Toast.LENGTH_SHORT).show();
            return root;
        }

        // Initialize the RecyclerView
        recyclerView = root.findViewById(R.id.recycler_view_announcements);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        announcementsList = new ArrayList<>();
        adapter = new AnnouncementsAdapter(getContext(), announcementsList);
        recyclerView.setAdapter(adapter);

        // Initialize the FloatingActionButton
        fab = root.findViewById(R.id.fab_add_announcement);
        fab.setOnClickListener(view -> showAddAnnouncementDialog());

        // Check if the current user is the club coordinator
        checkCoordinatorStatus();

        // Observe the announcements LiveData
        announcementsViewModel.getAnnouncements().observe(getViewLifecycleOwner(), announcements -> {
            announcementsList.clear();
            announcementsList.addAll(announcements);
            adapter.notifyDataSetChanged();
        });

        // Load announcements from database
        announcementsViewModel.loadAnnouncements(clubId);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.announcements_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_edit) {
            if (isCoordinator) {
                // Handle edit action
                showEditAnnouncementDialog();
            } else {
                Toast.makeText(getContext(), "Only coordinators can edit announcements", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (itemId == R.id.action_delete) {
            if (isCoordinator) {
                // Handle delete action
                showDeleteAnnouncementDialog();
            } else {
                Toast.makeText(getContext(), "Only coordinators can delete announcements", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void checkCoordinatorStatus() {
        Clubs clubsDb = new Clubs();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String currentUserId = currentUser.getUid();

            clubsDb.getClub(clubId, new Clubs.FirestoreCallback<Club>() {
                @Override
                public void onSuccess(Club club) {
                    isCoordinator = club.getCoordinator().equals(currentUserId);
                    fab.setVisibility(isCoordinator ? View.VISIBLE : View.GONE);
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(getContext(), "Error checking coordinator status: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showAddAnnouncementDialog() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_announcement, null);
        EditText titleEditText = dialogView.findViewById(R.id.edit_text_announcement_title);
        EditText descriptionEditText = dialogView.findViewById(R.id.edit_text_announcement_description);
        Button addButton = dialogView.findViewById(R.id.button_add_announcement);

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext())
                .setView(dialogView)
                .setTitle("Add Announcement");

        final androidx.appcompat.app.AlertDialog dialog = builder.create();

        addButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();
            String date = java.text.DateFormat.getDateInstance().format(new java.util.Date());

            if (!title.isEmpty() && !description.isEmpty()) {
                String announcementId = UUID.randomUUID().toString();
                Announcement announcement = new Announcement(announcementId, clubId, title, description, date);
                announcementsViewModel.addAnnouncement(announcement);

                Toast.makeText(getContext(), "Announcement added successfully!", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            } else {
                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    private void showEditAnnouncementDialog() {
        // Implementation for editing an announcement
    }

    private void showDeleteAnnouncementDialog() {
        // Implementation for deleting an announcement
    }
}
