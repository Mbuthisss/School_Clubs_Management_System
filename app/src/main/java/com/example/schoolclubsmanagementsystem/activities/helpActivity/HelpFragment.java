package com.example.schoolclubsmanagementsystem.activities.helpActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.schoolclubsmanagementsystem.R;
import com.example.schoolclubsmanagementsystem.activities.helpActivity.HelpViewModel;

public class HelpFragment extends Fragment {

    private HelpViewModel helpViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_help, container, false);

        // Initialize ViewModel
        helpViewModel = new ViewModelProvider(this).get(HelpViewModel.class);

        // Observe help content
        TextView helpTextView = root.findViewById(R.id.help_text_view);
        helpViewModel.getHelpContent().observe(getViewLifecycleOwner(), helpTextView::setText);

        return root;
    }
}
