package com.example.schoolclubsmanagementsystem.activities.myClubsActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolclubsmanagementsystem.R;
import com.example.schoolclubsmanagementsystem.adapters.MyClubsAdapter;
import com.example.schoolclubsmanagementsystem.models.Club;
import com.google.firebase.auth.FirebaseUser;
import com.example.schoolclubsmanagementsystem.user.Authentication;

import java.util.ArrayList;
import java.util.List;

public class MyClubsFragment extends Fragment {

    private RecyclerView clubsRecyclerView;
    private MyClubsAdapter myClubsAdapter;
    private List<Club> clubsList;
    private MyClubsViewModel myClubsViewModel;
    private Authentication auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_clubs, container, false);

        // Initialize instances
        auth = new Authentication();
        FirebaseUser currentUser = auth.getCurrentUser();

        // Initialize RecyclerView
        clubsRecyclerView = root.findViewById(R.id.clubs_recycler_view);
        clubsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        clubsList = new ArrayList<>();
        myClubsAdapter = new MyClubsAdapter(getContext(), clubsList);
        clubsRecyclerView.setAdapter(myClubsAdapter);

        // ViewModel setup
        myClubsViewModel = new ViewModelProvider(this).get(MyClubsViewModel.class);
        myClubsViewModel.getClubs().observe(getViewLifecycleOwner(), clubs -> {
            clubsList.clear();
            clubsList.addAll(clubs);
            myClubsAdapter.notifyDataSetChanged();
        });

        // Load clubs the student has joined and coordinates
        myClubsViewModel.loadStudentClubs(currentUser);

        return root;
    }
}
