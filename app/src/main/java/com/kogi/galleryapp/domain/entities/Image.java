package com.kogi.galleryapp.domain.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.kogi.galleryapp.domain.enums.ImageQuality;

public class Image implements Parcelable {
    private String url;
    private ImageQuality quality;
    private int width;
    private int height;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeString(quality.getValue());
    }

    protected Image(Parcel in) {
        url = in.readString();
        width = in.readInt();
        height = in.readInt();
        quality = ImageQuality.get(in.readString());
    }

    public Image() {
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

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
