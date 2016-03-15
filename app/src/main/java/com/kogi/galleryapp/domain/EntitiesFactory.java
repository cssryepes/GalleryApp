package com.kogi.galleryapp.domain;

import com.kogi.galleryapp.domain.entities.Feed;
import com.kogi.galleryapp.domain.entities.Image;
import com.kogi.galleryapp.domain.entities.User;
import com.kogi.galleryapp.domain.enums.ImageQuality;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EntitiesFactory {

    public List<Feed> getFeedFromJSON(JSONObject feed) throws JSONException {

        JSONArray data = feed.optJSONArray("data");

        List<Feed> response = new ArrayList<>();

        for (int i = 0; i < data.length(); i++) {
            JSONObject json = data.getJSONObject(i);
            response.add(getFeedFromJSONObject(json));
        }

        return response;
    }

    private Feed getFeedFromJSONObject(JSONObject data) throws JSONException {
        Feed response = new Feed();
        response.setCreatedTime(data.optString("created_time"));
        response.setId(data.optString("id"));
        response.setLikes(data.optJSONObject("likes").optLong("count"));
        response.setLink(data.optString("link"));
        response.setTags(getTagsFromJSONObject(data));
        response.setUser(getUserFromJSONObject(data));
        response.setImages(getImagesFromJSONObject(data));
        return response;
    }

    private List<Image> getImagesFromJSONObject(JSONObject data) {

        List<Image> response = new ArrayList<>();

        for (ImageQuality quality : ImageQuality.values()) {
            Image image = getImageFromJSONObject(data, quality);
            response.add(image);
        }

        return response;
    }

    private Image getImageFromJSONObject(JSONObject data, ImageQuality quality) {
        Image image = new Image();
        image.setQuality(quality);
        image.setUrl(data.optJSONObject(quality.getValue()).optString("url"));
        image.setWidth(data.optJSONObject(quality.getValue()).optInt("width"));
        image.setHeight(data.optJSONObject(quality.getValue()).optInt("height"));
        return image;
    }

    private List<String> getTagsFromJSONObject(JSONObject data) throws JSONException {
        JSONArray tags = data.optJSONArray("tags");
        List<String> response = new ArrayList<>();
        for (int i = 0; i < tags.length(); i++) {
            response.add(tags.getString(i));
        }
        return response;
    }

    private User getUserFromJSONObject(JSONObject data) {
        JSONObject user = data.optJSONObject("user");
        User response = new User();
        response.setUsername(user.optString("username"));
        response.setProfilePricture(user.optString("profile_picture"));
        response.setId(user.optString("id"));
        response.setFullName(user.optString("full_name"));
        return response;
    }

}
