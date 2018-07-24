package com.ingic.tanfit.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by saeedhyder on 12/22/2017.
 */

public class ISO8601TimeStampHelper {

    public String getISO8601StringForCurrentDate() {
        Date now = new Date();
        return getISO8601StringForDate(now);
    }

    /**
     * Return an ISO 8601 combined date and time string for specified date/time
     *
     * @param date
     *            Date
     * @return String with format "yyyy-MM-dd'T'HH:mm:ss'Z'"
     */
    private String getISO8601StringForDate(Date date) {
      //  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss z");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.ENGLISH);
    //    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        dateFormat.setTimeZone(TimeZone.getDefault());

        return dateFormat.format(date);
    }
}
