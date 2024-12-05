package com.example.schoolclubsmanagementsystem.activities.clubDashboardUi.events;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolclubsmanagementsystem.firestore.Events;
import com.example.schoolclubsmanagementsystem.models.Event;

import java.util.List;

public class EventsViewModel extends ViewModel {
    private final MutableLiveData<List<Event>> events;

    public EventsViewModel() {
        events = new MutableLiveData<>();
    }

    public LiveData<List<Event>> getEvents() {
        return events;
    }

    public void loadEvents(String clubId) {
        Events eventsDb = new Events();
        eventsDb.getEvents(clubId, new Events.FirestoreCallback<List<Event>>() {
            @Override
            public void onSuccess(List<Event> result) {
                events.setValue(result);
            }

            @Override
            public void onFailure(Exception e) {
                // Handle error if needed
            }
        });
    }

    public void addEvent(Event event) {
        Events eventsDb = new Events();
        eventsDb.addEvent(event, new Events.FirestoreCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                // Refresh the list of events after adding
                loadEvents(event.getClubId());
            }

            @Override
            public void onFailure(Exception e) {
                // Handle error if needed
            }
        });
    }
}
