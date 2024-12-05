package com.example.schoolclubsmanagementsystem.activities.clubDashboardUi.payments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolclubsmanagementsystem.R;
import com.example.schoolclubsmanagementsystem.adapters.PaymentsAdapter;
import com.example.schoolclubsmanagementsystem.models.Payment;

import java.util.ArrayList;
import java.util.List;

public class PaymentsFragment extends Fragment {

    private RecyclerView paymentsRecyclerView;
    private PaymentsAdapter paymentsAdapter;
    private List<Payment> paymentsList;
    private PaymentsViewModel paymentsViewModel;
    private String clubId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_payments, container, false);

        // Retrieve the club ID from arguments
        if (getArguments() != null) {
            clubId = getArguments().getString("clubId");
        }

        // Initialize RecyclerView
        paymentsRecyclerView = root.findViewById(R.id.payments_recycler_view);
        paymentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        paymentsList = new ArrayList<>();
        paymentsAdapter = new PaymentsAdapter(getContext(), paymentsList);
        paymentsRecyclerView.setAdapter(paymentsAdapter);

        // ViewModel setup
        paymentsViewModel = new ViewModelProvider(this).get(PaymentsViewModel.class);
        paymentsViewModel.getPayments().observe(getViewLifecycleOwner(), payments -> {
            paymentsList.clear();
            paymentsList.addAll(payments);
            paymentsAdapter.notifyDataSetChanged();
        });

        // Load payments for the club
        paymentsViewModel.loadPayments(clubId);

        return root;
    }
}
