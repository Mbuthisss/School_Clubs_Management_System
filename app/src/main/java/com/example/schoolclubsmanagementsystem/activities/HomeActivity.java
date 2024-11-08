package com.example.schoolclubsmanagementsystem.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolclubsmanagementsystem.R;
import com.example.schoolclubsmanagementsystem.adapters.NewsAdapter;
import com.example.schoolclubsmanagementsystem.base.BaseActivity;
import com.example.schoolclubsmanagementsystem.models.News;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {

    private RecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;
    private List<News> newsList;
    DrawerLayout drawerLayout;
    ImageButton buttonDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        drawerLayout = findViewById(R.id.main);
        buttonDrawerToggle = findViewById(R.id.buttonDrawerToggle);

        buttonDrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });

        // Initialize RecyclerView
        newsRecyclerView = findViewById(R.id.news_recycler_view);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sample data for the news list
        newsList = new ArrayList<>();
        newsList.add(new News("Title 1", "Description 1", "5 minutes ago"));
        newsList.add(new News("Title 2", "Description 2", "10 minutes ago"));
        // Add more items here...

        // Set up adapter
        newsAdapter = new NewsAdapter(this,newsList);
        newsRecyclerView.setAdapter(newsAdapter);

    }
}