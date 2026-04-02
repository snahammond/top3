package com.example.dailynews;

import android.content.Context;
import android.content.SharedPreferences;

public class NewsStorage {

    private static final String PREFS_NAME = "news_prefs";
    private static final String KEY_LATEST_NEWS = "latest_news";

    public static void saveNews(Context context, String json) {
        SharedPreferences prefs =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_LATEST_NEWS, json).apply();
    }

    public static String getNews(Context context) {
        SharedPreferences prefs =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_LATEST_NEWS, null);
    }
}
