package com.kogi.galleryapp.domain.entities;

import com.kogi.galleryapp.domain.enums.ImageQuality;

public class Image {
    private String url;
    private ImageQuality quality;
    private int width;
    private int height;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImageQuality getQuality() {
        return quality;
    }

    public void setQuality(ImageQuality quality) {
        this.quality = quality;
    }

    @Override
    public String toString() {
        return "Image{" +
                "url='" + url + '\'' +
                ", quality=" + quality +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
