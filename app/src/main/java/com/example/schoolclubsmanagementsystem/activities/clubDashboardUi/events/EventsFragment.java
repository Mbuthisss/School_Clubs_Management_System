package com.example.schoolclubsmanagementsystem.activities.clubDashboardUi.events;

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
import com.example.schoolclubsmanagementsystem.adapters.EventsAdapter;
import com.example.schoolclubsmanagementsystem.models.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventsFragment extends Fragment {

    private EventsViewModel eventsViewModel;
    private RecyclerView recyclerView;
    private EventsAdapter adapter;
    private List<Event> eventsList;
    private String clubId;
    private FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        eventsViewModel = new ViewModelProvider(this).get(EventsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_events, container, false);
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
        recyclerView = root.findViewById(R.id.recycler_view_events);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        eventsList = new ArrayList<>();
        adapter = new EventsAdapter(getContext(), eventsList);
        recyclerView.setAdapter(adapter);

        // Initialize the FloatingActionButton
        fab = root.findViewById(R.id.fab_add_event);
        fab.setOnClickListener(view -> showAddEventDialog());

        // Observe the events LiveData
        eventsViewModel.getEvents().observe(getViewLifecycleOwner(), events -> {
            eventsList.clear();
            eventsList.addAll(events);
            adapter.notifyDataSetChanged();
        });

        // Load events from database
        eventsViewModel.loadEvents(clubId);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.events_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_edit) {
            // Handle edit action
            showEditEventDialog();
            return true;
        } else if (itemId == R.id.action_delete) {
            // Handle delete action
            showDeleteEventDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showAddEventDialog() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_event, null);
        EditText nameEditText = dialogView.findViewById(R.id.edit_text_event_name);
        EditText locationEditText = dialogView.findViewById(R.id.edit_text_event_location);
        EditText descriptionEditText = dialogView.findViewById(R.id.edit_text_event_description);
        EditText dateEditText = dialogView.findViewById(R.id.edit_text_event_date);
        Button addButton = dialogView.findViewById(R.id.button_add_event);

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext())
                .setView(dialogView)
                .setTitle("Add Event");

        final androidx.appcompat.app.AlertDialog dialog = builder.create();

        addButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String location = locationEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();
            String date = dateEditText.getText().toString().trim();

            if (!name.isEmpty() && !location.isEmpty() && !description.isEmpty() && !date.isEmpty()) {
                String eventId = UUID.randomUUID().toString();
                Event event = new Event(eventId, clubId, name, location, description, date);
                eventsViewModel.addEvent(event);

                Toast.makeText(getContext(), "Event added successfully!", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            } else {
                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    private void showEditEventDialog() {
        // Implementation for editing an event
    }

    private void showDeleteEventDialog() {
        // Implementation for deleting an event
    }
}
