package com.example.dailynews.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.browser.customtabs.CustomTabsIntent;

public final class UrlOpener {

    private UrlOpener() {}

    public static void openUrl(Context context, String url) {
        if (url == null || url.trim().isEmpty()) return;

        String normalized = normalizeUrl(url);

        try {
            CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
            customTabsIntent.launchUrl(context, Uri.parse(normalized));
        } catch (ActivityNotFoundException e) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(normalized));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (Exception ignored) {}
        } catch (Exception ignored) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(normalized));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (Exception ignored2) {}
        }
    }

    private static String normalizeUrl(String url) {
        String trimmed = url.trim();
        if (trimmed.startsWith("http://") || trimmed.startsWith("https://")) {
            return trimmed;
        } else {
            return "https://" + trimmed;
        }
    }
}
