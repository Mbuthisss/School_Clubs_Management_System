package com.example.schoolclubsmanagementsystem.activities.clubListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolclubsmanagementsystem.R;
import com.example.schoolclubsmanagementsystem.activities.CreateClubActivity;
import com.example.schoolclubsmanagementsystem.adapters.ClubListAdapter;
import com.example.schoolclubsmanagementsystem.models.Club;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ClubListFragment extends Fragment {

    private RecyclerView clubRecyclerView;
    private ClubListAdapter clubListAdapter;
    private List<Club> clubsList;
    private ClubListViewModel clubListViewModel;
    private ImageView createClubButton;
    private String currentUserId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_club_list, container, false);

        createClubButton = root.findViewById(R.id.create_club_button);
        clubRecyclerView = root.findViewById(R.id.club_recycler_view);

        createClubButton.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), CreateClubActivity.class);
            startActivity(intent);
        });

        clubRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        clubsList = new ArrayList<>();
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        clubListAdapter = new ClubListAdapter(getContext(), clubsList, currentUserId);
        clubRecyclerView.setAdapter(clubListAdapter);

        clubListViewModel = new ViewModelProvider(this).get(ClubListViewModel.class);
        clubListViewModel.getClubs().observe(getViewLifecycleOwner(), clubs -> {
            clubsList.clear();
            clubsList.addAll(clubs);
            clubListAdapter.notifyDataSetChanged();
        });

        clubListViewModel.loadClubs();

        return root;
    }
}
