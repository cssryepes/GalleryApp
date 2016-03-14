package com.kogi.galleryapp.domain.models;

import android.util.Log;

import com.kogi.galleryapp.GalleryApp;
import com.kogi.galleryapp.R;
import com.kogi.galleryapp.Utils;
import com.kogi.galleryapp.domain.EntitiesFactory;
import com.kogi.galleryapp.domain.HttpConnection;
import com.kogi.galleryapp.domain.entities.Feed;
import com.kogi.galleryapp.domain.entities.Image;
import com.kogi.galleryapp.domain.enums.ResponseStatus;
import com.kogi.galleryapp.domain.enums.SocialMediaType;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class SocialMediaModel {

    private OnSocialMediaListener mListener;
    private EntitiesFactory mFactory;

    private SocialMediaModel() {
    }

    public SocialMediaModel(OnSocialMediaListener mListener) {
        this.mListener = mListener;
        this.mFactory = new EntitiesFactory();
    }

    public void removeOnSocialMediaListener() {
        this.mListener = null;
    }

    public boolean getFeedSocialMedia(SocialMediaType type) {
        Object response = null;
        ResponseStatus status = null;

        if (GalleryApp.getInstance().isNetworkAvailable()) {
            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("client_id", "05132c49e9f148ec9b8282af33f88ac7");
                response = HttpConnection.getResponse("https://api.instagram.com/v1/media/popular?", params);

                JSONObject json = new JSONObject((String) response);
                //response = this.mFactory.getFeedFromJSON(json);
                List<Feed> feed = this.mFactory.getFeedFromJSON(json);
                status = ResponseStatus.OK;

                for (Feed item : feed) {
                    for (Image image : item.getImages()) {
                        HttpConnection.getBitmapFromURL(image.getUrl());

                    }
                }


            } catch (Exception e) {
                status = ResponseStatus.ERROR;
                response = Utils.getStackTrace(e);
            }

        } else {
            status = ResponseStatus.NO_CONNECTION;
            response = GalleryApp.getInstance().getString(R.string.no_connectivity);
        }

        GalleryApp.print(Log.DEBUG, status.name());
        GalleryApp.print(Log.DEBUG, response.toString());

        if (this.mListener != null) {
            this.mListener.onDataReceived(status, response);
            return true;
        }

        return false;

    }

}
