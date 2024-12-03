package com.example.schoolclubsmanagementsystem.activities.homeActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.schoolclubsmanagementsystem.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView appLogoImageView = root.findViewById(R.id.app_logo_image_view);
        TextView appDescriptionTextView = root.findViewById(R.id.app_description_text_view);

        // Set the app logo
        appLogoImageView.setImageResource(R.drawable.app_logo);

        // ViewModel setup
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getAppDescription().observe(getViewLifecycleOwner(), appDescriptionTextView::setText);

        return root;
    }
}
