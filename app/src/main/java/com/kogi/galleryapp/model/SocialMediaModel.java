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
//            if (type == SocialMediaType.INSTAGRAM)
        new DownloadFeed(false).execute("https://api.instagram.com/v1/media/popular", "client_id", "05132c49e9f148ec9b8282af33f88ac7");

    }

    public void refreshFeedSocialMedia(SocialMediaType type) {
        new DownloadFeed(true).execute("https://api.instagram.com/v1/media/popular", "client_id", "05132c49e9f148ec9b8282af33f88ac7");

    }

    private void sendDataListener(ResponseStatus status, Object response) {
        if (mListener != null) {
            mListener.onDataReceived(status, response);
        }
    }

    private class DownloadFeed extends AsyncTask<String, Void, ResponseStatus> {
        private EntitiesFactory mFactory = null;
        private String mResponse = null;
        private boolean mRefresh = false;

        public DownloadFeed(boolean isRefresh) {
            mRefresh = isRefresh;
        }

        @Override
        protected ResponseStatus doInBackground(String... params) {
            GalleryApp app = GalleryApp.getInstance();
            String url = String.format(app.
                    getString(R.string.format_url), params[0], params[1], params[2]);

            if (app.isNetworkAvailable()) {
                try {
                    mResponse = HttpConnection.getResponse(url);
                    app.addJSONToCache(url, mResponse);
                } catch (IOException e) {
                    mResponse = Utils.getStackTrace(e);
                    return ResponseStatus.ERROR;
                }
            } else {
                mResponse = app.getJSONFromCache(url);
                if (mResponse == null || mRefresh) {
                    mResponse = app.getString(R.string.prompt_no_connectivity);
                }
                return ResponseStatus.NO_CONNECTION;
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
            JSONObject json = null;
            try {
                json = new JSONObject(mResponse);

            } catch (Exception e) {
                status = ResponseStatus.ERROR;
                Utils.print(Log.ERROR, Utils.getStackTrace(e));
            }

            if (json != null) {
                try {
                    feed = this.mFactory.getFeedFromJSON(json);
                } catch (Exception e) {
                    Utils.print(Log.ERROR, Utils.getStackTrace(e));
                }
            }

            Utils.print(Log.DEBUG, status.name());
            Utils.print(Log.DEBUG, mResponse);

            sendDataListener(status, status.equals(ResponseStatus.ERROR) ? mResponse : feed);

        }

    }

}
