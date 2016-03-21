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
    public static final String FEED_POSITION = "FEED_POSITION";
    public static final String DIALOG_STYLE = "DIALOG_STYLE";
    public static final String DIALOG_TITLE = "DIALOG_TITLE";
    public static final String DIALOG_BODY = "DIALOG_BODY";
    public static final String DIALOG_BUTTON = "DIALOG_BUTTON";

    public static String getStackTrace(Exception e) {
        StringWriter errors = new StringWriter();
        PrintWriter writer = new PrintWriter(errors);
        e.printStackTrace(writer);
        return errors.toString();
    }

    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(time * 1000);
        return DateFormat.format("ccc dd LLL yyyy 'at' HH:mm", cal).toString();
    }

    /**
     * Imprime en consola, si el mensaje es muy largo se divide en varias impresiones
     */
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

    public static Bundle getListFeedBundle(List<Feed> feed, int position) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(FEED, (ArrayList<? extends Parcelable>) feed);
        args.putInt(FEED_POSITION, position);
        return args;
    }

    public static Bundle getFeedBundle(Feed feed) {
        Bundle args = new Bundle();
        args.putParcelable(FEED, feed);
        return args;
    }

    public static Bundle getDialogBundle(int style,String title, String message, String button) {
        Bundle args = new Bundle();
        args.putInt(DIALOG_STYLE, style);
        args.putString(DIALOG_TITLE, title);
        args.putString(DIALOG_BODY, message);
        args.putString(DIALOG_BUTTON, button);
        return args;
    }

}
