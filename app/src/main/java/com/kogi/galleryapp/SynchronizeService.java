package com.kogi.galleryapp;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.kogi.galleryapp.domain.HttpConnection;
import com.kogi.galleryapp.domain.entities.Feed;
import com.kogi.galleryapp.domain.entities.Image;

import java.util.List;

/**
 * Servicio para la descarga de imagenes y el guardado de estas en cache
 */
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

            if (feeds != null) {
                for (Feed feed : feeds) {
                    for (Image image : feed.getImages()) {

                        Bitmap bitmapFromCache = app.getBitmapFromCache(image.getUrl());

                        //Si la imagen ya existe en cache no se descarga, pero solo se guarda en cache
                        // de disco para no afectar el rendimiento de la aplicacion
                        if (bitmapFromCache == null) {
                            try {
                                Bitmap bitmap = HttpConnection.getBitmapFromURL(image.getUrl());
                                app.addBitmapToDiskCache(image.getUrl(), bitmap);
                                Utils.print(Log.DEBUG, "DownloadImage Service: " + image.getUrl());
                            } catch (Exception e) {
                                Utils.print(Log.ERROR, Utils.getStackTrace(e));
                            }
                        }
                    }
                }

            }

        }

    }

}
