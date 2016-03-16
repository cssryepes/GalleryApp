package com.kogi.galleryapp.ui.fragments.adapters.helpers;

import android.os.AsyncTask;
import android.util.Log;

import com.kogi.galleryapp.Utils;
import com.kogi.galleryapp.domain.HttpConnection;
import com.kogi.galleryapp.domain.entities.Image;
import com.kogi.galleryapp.domain.enums.ResponseStatus;

import java.util.List;

public class DownloadImage extends AsyncTask<ViewHolder, Void, ViewHolder> {
    private ResponseStatus status;

    @Override
    protected ViewHolder doInBackground(ViewHolder... params) {
        Utils.print(Log.DEBUG, params[0].toString());
        try {
            List<Image> images = params[0].mFeed.getImages();
            for (Image image : images) {
                if (image.getQuality().equals(params[0].mQuality)) {
                    params[0].mBitmap = HttpConnection.getBitmapFromURL(image.getUrl());
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
    protected void onPreExecute() {
        super.onPreExecute();
        Utils.print(Log.DEBUG, "DownloadImage AsyncTask - onPreExecute()");
    }

    @Override
    protected void onPostExecute(ViewHolder viewHolder) {
        super.onPostExecute(viewHolder);
        Utils.print(Log.DEBUG, "DownloadImage AsyncTask - onPostExecute(ViewHolder viewHolder)");

        if (status.equals(ResponseStatus.OK) && viewHolder.mBitmap != null) {
            viewHolder.mImageView.setImageBitmap(viewHolder.mBitmap);
            viewHolder.mView.invalidate();
        }
    }
}