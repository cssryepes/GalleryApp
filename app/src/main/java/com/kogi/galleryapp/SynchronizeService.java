package com.kogi.galleryapp;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.kogi.galleryapp.domain.HttpConnection;
import com.kogi.galleryapp.domain.entities.Feed;
import com.kogi.galleryapp.domain.entities.Image;

import java.io.IOException;
import java.util.List;

public class SynchronizeService extends IntentService {

    public SynchronizeService() {
        super("SynchronizeService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {

            GalleryApp app = GalleryApp.getInstance();
            List<Feed> feeds = extras.getParcelableArrayList(Utils.FEED);

            for (Feed feed : feeds) {
                for (Image image : feed.getImages()) {

                    Bitmap bitmapFromCache = app.getBitmapFromCache(image.getUrl());
                    if (bitmapFromCache == null) {
                        try {
                            Bitmap bitmap = HttpConnection.getBitmapFromURL(image.getUrl());
                            app.addBitmapToDiskCache(image.getUrl(), bitmap);
                            Utils.print(Log.DEBUG, "DownloadImage Service: " + image.getUrl());
                        } catch (IOException e) {
                            Utils.print(Log.DEBUG, "DownloadImage Service: " + image.getUrl());
                        }
                    }
                }
            }

        }

    }

}
