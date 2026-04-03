package com.example.dailynews.util;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class TimeUtils {

    private TimeUtils() {}

    public static String getRelativeTime(String isoTimestamp) {
        if (isoTimestamp == null || isoTimestamp.trim().isEmpty()) return "";

        long publishedMillis = parseIsoToMillis(isoTimestamp);
        if (publishedMillis <= 0) return "";

        long now = System.currentTimeMillis();
        CharSequence relative = DateUtils.getRelativeTimeSpanString(
                publishedMillis,
                now,
                DateUtils.MINUTE_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_RELATIVE);

        return relative.toString();
    }

    private static long parseIsoToMillis(String iso) {
        try {
            Instant instant = Instant.parse(iso);
            return instant.toEpochMilli();
        } catch (NoClassDefFoundError | DateTimeParseException ignored) {
        } catch (Exception ignored) {}

        String[] patterns = new String[] {
                "yyyy-MM-dd'T'HH:mm:ss'Z'",
                "yyyy-MM-dd'T'HH:mm:ssZ",
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
        };

        for (String pattern : patterns) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date d = sdf.parse(iso.replaceAll(":(?=[0-9]{2}$)", ""));
                if (d != null) return d.getTime();
            } catch (ParseException ignored) {}
        }

        return -1;
    }
}
