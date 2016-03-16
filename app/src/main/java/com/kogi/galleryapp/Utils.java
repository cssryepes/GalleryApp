package com.kogi.galleryapp;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.format.DateFormat;
import android.util.Log;

import com.kogi.galleryapp.domain.entities.Feed;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Utils {

    public static final String TAG = "GALLERY_APP";
    public static final String FEED = "FEED";
    public static final String POSITION = "POSITION";

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

    public static void print(int type, String message) {
        int maxLogSize = 4000;
        for (int i = 0; i <= message.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i + 1) * maxLogSize;
            end = end > message.length() ? message.length() : end;
            switch (type) {
                case Log.DEBUG:
                    Log.d(TAG, message.substring(start, end));
                    break;
                case Log.ERROR:
                    Log.e(TAG, message.substring(start, end));
                    break;
                default:
                    break;
            }
        }
    }

    public static Bundle getBundle(List<Feed> feed, int position) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(FEED, (ArrayList<? extends Parcelable>) feed);
        args.putInt(POSITION, position);
        return args;
    }

    public static Bundle getBundle(Feed feed) {
        Bundle args = new Bundle();
        args.putParcelable(FEED, feed);
        return args;
    }

}
