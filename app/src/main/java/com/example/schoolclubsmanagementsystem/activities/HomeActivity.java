package com.example.schoolclubsmanagementsystem.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.schoolclubsmanagementsystem.MainActivity;
import com.example.schoolclubsmanagementsystem.R;
import com.example.schoolclubsmanagementsystem.adapters.NewsAdapter;
import com.example.schoolclubsmanagementsystem.models.News;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends MainActivity {

    private RecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;
    private List<News> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.main);
        buttonDrawerToggle = findViewById(R.id.buttonDrawerToggle);

        buttonDrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        // Initialize RecyclerView
        newsRecyclerView = findViewById(R.id.news_recycler_view);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sample data for the news list
        newsList = new ArrayList<>();
        newsList.add(new News("Title 1", "Description 1", "5 minutes ago"));
        newsList.add(new News("Title 2", "Description 2", "10 minutes ago"));
        newsList.add(new News("Title 3", "Description 3", "10 minutes ago"));
        newsList.add(new News("Title 4", "Description 4", "10 minutes ago"));
        newsList.add(new News("Title 5", "Description 5", "10 minutes ago"));
        newsList.add(new News("Title 6", "Description 6", "10 minutes ago"));
        // Add more items here...

        // Set up adapter
        newsAdapter = new NewsAdapter(this, newsList);
        newsRecyclerView.setAdapter(newsAdapter);
    }
}
