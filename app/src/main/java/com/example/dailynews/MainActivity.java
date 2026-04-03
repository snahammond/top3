package com.example.dailynews;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailynews.model.NewsItem;
import com.example.dailynews.ui.TopStoriesAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvTopStories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvTopStories = findViewById(R.id.rvTopStories);
        rvTopStories.setLayoutManager(new LinearLayoutManager(this));

        List<NewsItem> top3 = sampleData();
        TopStoriesAdapter adapter = new TopStoriesAdapter(top3, item -> {
            // Example click: open article URL if you have one
            // If NewsItem had a url field, you could:
            // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(item.getUrl())));
        });
        rvTopStories.setAdapter(adapter);
    }

    private List<NewsItem> sampleData() {
        List<NewsItem> list = new ArrayList<>();
        list.add(new NewsItem(
                "Hero story title goes here — concise and compelling",
                "BBC News",
                "2h ago",
                "https://via.placeholder.com/800x450.png?text=Hero+Image",
                "https://www.bbc.co.uk"
        ));
        list.add(new NewsItem(
                "Second story headline that is short and scannable",
                "The Guardian",
                "3h ago",
                "https://via.placeholder.com/200.png?text=Thumb+1",
                "https://www.theguardian.com"
        ));
        list.add(new NewsItem(
                "Third story headline with a clear hook",
                "Reuters",
                "4h ago",
                "https://via.placeholder.com/200.png?text=Thumb+2",
                "https://www.reuters.com"
        ));
        return list;
    }
}
