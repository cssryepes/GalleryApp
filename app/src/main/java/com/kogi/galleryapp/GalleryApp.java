package com.kogi.galleryapp;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.kogi.galleryapp.domain.entities.Feed;

import java.util.ArrayList;
import java.util.List;

public class GalleryApp extends Application {
    private static GalleryApp instance = null;
    public static final String TAG = "GALLERY_APP";
    public static final String FEED = "FEED";
    public static final String POSITION = "POSITION";

    public static GalleryApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Bundle getBundle(List<Feed> feed, int position) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(FEED, (ArrayList<? extends Parcelable>) feed);
        args.putInt(POSITION, position);
        return args;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void print(int type, String message) {
        int maxLogSize = 4000;
        for(int i = 0; i <= message.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
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
}
