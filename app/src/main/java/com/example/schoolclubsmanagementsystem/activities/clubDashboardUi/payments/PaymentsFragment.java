package com.example.schoolclubsmanagementsystem.activities.clubDashboardUi.payments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.schoolclubsmanagementsystem.R;

public class PaymentsFragment extends Fragment {

    private PaymentsViewModel mViewModel;

    public static PaymentsFragment newInstance() {
        return new PaymentsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payments, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PaymentsViewModel.class);
        // TODO: Use the ViewModel
    }

}