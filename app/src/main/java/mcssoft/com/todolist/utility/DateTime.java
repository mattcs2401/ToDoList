package mcssoft.com.todolist.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import mcssoft.com.todolist.R;

public class DateTime {

    /**
     * Create a DateTime object using the current date and time.
     */
    public DateTime() {
        timeMillis = Calendar.getInstance(Locale.getDefault()).getTimeInMillis();
    }

    /**
     * Create a DateTime object using the given date and time.
     * @param timeMillis The date and time in millis.
     */
    public DateTime(long timeMillis) {
        this.timeMillis = timeMillis;
    }

    /**
     * Get the date formatted as DD/MM/YYYY with optional time HH:MM (based on date/time currently set).
     * @param incTime Flag, include the time component.
     * @return The formatted date (and time).
     */
    public String getFormattedDate(boolean incTime) {
        String mask = null;
        if(incTime) {
            mask = Resources.getInstance().getString(R.string.date_time_format);
        } else {
            mask = Resources.getInstance().getString(R.string.date_format);
        }
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTime(new Date(timeMillis));
        SimpleDateFormat sdFormat = new SimpleDateFormat(mask, Locale.getDefault());
        return sdFormat.format(calendar.getTime());
    }

    /**
     * Get a compacted version of the date and time (based on date/time currently set).
     * @return Value as DDMMYYYYHHMMSS.
     */
    public String getCompactedDateTime() {
        String mask = Resources.getInstance().getString(R.string.date_time_format_compacted);
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTime(new Date(timeMillis));
        SimpleDateFormat sdFormat = new SimpleDateFormat(mask, Locale.getDefault());
        return sdFormat.format(calendar.getTime());
    }

    public long getTimeInMillis() {
        return timeMillis;
    }

    public void setTimeInMillis(long timeMillis) {
        this.timeMillis = timeMillis;
    }

    private long timeMillis;
}
