package com.example.schoolclubsmanagementsystem.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.schoolclubsmanagementsystem.R;
import com.example.schoolclubsmanagementsystem.models.News; // Make sure this path is correct
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private Context context;
    private List<News> newsList;

    // Constructor for the adapter
    public NewsAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    // Inflates the news_item.xml layout for each item in the RecyclerView
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view);
    }

    // Binds data from the newsList to each item in the RecyclerView
    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News news = newsList.get(position); // Get the news item at this position
        holder.newsTitle.setText(news.getTitle()); // Set the title
        holder.newsDate.setText(news.getDate()); // Set the date
        holder.newsDescription.setText(news.getDescription()); // Set a short description or summary
    }

    // Returns the total number of items in the data set
    @Override
    public int getItemCount() {
        return newsList.size();
    }

    // ViewHolder class that holds the views for each item
    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView newsTitle, newsDate, newsDescription;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.news_title); // Title TextView in news_item.xml
            newsDate = itemView.findViewById(R.id.news_date); // Date TextView in news_item.xml
            newsDescription = itemView.findViewById(R.id.news_description); // Description TextView in news_item.xml
        }
    }
}