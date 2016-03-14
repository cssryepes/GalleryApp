package com.kogi.galleryapp.domain.entities;

import com.kogi.galleryapp.domain.enums.ImageQuality;

public class Image {
    private String url;
    private ImageQuality quality;

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
                ", quality=" + quality.name() +
                '}';
    }
}
