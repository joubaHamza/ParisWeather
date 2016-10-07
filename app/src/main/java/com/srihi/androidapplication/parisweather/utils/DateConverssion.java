package com.srihi.androidapplication.parisweather.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Hamza Srihi on 06/10/2016.
 */
public class DateConverssion {

    public static String getDatyStringFormat(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("EEEE", Locale.FRANCE);
        return format.format(date).toUpperCase();
    }

    public static String getDateFormat(long seconds) {
        Calendar calendar = Calendar.getInstance(Locale.FRANCE);
        calendar.setTimeInMillis(seconds*1000L);
        return getDatyStringFormat(calendar.getTime());
    }

    public static Date convertToNewDate(String dateString) {
        // "yyyy-MM-dd HH:mm:ss.SSSSSS"; //2014-12-02 14:38:57.000000
        String date = dateString;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS", Locale.FRANCE);
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(date);
        } catch (ParseException e) {

            e.printStackTrace();
        }
        return convertedDate;
    }

    public static String getDateString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        return format.format(date);
    }
}
