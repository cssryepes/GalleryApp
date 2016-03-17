package com.kogi.galleryapp.model;

import android.os.AsyncTask;
import android.util.Log;

import com.kogi.galleryapp.GalleryApp;
import com.kogi.galleryapp.R;
import com.kogi.galleryapp.Utils;
import com.kogi.galleryapp.domain.EntitiesFactory;
import com.kogi.galleryapp.domain.HttpConnection;
import com.kogi.galleryapp.domain.entities.Feed;
import com.kogi.galleryapp.domain.enums.ResponseStatus;
import com.kogi.galleryapp.domain.enums.SocialMediaType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class SocialMediaModel {

    private OnSocialMediaListener mListener;

    private SocialMediaModel() {
    }

    public SocialMediaModel(OnSocialMediaListener mListener) {
        this.mListener = mListener;
    }

    public void removeOnSocialMediaListener() {
        this.mListener = null;
    }

    public void getFeedSocialMedia(SocialMediaType type) {

        if (GalleryApp.getInstance().isNetworkAvailable()) {
//            if (type == SocialMediaType.INSTAGRAM)
            new DownloadFeed().execute("https://api.instagram.com/v1/media/popular", "client_id", "05132c49e9f148ec9b8282af33f88ac7");

        } else {
            sendDataListener(ResponseStatus.NO_CONNECTION, GalleryApp.getInstance().getString(R.string.no_connectivity));

        }

    }

    private void sendDataListener(ResponseStatus status, Object response) {
        if (mListener != null) {
            mListener.onDataReceived(status, response);
        }
    }

    private class DownloadFeed extends AsyncTask<String, Void, ResponseStatus> {
        private EntitiesFactory mFactory = null;
        private String mResponse = null;

        @Override
        protected ResponseStatus doInBackground(String... params) {
            try {
                mResponse = HttpConnection.getResponse(String.format(GalleryApp.getInstance().getString(R.string.format_url), params[0], params[1], params[2]));
            } catch (IOException e) {
                mResponse = Utils.getStackTrace(e);
                return ResponseStatus.ERROR;
            }
            return ResponseStatus.OK;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.mFactory = new EntitiesFactory();
        }

        @Override
        protected void onPostExecute(ResponseStatus status) {
            super.onPostExecute(status);
            List<Feed> feed = null;
            try {
                JSONObject json = new JSONObject(mResponse);
                feed = this.mFactory.getFeedFromJSON(json);

            } catch (JSONException e) {
                status = ResponseStatus.ERROR;
                mResponse = Utils.getStackTrace(e);
            }

            Utils.print(Log.DEBUG, status.name());
            Utils.print(Log.DEBUG, mResponse.toString());

            sendDataListener(status, feed);

        }

    }

}