package helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateHelper {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static String getDateForPrefix(boolean useTime, boolean useMillis, boolean useUnderlines) {
        String pattern = "yyyy_MM_dd";
        if (useTime) {
            if (useMillis) {
                pattern = pattern + "_HH_mm_ss_SSSS";
            } else {
                pattern = pattern + "_HH_mm_ss";
            }
        }

        if (!useUnderlines) {
            pattern = pattern.replace("_", "");
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.US);

        Date date = null;
        try {
            date = new SimpleDateFormat(pattern).parse(getUtcTimezoneValue(pattern));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return simpleDateFormat.format(date);
    }

    public static String getUtcTimezoneValue(String dateFormat) {
        String dateString;

        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(dateFormat, Locale.US); // "EE MMM dd HH:mm:ss zzz yyyy"
        simpleDateFormat.setTimeZone(timeZone);
        dateString = simpleDateFormat.format(calendar.getTime());

        return dateString;
    }
}
