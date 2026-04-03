package com.example.dailynews;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailynews.model.NewsItem;
import com.example.dailynews.ui.TopStoriesAdapter;
import com.example.dailynews.util.UrlOpener;

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
            UrlOpener.openUrl(MainActivity.this, item.getUrl());
        });
        rvTopStories.setAdapter(adapter);
    }

    private List<NewsItem> sampleData() {
        List<NewsItem> list = new ArrayList<>();
        list.add(new NewsItem(
                "UK economy shows signs of resilience amid global uncertainty",
                "BBC News",
                "2026-04-03T20:30:00Z",
                "https://ichef.bbci.co.uk/news/1024/branded_news/12345.jpg",
                "https://www.bbc.co.uk/news/business-66812345"
        ));
        list.add(new NewsItem(
                "Climate summit: leaders agree on new emissions targets",
                "The Guardian",
                "2026-04-03T19:10:00Z",
                "https://i.guim.co.uk/img/media/abcdef12345/0_0_1200_675/master/1200.jpg",
                "https://www.theguardian.com/environment/2026/apr/03/climate-summit-leaders-new-emissions-targets"
        ));
        list.add(new NewsItem(
                "Global markets react after major tech earnings beat expectations",
                "Reuters",
                "2026-04-03T18:00:00Z",
                "https://www.reuters.com/resizer/xyz12345/800x450.jpg",
                "https://www.reuters.com/technology/global-markets-tech-earnings-2026-04-03/"
        ));
        return list;
    }
}
