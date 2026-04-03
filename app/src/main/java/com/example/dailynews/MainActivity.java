package com.example.dailynews;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailynews.model.NewsItem;
import com.example.dailynews.network.ArticleDto;
import com.example.dailynews.network.NewsApiResponse;
import com.example.dailynews.network.NetworkClient;
import com.example.dailynews.ui.TopStoriesAdapter;
import com.example.dailynews.util.UrlOpener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvTopStories;
    private TopStoriesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvTopStories = findViewById(R.id.rvTopStories);
        rvTopStories.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TopStoriesAdapter(new ArrayList<>(), item -> {
            UrlOpener.openUrl(MainActivity.this, item.getUrl());
        });
        rvTopStories.setAdapter(adapter);

        fetchTopStories();
    }

    private void fetchTopStories() {
        String apiKey = BuildConfig.NEWS_API_KEY;
        if (apiKey == null || apiKey.trim().isEmpty()) {
            Toast.makeText(this, "News API key not configured", Toast.LENGTH_LONG).show();
            return;
        }

        Call<NewsApiResponse> call = NetworkClient.service().topHeadlines("gb", 3, apiKey);
        call.enqueue(new Callback<NewsApiResponse>() {
            @Override
            public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "Failed to load news", Toast.LENGTH_SHORT).show());
                    return;
                }
                List<ArticleDto> articles = response.body().articles;
                if (articles == null || articles.isEmpty()) {
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "No top stories available", Toast.LENGTH_SHORT).show());
                    return;
                }

                List<NewsItem> mapped = new ArrayList<>();
                for (ArticleDto a : articles) {
                    String title = a.title != null ? a.title : "";
                    String source = (a.source != null && a.source.name != null) ? a.source.name : "";
                    String publishedAt = a.publishedAt != null ? a.publishedAt : "";
                    String imageUrl = a.urlToImage != null ? a.urlToImage : "";
                    String url = a.url != null ? a.url : "";
                    mapped.add(new NewsItem(title, source, publishedAt, imageUrl, url));
                }

                runOnUiThread(() -> adapter.update(mapped));
            }

            @Override
            public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show());
            }
        });
    }
}
