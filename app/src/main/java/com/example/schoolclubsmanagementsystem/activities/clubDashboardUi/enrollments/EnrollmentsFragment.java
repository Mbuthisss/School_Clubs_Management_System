package com.example.schoolclubsmanagementsystem.activities.clubDashboardUi.enrollments;

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
import com.example.schoolclubsmanagementsystem.adapters.EnrollmentsAdapter;
import com.example.schoolclubsmanagementsystem.models.Student;
import com.example.schoolclubsmanagementsystem.models.Club;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentsFragment extends Fragment {

    private RecyclerView enrollmentsRecyclerView;
    private EnrollmentsAdapter enrollmentsAdapter;
    private List<Student> studentsList;
    private EnrollmentsViewModel enrollmentsViewModel;
    private String clubId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_enrollments, container, false);

        // Retrieve the club ID from arguments
        if (getArguments() != null) {
            clubId = getArguments().getString("clubId");
        }

        // Initialize RecyclerView
        enrollmentsRecyclerView = root.findViewById(R.id.enrollments_recycler_view);
        enrollmentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        studentsList = new ArrayList<>();
        enrollmentsAdapter = new EnrollmentsAdapter(getContext(), studentsList);
        enrollmentsRecyclerView.setAdapter(enrollmentsAdapter);

        // ViewModel setup
        enrollmentsViewModel = new ViewModelProvider(this).get(EnrollmentsViewModel.class);
        enrollmentsViewModel.getEnrolledStudents().observe(getViewLifecycleOwner(), students -> {
            studentsList.clear();
            studentsList.addAll(students);
            enrollmentsAdapter.notifyDataSetChanged();
        });

        // Load enrolled students
        enrollmentsViewModel.loadEnrolledStudents(clubId);

        return root;
    }
}
