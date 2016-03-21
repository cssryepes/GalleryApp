package com.kogi.galleryapp.ui.helpers;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.kogi.galleryapp.GalleryApp;
import com.kogi.galleryapp.Utils;
import com.kogi.galleryapp.domain.HttpConnection;
import com.kogi.galleryapp.domain.entities.Image;
import com.kogi.galleryapp.domain.enums.ResponseStatus;

import java.util.List;

/**
 * Proceso encargado de la actualizacion del bitmap e imageview del ViewHolder que se le pasa
 * como parametro.
 */
public class DownloadImage extends AsyncTask<ViewHolder, Void, ViewHolder> {
    private ResponseStatus status;

    @Override
    protected ViewHolder doInBackground(ViewHolder... params) {
        /**
         * Se obtiene del feed la imagen de la calidad seleccionada y se hace el proceso de descarga de la
         * imagen si aun no esta en la cachey  luego la almacena alli.
         */
        try {
            List<Image> images = params[0].mFeed.getImages();
            for (Image image : images) {
                if (image.getQuality().equals(params[0].mQuality)) {

                    GalleryApp app = GalleryApp.getInstance();
                    Bitmap bitmapFromCache = app.getBitmapFromCache(image.getUrl());

                    if (bitmapFromCache != null) {
                        params[0].mBitmap = bitmapFromCache;
                    } else {
                        Utils.print(Log.DEBUG, "DownloadImage AsyncTask: " + image.getUrl());
                        params[0].mBitmap = HttpConnection.getBitmapFromURL(image.getUrl());
                        // Ademas de guardarla en la cache del disco se hace la insercion en la
                        // cache de memoria
                        app.addBitmapToCache(image.getUrl(), params[0].mBitmap);
                    }

                    status = ResponseStatus.OK;
                    return params[0];
                }
            }
        } catch (Exception e) {
            Utils.print(Log.ERROR, Utils.getStackTrace(e));
            status = ResponseStatus.ERROR;
        }
        return params[0];
    }

    @Override
    protected void onPostExecute(ViewHolder viewHolder) {
        super.onPostExecute(viewHolder);

        // Pintamos el bitmap en el imageview
        if (status.equals(ResponseStatus.OK) && viewHolder.mBitmap != null) {
            viewHolder.mImageView.setImageBitmap(viewHolder.mBitmap);
            viewHolder.mView.invalidate();
        }
    }
}