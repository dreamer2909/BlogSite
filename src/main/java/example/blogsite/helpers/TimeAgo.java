package example.blogsite.helpers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TimeAgo {
    public static String getTimeAgo(LocalDateTime from) {
        LocalDateTime to = LocalDateTime.now();

        Duration duration = Duration.between(from, to);

        if (duration.toMinutes() < 1) return duration.toSeconds() + " giây trước";
        if (duration.toHours() < 1) return duration.toMinutes() + " phút trước";
        if (duration.toDays() < 1) return duration.toDays() + " giờ trước";
        long days = ChronoUnit.DAYS.between(from, to);
        long months = ChronoUnit.MONTHS.between(from, to);
        long years = ChronoUnit.YEARS.between(from, to);

        if (months < 1) return days + " ngày trước";
        if (years < 1) return months + " tháng trước";
        return years + " năm trước";
    }
}
