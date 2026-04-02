package com.example.dailynews;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dailynews.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadNews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNews();
    }

    private void loadNews() {
        String json = NewsStorage.getNews(this);
        if (json == null) {
            binding.tvNews.setText("No news fetched yet. It will update around 7 PM daily.");
            return;
        }

        try {
            JSONArray arr = new JSONArray(json);
            SpannableStringBuilder builder = new SpannableStringBuilder();

            for (int i = 0; i < arr.length(); i++) {
                JSONObject item = arr.getJSONObject(i);
                final String title = item.optString("title", "Untitled");
                final String url = item.optString("url", "");

                int start = builder.length();
                builder.append((i + 1) + ". " + title + "\n\n");
                int end = builder.length();

                if (!url.isEmpty()) {
                    builder.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        }
                    }, start, end, 0);
                }
            }

            binding.tvNews.setText(builder);
            binding.tvNews.setMovementMethod(LinkMovementMethod.getInstance());
        } catch (Exception e) {
            binding.tvNews.setText("Error parsing news.");
        }
    }
}
