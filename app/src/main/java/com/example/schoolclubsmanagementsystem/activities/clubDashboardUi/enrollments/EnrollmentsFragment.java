package com.example.schoolclubsmanagementsystem.activities.clubDashboardUi.enrollments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.schoolclubsmanagementsystem.R;

public class EnrollmentsFragment extends Fragment {

    private EnrollmentsViewModel mViewModel;

    public static EnrollmentsFragment newInstance() {
        return new EnrollmentsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_enrollments, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EnrollmentsViewModel.class);
        // TODO: Use the ViewModel
    }

}