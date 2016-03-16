package com.kogi.galleryapp;

import android.text.format.DateFormat;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Locale;

public class Utils {

    public static String getStackTrace(Exception e) {
        StringWriter errors = new StringWriter();
        PrintWriter writer = new PrintWriter(errors);
        e.printStackTrace(writer);
        return errors.toString().replaceAll("\t", "").replaceAll("\n", " | ").replaceAll("\r", "").trim();
    }

    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("ccc dd LLL yyyy 'at' HH:mm", cal).toString();
        return date;
    }
}
