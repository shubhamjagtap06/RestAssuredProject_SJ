package api.utilities;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static String getCurrentDateInISOFormat() {
        // Get the current time in UTC
        Instant now = Instant.now();
        // Define the format you want (ISO 8601 with milliseconds and 'Z' for UTC)
        DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .withZone(ZoneId.of("Asia/Kolkata"));
        // Format the current instant
        return formatter.format(now);
    }
}