package com.example.dailynews;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsWorker extends Worker {

    private static final String TAG = "NewsWorker";

    // TODO: put your real NewsAPI key here
    private static final String NEWS_API_KEY = "YOUR_NEWSAPI_KEY_HERE";

    private final OkHttpClient client = new OkHttpClient();

    public NewsWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            // Example: top headlines, general, GB (you can change country/category)
            String url = "https://newsapi.org/v2/top-headlines?country=gb&pageSize=3&apiKey=" + NEWS_API_KEY;

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                Log.e(TAG, "HTTP error: " + response.code());
                return Result.retry();
            }

            String body = response.body() != null ? response.body().string() : "";
            Log.d(TAG, "News response: " + body);

            // Parse titles + URLs of top 3
            JSONObject json = new JSONObject(body);
            JSONArray articles = json.optJSONArray("articles");
            if (articles == null || articles.length() == 0) {
                Log.w(TAG, "No articles in response");
                return Result.success();
            }

            JSONArray simplified = new JSONArray();
            int count = Math.min(3, articles.length());
            for (int i = 0; i < count; i++) {
                JSONObject a = articles.getJSONObject(i);
                JSONObject item = new JSONObject();
                item.put("title", a.optString("title", ""));
                item.put("description", a.optString("description", ""));
                item.put("url", a.optString("url", ""));
                simplified.put(item);
            }

            NewsStorage.saveNews(getApplicationContext(), simplified.toString());

            return Result.success();
        } catch (IOException e) {
            Log.e(TAG, "Network error", e);
            return Result.retry();
        } catch (Exception e) {
            Log.e(TAG, "Unexpected error", e);
            return Result.failure();
        }
    }
}
