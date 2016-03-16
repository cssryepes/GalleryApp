
package com.kogi.galleryapp.domain.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Feed implements Parcelable {

    private String id;
    private String caption;
    private String link;
    private String createdTime; //timestamp
    private List<String> tags;
    private List<Image> images;
    private User user;
    private long likes;

    public Feed() {

    }

    public Feed(Parcel in) {
        id = in.readString();
        caption = in.readString();
        link = in.readString();
        createdTime = in.readString();
        tags = in.createStringArrayList();
        likes = in.readLong();
    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel in) {
            return new Feed(in);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(caption);
        dest.writeString(link);
        dest.writeString(createdTime);
        dest.writeStringList(tags);
        dest.writeLong(likes);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "id='" + id + '\'' +
                ", caption='" + caption + '\'' +
                ", link='" + link + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", tags=" + tags +
                ", images=" + images +
                ", user=" + user +
                ", likes=" + likes +
                '}';
    }
}
