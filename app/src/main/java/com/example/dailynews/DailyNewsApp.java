package com.example.dailynews;

import android.app.Application;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class DailyNewsApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        scheduleDailyNewsWork();
    }

    private void scheduleDailyNewsWork() {
        LocalDateTime now = LocalDateTime.now();
        LocalTime targetTime = LocalTime.of(19, 0); // 7 PM

        LocalDateTime firstRun;
        if (now.toLocalTime().isBefore(targetTime)) {
            firstRun = now.withHour(19).withMinute(0).withSecond(0).withNano(0);
        } else {
            firstRun = now.plusDays(1).withHour(19).withMinute(0).withSecond(0).withNano(0);
        }

        long initialDelayMillis = Duration.between(now, firstRun).toMillis();

        PeriodicWorkRequest request =
                new PeriodicWorkRequest.Builder(NewsWorker.class, 24, TimeUnit.HOURS)
                        .setInitialDelay(initialDelayMillis, TimeUnit.MILLISECONDS)
                        .build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "daily_news_work",
                ExistingPeriodicWorkPolicy.UPDATE,
                request
        );
    }
}
